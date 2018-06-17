/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.minhaz1217;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFrame;

public class AutoSnake extends JFrame {

    public AutoSnake() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
               
        setResizable(false);
        pack();
        
        setTitle("Auto Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        Queue<Point> q = new LinkedList<>();
        BFS b = new BFS();
        b.getDirection(1, 0, 0, 50, 50);

        
        EventQueue.invokeLater(() -> {
            JFrame ex = new AutoSnake();
            ex.setVisible(true);
        });
    }
}
