/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.minhaz1217;

/**
 *
 * @author Minhaz
 */
public class Point implements Comparable<Point> {
public int x,y;
public double cost;
String path;
    Point(int xx, int yy, String str, double co){
        x = xx;
        y = yy;
        path = str;
        cost = co;
    }
    Point(int xx, int yy){
        x = xx;
        y = yy;
        path = "";
        cost = 0.00;
    }
    @Override
    public int compareTo(Point o) {
        // lowest value means higher priority
        if(cost >= o.cost){
            return 1;
        }else{
            return -1;
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    

}

