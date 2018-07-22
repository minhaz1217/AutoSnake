/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.minhaz1217;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Minhaz
 */



public class AStar {
    
    private final int dx[] = {0, 0, -1, 1};
    private final int dy[] = {-1, 1, 0, 0};
    private final String dir[] = {"u", "d", "l", "r"};
    private final String revDir[] = {"d", "u", "r", "l"};
    
    
    double distance(Point a, Point b){
        //System.out.println("A: "+ a.x + " "+ a.y + " : " + b.x + " " + b.y);
        double ans1 = (b.x - a.x) * (b.x - a.x);
        double ans2 = (b.y - a.y) * (b.y - a.y);
        //System.out.println("A: " + ans1 + " B: " + ans2);
        return Math.sqrt( ans1 + ans2 );
    }
    public String directinoWithoutBody(int direction, int headx, int heady, int foodx, int foody) {
        
        PriorityQueue<Point>pq = new PriorityQueue<>();
        //Debug.println(dir[direction] + "", revDir[direction] + "");
        int x, y, vx, vy;
        double myCost,hcost,gcost;
        int sz = 304;
        boolean[][] visited = new boolean[sz][sz];
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                visited[i][j] = false;
            }
        }
        String finalPath = "";
        Point head = new Point(headx, heady, "", 0);
        Point food = new Point(foodx, foody, "", 0);
        pq.add(new Point(headx, heady, "", distance(head,food)+0));
        visited[headx][heady] = false;
        while (pq.size() > 0) {
            Point temp = pq.remove();
            x = temp.x;
            y = temp.y;
            myCost = temp.cost;
            hcost = distance(food, new Point(x,y,"", 0));
            gcost = myCost - hcost;
            //System.out.println("GCOST: "+ gcost);
            if (x == foodx && y == foody) {
                //System.out.println(temp.path);=
                    //System.out.println(dir[direction] + " : " + temp.path + " -> " + revDir[direction]);
                finalPath = temp.path;
                //Debug.println(dir[direction] + "", revDir[direction] + "");
                //Debug.println(finalPath, "path");
                break;
            }
            for (int i = 0; i < 4; i++) {
                vx = x + dx[i];
                vy = y + dy[i];
                if (!(vx < 0 || vx > 300 || vy < 0 || vy > 300) && !visited[vx][vy]) {
                    
                    if(temp.path.length() == 0){ // we just need to store the first direction move.
                        if(dir[i] != revDir[direction]){ // this is so that the snake can't go reverse direction.
                            pq.add(new Point(vx, vy, dir[i], distance(food, new Point(vx,vy, "", 0)) + gcost+1    ));
                        }
                    }else{ // if the path already has first move we don't need any modification.
                        pq.add(new Point(vx, vy, temp.path, distance(food, new Point(vx,vy, "", 0)) + gcost+1 ));
                    }
                    visited[vx][vy] = true;
                }
            }
        }
        //System.out.println("F: " + finalPath);
        return finalPath;
    }
    
    
    public String directinoWithBody(int direction, int xx[], int yy[], int bodyLength, int foodx, int foody) {
        
        PriorityQueue<Point>pq = new PriorityQueue<>();
        //Debug.println(dir[direction] + "", revDir[direction] + "");
        int x, y, vx, vy;
        double myCost,hcost,gcost;
        int sz = 304;
        int headx = xx[0], heady = yy[0];
        boolean[][] visited = new boolean[sz][sz];
        int len = bodyLength;
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                visited[i][j] = false;
            }
        }
        
        String finalPath = "";
        Point head = new Point(headx, heady, "", 0);
        Point food = new Point(foodx, foody, "", 0);
        pq.add(new Point(headx, heady, "", distance(head,food)+0));
        visited[headx][heady] = false;
        while (pq.size() > 0) {
            Point temp = pq.remove();
            x = temp.x;
            y = temp.y;
            myCost = temp.cost;
            hcost = distance(food, new Point(x,y,"", 0));
            gcost = myCost - hcost;
            //System.out.println("GCOST: "+ gcost);
            if (x == foodx && y == foody) {
                //System.out.println(temp.path);=
                    //System.out.println(dir[direction] + " : " + temp.path + " -> " + revDir[direction]);
                finalPath = temp.path;
                //Debug.println(dir[direction] + "", revDir[direction] + "");
                //Debug.println(finalPath, "path");
                break;
            }
            for (int i = 0; i < 4; i++) {
                vx = x + dx[i];
                vy = y + dy[i];
                if (!(vx < 0 || vx > 300 || vy < 0 || vy > 300) && !visited[vx][vy]) {
                    for(int j=0;j<=len;j++){
                        if( !(vx == xx[j] && vy == yy[j]) ){
                            if(temp.path.length() == 0){ // we just need to store the first direction move.
                                if(dir[i] != revDir[direction]){ // this is so that the snake can't go reverse direction.
                                    pq.add(new Point(vx, vy, dir[i], distance(food, new Point(vx,vy, "", 0)) + gcost+1    ));
                                }
                            }else{ // if the path already has first move we don't need any modification.
                                pq.add(new Point(vx, vy, temp.path, distance(food, new Point(vx,vy, "", 0)) + gcost+1 ));
                            }
                            visited[vx][vy] = true;
                        }
                    }
                }
            }
        }
        //System.out.println("F: " + finalPath);
        return finalPath;
    
        //return "";
    }
}
