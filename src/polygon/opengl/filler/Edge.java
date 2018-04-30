/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygon.opengl.filler;

/**
 *
 * @author Kevin
 */
public class Edge {
    public float yMin;
    public float yMax;
    public float xVal;
    public float slop;
    public Edge(float y1, float y2, float x, float slope){
        yMin = y1;
        yMax = y2;
        xVal = x;
        slop = slope;
    }
    //Returns true if what you're comparing to is bigger
    public boolean compare(Edge e){
        if (this.yMin < e.yMin){
            return true;
        } else if (this.yMin > e.yMin){
            return false;
        } else {
            if (this.xVal < e.xVal){
                return true;
            } else if (this.xVal > e.xVal){
                return false;
            } else {
                if (this.yMax < e.yMax){
                    return true;
                } else if (this.yMax > e.yMax){
                    return false;
                } else {
                    return false;
                }
            }
        }
    }
    
    public int compareTo(Edge e){
        if (this.xVal < e.xVal){
            return -1;
        } else if (this.xVal == e.xVal){
            return 0;
        } else {
            return 1;
        }
    }
    
}
