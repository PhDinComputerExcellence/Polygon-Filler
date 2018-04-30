/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygon.opengl.filler;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        glOrtho(-1280, 1280, -960, 960, 1, -1);
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
        ArrayList<Node> list = new ArrayList<Node>();
        String check = "";
        String hold = "";
        ArrayList<String> holdForm = new ArrayList<String>();
        char c;
        Scanner b;
        boolean Transformation = false;
            boolean newPoly = false;
        while (sc.hasNext()){
            if (!newPoly){
            hold = sc.nextLine();
            c = hold.charAt(0);
            hold = hold.replaceAll(",", " ");
            b = new Scanner(hold);} 
                else {
                c = check.charAt(0);
                hold = check;
                hold = hold.replaceAll(",", " ");
                b = new Scanner(hold);
                Transformation = false;
                newPoly = false;
                
                
            }
            if (c=='P'){
                String newhold = hold.substring(1);
                b = new Scanner(newhold);
                glColor3f(b.nextFloat(), b.nextFloat(), b.nextFloat());
            } else if (Character.isDigit(c)){
                float holdx = b.nextFloat();
                float holdy = b.nextFloat();
                list.add(new Node(holdx, holdy));
                
            } else if (c == 'T'){
                Transformation = true;
                holdForm.removeAll(holdForm);
                while (sc.hasNext()){
                    check = sc.nextLine();
                    if (check.charAt(0)=='P'){
                        //Take off tranaforms if you don't want the transforms
                        Transform(list, holdForm);
                        Fill(list);
                        newPoly = true;
                        list = new ArrayList<Node>();
                        break;
                    }
                    holdForm.add(check);
                    
                }
                
            }  
        }
        //Take off tranaforms if you don't want the transforms
       Transform(list, holdForm);
        Fill(list);
        System.out.println("Done");
     //   Fill(list); 
        glEnd();
        Display.update();
        Display.sync(60);
        }catch(Exception e){
        }
    
}
    }

    public void Transform(ArrayList<Node> list, ArrayList<String> action){
        
        for (int i = 0; i <action.size(); i++){
            if (action.get(i).charAt(0) == 'r'){
                Scanner sc = new Scanner(action.get(i).substring(1));
                Rotate(list, sc.nextFloat(), sc.nextFloat(), sc.nextFloat());
            } else if (action.get(i).charAt(0) == 't'){
                Scanner sc = new Scanner(action.get(i).substring(1));
                Translate(list, sc.nextFloat(), sc.nextFloat());
            } else {
               Scanner sc = new Scanner(action.get(i).substring(1));
                Scaling(list, sc.nextFloat(), sc.nextFloat(),sc.nextFloat(),sc.nextFloat());
            }
        }
        
        
    }
    
    public void Translate(ArrayList<Node> list, float x, float y){
        for (int i = 0; i < list.size(); i++){
            list.get(i).x += x;
            list.get(i).y += y;
        }
    }
    
    public float getCenterx(ArrayList<Node> list){
        float minx= 0;
        float maxx= 0;
        float miny =0;
        float maxy =0;
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                minx = list.get(i).x;
            } else {
                if (list.get(i).x < minx){
                    minx = list.get(i).x;
                }
            }
        }
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                miny = list.get(i).y;
            } else {
                if (list.get(i).y < minx){
                    miny = list.get(i).y;
                }
            }
        }
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                maxx = list.get(i).x;
            } else {
                if (list.get(i).x > maxx){
                    maxx = list.get(i).x;
                }
            }
        }
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                maxy = list.get(i).y;
            } else {
                if (list.get(i).y > maxy){
                    maxx = list.get(i).y;
                }
            }
        }
        return (minx +((maxx - minx)/2));
        
    }
    
    public float getCentery(ArrayList<Node> list){
        float minx= 0;
        float maxx= 0;
        float miny =0;
        float maxy =0;
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                minx = list.get(i).x;
            } else {
                if (list.get(i).x < minx){
                    minx = list.get(i).x;
                }
            }
        }
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                miny = list.get(i).y;
            } else {
                if (list.get(i).y < minx){
                    miny = list.get(i).y;
                }
            }
        }
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                maxx = list.get(i).x;
            } else {
                if (list.get(i).x > maxx){
                    maxx = list.get(i).x;
                }
            }
        }
        
        for (int i = 0; i < list.size(); i++){
            if (i==0){
                maxy = list.get(i).y;
            } else {
                if (list.get(i).y > maxy){
                    maxx = list.get(i).y;
                }
            }
        }
        return (miny + ((maxy-miny)/2));
        
    }
    
    
    public void Rotate(ArrayList<Node> list, float degree, float pivot, float pivot2){
        float centerx = getCenterx(list);
        float centery = getCentery(list);
        for (int i = 0 ; i < list.size(); i++){
            float[] holder = {list.get(i).x, list.get(i).y};
            AffineTransform.getRotateInstance(Math.toRadians(degree), pivot, pivot2).transform(holder, 0, holder, 0, 1);
            list.get(i).x = holder[0];
            list.get(i).y = holder[1];
//            list.get(i).x = (int)(pivot + (list.get(i).x - pivot)*Math.cos(degree) - (list.get(i).y - pivot2)*Math.sin(degree));
//            list.get(i).y = (int)(pivot2 + (list.get(i).x -pivot)*Math.sin(degree) + (list.get(i).y - pivot2)*Math.cos(degree));
        }
    }
    
    public void Scaling(ArrayList<Node> list, float scale, float scale2, float pivot, float pivot2){
        float centerx = getCenterx(list);
        float centery = getCentery(list);
        for (int i = 0 ; i < list.size(); i++){
            list.get(i).x = Math.round(((list.get(i).x - pivot)*scale)+pivot);
            list.get(i).y = Math.round(((list.get(i).y - pivot2)*scale2)+pivot2);
        }
    }




   public void changeC(float x, float y, float z){
        glColor3f(x,y,z);
    }
   
   public void Fill(ArrayList<Node> list){
        ArrayList<Edge> All = new ArrayList<Edge>();
        ArrayList<Edge> Global = new ArrayList<Edge>();
        ArrayList<Edge> Active = new ArrayList<Edge>();
        for (int i = 0; i < list.size(); i++){
            if (i == (list.size()-1)){float currentymin;
                float currentymax;
                float currentxval;
                float currentslope;
                int check = findYMin(list.get(i), list.get(0));
                if (check==0){
                    
                    currentymin = list.get(0).y;
                    currentxval = list.get(0).x;
                } else if (check==1){
                    currentymin = list.get(i).y;
                    currentxval = list.get(i).x;
                } else {
                    currentymin = list.get(0).y;
                    currentxval = list.get(0).x;
                }
                
                int check2 = findYMax(list.get(i), list.get(0));
                if (check2==0){
                    currentymax = list.get(0).y;
                } else if (check2==1){
                    currentymax = list.get(i).y;
                } else {
                    currentymax = list.get(0).y;
                }
                
                currentslope = inverseSlope(list.get(i), list.get(0));
                All.add(new Edge(currentymin, currentymax, currentxval, currentslope));
                
            } else {
                float currentymin;
                float currentymax;
                float currentxval;
                float currentslope;
                int check = findYMin(list.get(i), list.get(i+1));
                if (check==0){
                    currentymin = list.get(i+1).y;
                    currentxval = list.get(i+1).x;
                } else if (check==1){
                    currentymin = list.get(i).y;
                    currentxval = list.get(i).x;
                } else {
                    currentymin = list.get(i+1).y;
                    currentxval = list.get(i+1).x;
                }
                
                int check2 = findYMax(list.get(i), list.get(i+1));
                if (check2==0){
                    currentymax = list.get(i+1).y;
                } else if (check2==1){
                    currentymax = list.get(i).y;
                } else {
                    currentymax = list.get(i+1).y;
                }
                
                currentslope = inverseSlope(list.get(i), list.get(i+1));
                All.add(new Edge(currentymin, currentymax, currentxval, currentslope));
                
                
            }
            
        }
        
//        for (int i = 0; i < All.size(); i++){
//            System.out.println(All.get(i).yMin);
//        }
        //Global Table Move
            for (int i = 0; i < All.size(); i++){
                if (i==0){
                    if (checkIf(All.get(0))){
                        Global.add(All.get(0));
                    }
                } else {
                    if (checkIf(All.get(i))){
                        for (int j = Global.size()-1; j >= 0; j--){
                            if (All.get(i).compare(Global.get(j))){
                                if (j==0){
                                    Global.add(0, All.get(i));
                                }
                            } else {
                                Global.add(j+1, All.get(i));
                                break;
                            }
                        }
                    }
                }
            }
            
//            for (int i = 0; i < Global.size(); i++){
//            System.out.println(Global.get(i).yMin);
//        }
            //Active Table Start
            float Scanline = Global.get(0).yMin;
            boolean Parity = true;
            float max = 0;
            for (int i = 0; i < Global.size(); i++){
                if (i==0){
                    max = Global.get(i).yMax;
                } else {
                    if (Global.get(i).yMax > max){
                        max = Global.get(i).yMax;
                    }
                }
            }
            for (int i = (int) Scanline; i < max; i++){
                for (int j = 0; j < Global.size(); ){
                    if (Global.get(j).yMin!=i){
                        break;
                    }else{
                        Active.add(Global.get(j));
                        Global.remove(j);
                    }
                }
//                for (int z = 0; z < Global.size(); z++){
//                    System.out.println(Global.get(z).yMin);
//                }
                
                 Collections.sort(Active, new Comparator<Edge>(){
                    @Override
                    public int compare(Edge e , Edge x){
                    return e.compareTo(x);
                     }
                        });
                FillStuff(Active, i);
                
                for (int j = Active.size()-1; j >-1; j--){
                    if (Active.get(j).yMax <= i+1){
                        Active.remove(j);
                    }
                }
                               
            }
            
            
            
        
   }
   
   public void FillStuff(ArrayList<Edge> list, int y){
       boolean Parity = false;
//       for (int i = 0; i < list.size(); i++){
//           System.out.println(list.get(i).yMax);
//       }
       
       for(int i = 0; i < list.size(); i++){
           Parity = !Parity;
           if (Parity){
               float xMax = list.get(i+1).xVal;
               
               for (float j = list.get(i).xVal; j <= xMax; j++){
                   glVertex2f(j, y);
               }
           }
           
       }
       for (int j = 0; j < list.size(); j++){
               list.get(j).xVal = list.get(j).xVal + list.get(j).slop;
           }
       
       Collections.sort(list, new Comparator<Edge>(){
           @Override
           public int compare(Edge e , Edge x){
               return e.compareTo(x);
           }
       });
   }
   
   public boolean checkIf(Edge e){
       if (e.slop==1999999){
           return false;
       } else {
           return true;
       }
   }
   
   public int findYMin(Node x, Node y){
       if (x.y < y.y){
           return 1;
       } else if (y.y < x.y){
           return 2;
       } else {
           return 0;
       }
   }
   
   public int findYMax(Node x, Node y){
       if (x.y > y.y){
           return 1;
       } else if (y.y > x.y){
           return 2;
       } else {
           return 0;
       }
   }
   
   public float inverseSlope(Node x, Node y){
       float dx = y.x - x.x;
       float dy = y.y - x.y;
       if (dy == 0){
           return 1999999;
       } else {
           return dx/dy;
       }
   }
   
   
   
   public void makePoly(ArrayList<Node> list){
       glBegin(GL_POLYGON);
        for (int i = (list.size()-1); i < 0; i--){
            glVertex2f(list.get(i).x, list.get(i).y);
        }
       glEnd();
   }
}
