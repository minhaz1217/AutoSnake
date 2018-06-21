/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.minhaz1217;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import sun.security.ssl.Debug;

/**
 *
 * @author Minhaz
 */
public class BFS {
    private final int dx[] = {0, 0,-1, 1};
    private final int dy[] = {1, -1, 0, 0};
    private final char dir[] = {'d','u','l','r'};
    private final char revDir[] = {'u', 'd','r', 'l'};
    public String directinoWithoutBody(int direction, int headx, int heady, int foodx, int foody){
        Queue<Point> q = new LinkedList<>();
        
        //Debug.println(dir[direction] + "", revDir[direction] + "");
        int x, y,vx,vy;
        int sz = 304;
        boolean[][]  visited = new boolean[sz][sz];
        for(int i=0;i<sz;i++){
            for(int j =0 ;j < sz;j++){
                visited[i][j] = false;
            }
        }
        String finalPath = "";
        
        q.add(new Point(headx, heady, ""));
        visited[headx][heady] = false;
        while(q.size()> 0){
            Point temp = q.remove();
            x = temp.x;
            y = temp.y;
            if(x == foodx && y == foody){
                finalPath = temp.path;
                //Debug.println(dir[direction] + "", revDir[direction] + "");
                //Debug.println(finalPath, "path");
                break;
            }
            for(int i=0;i<4;i++){
             vx = x+dx[i];
             vy = y+dy[i];
             if( !(vx<0 || vx >300 || vy<0 || vy>300) && !visited[vx][vy] ){
                 String myPath = "";
                 if(temp.path.equals("") && dir[i] == revDir[direction]){
                     continue;
                 }else{
                     myPath = dir[i] + "";
                 }
                 q.add(new Point(vx,vy, myPath));
                 visited[vx][vy] = true;
             }
            }
            
        }
        return finalPath;
//        Debug.println(finalPath , "PATH");
    }
}
