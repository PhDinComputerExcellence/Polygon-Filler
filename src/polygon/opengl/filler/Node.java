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
public class Node {
    public float x;
    public float y;
    public Node(float xx, float yy){
        x = xx;
        y = yy;
    }
    public float returnX(){
        return x;
    }
    public float returnY(){
        return y;
    }
    
}
