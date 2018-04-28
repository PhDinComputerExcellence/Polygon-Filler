/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygon.opengl.filler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Kevin
 */
public class PolygonOpenGLFiller {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    PolygonOpenGLFiller work = new PolygonOpenGLFiller();
        
        work.start();    }
    
    public void start() {
try {
    createWindow();
    initGL();
    render();} catch (Exception e) {
e.printStackTrace();}
}
    
    public void createWindow() throws Exception{
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1");
        
        Display.create();
    }
    
    public void initGL() throws Exception{
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        glMatrixMode(GL_PROJECTION); 
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render() {
while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
    try{
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();  
        glColor3f(1.0f,1.0f,0.0f);
        glPointSize(1);
        glBegin(GL_POINTS);
        File file = new File("coordinates.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner sc = new Scanner(br);
        while (sc.hasNext()){
            String hold = sc.nextLine();
            char c = hold.charAt(0);
            hold = hold.substring(1);
            hold = hold.replaceAll(",", " ");
            Scanner b = new Scanner(hold);
            if (c=='P'){
                glColor3f(b.nextInt(), b.nextInt(), b.nextInt());
            } else if (Character.isDigit(c)){
                
            }
            
        }
        glEnd();
        Display.update();
        Display.sync(60);
        }catch(Exception e){
        }
    
}

    }
}
