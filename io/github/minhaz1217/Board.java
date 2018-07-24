/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.minhaz1217;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Console;
import java.util.PriorityQueue;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.security.ssl.Debug;


public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 23;
    
    
    

    public final int x[] = new int[ALL_DOTS];
    public final int y[] = new int[ALL_DOTS];

    private int dots;
    private int score;
    public int apple_x;
    public int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    
    
    private final int AI_MODE = 2; // 1 for bfs without body, 2 for bfs with body
    
    public String snakePath = "";
    public int pathIndex = 0;
    BFS bfs = new BFS();
    AStar astar = new AStar();
    private final char revDir[] = {'u', 'd','r', 'l'};
    
    public Board() {
        score = 0;
        initBoard();
    }
    
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setDoubleBuffered(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
            drawScore(g);
            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString("Score: "+ score, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 + 20);
    }
    private void drawScore(Graphics g) {
        
        String msg = "Score: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)), B_HEIGHT);
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score++;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
    public char getDirectionAsChar(){
        if(upDirection){
            return 'u';
        }else if(downDirection){
            return 'd';
        }else if(leftDirection){
            return 'l';
        }else{
            return 'r';
        }
    }
    
    public int getDirectionAsInt(){       
        if(upDirection){
            return 0;
        }else if(downDirection){
            return 1;
        }else if(leftDirection){
            return 2;
        }else{
            return 3;
        }
    }
    public void endGame(){
        inGame = false;
    }
    public void startGame(){
        inGame = true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            
            if(AI_MODE == 1){
                snakePath = bfs.directinoWithoutBody(getDirectionAsInt() , x[0], y[0], apple_x, apple_y);
                if(snakePath.length() == 0){
                    System.out.println("ZERO");
                    snakePath = bfs.directinoWithoutBody(getDirectionAsInt() , x[0], y[0], apple_x, apple_y);
                }
            }else if(AI_MODE == 2){
                //System.out.println("X: "+ x.length + " Y: " + y.length);
                snakePath = bfs.directinoWithBody(getDirectionAsInt(), x, y, dots, apple_x, apple_y);
            }else if(AI_MODE == 3){
                snakePath = astar.directinoWithoutBody(getDirectionAsInt(), x[0], y[0], apple_x, apple_y);
            }else if(AI_MODE == 4){
                snakePath = astar.directinoWithBody(getDirectionAsInt(), x, y,dots, apple_x, apple_y);
            }
            else{
                snakePath = "";
            }
            

            if(AI_MODE != 0){
                goDirection(snakePath.charAt(0));
            
            }
            //System.out.println("PATH: " + snakePath + " " + snakePath.length());
            //System.out.println(getDirectionAsChar() + " : " + snakePath);
            //endGame();
            
            
            move();
            checkApple();
        }

        repaint();
    }
    
    public void goDirection(char a){
        //Debug.println("HELLO", "HI");
        if(getDirectionAsChar() != a)
        {
            //Debug.println("My Dir: " +  getDirectionAsChar(), a + " Path: "+ snakePath + " REv: " + revDir[getDirectionAsInt()] );
        }
        
        if(a == 'u'){
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }else if(a == 'd'){
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }else if(a == 'l'){
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }else if(a == 'r'){
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                   goDirection('l');
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                   goDirection('r');
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                   goDirection('u');
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                   goDirection('d');
            }
            if(key == KeyEvent.VK_SPACE && !inGame){
                //Debug.println("SPACE PRESSED", "GAMEOVER");
                score = 0;
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                leftDirection = false;
                inGame = true;
                initBoard();
            }
        }
    }
}
