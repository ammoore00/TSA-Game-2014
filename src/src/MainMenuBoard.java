package src;


import javax.swing.JButton;
import javax.swing.AbstractButton;

import java.awt.Color;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;


@SuppressWarnings({ "serial", "unused" })
public class MainMenuBoard extends JPanel implements ActionListener{

    private Timer timer;
    private String one = ".\\..\\Main.png";
    private ImageIcon background = new ImageIcon(this.getClass().getResource(one));

    boolean playPressed = false;
    
    public  MainMenuBoard() {
        //set all present to get mouse input
        addMouseListener( new TAdapter() );
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true); 
        //

        //image = background.getImage();
    }

    /**
     * Handles all drawing 
     */
    public void paint(Graphics g) {
        //call recursive, not sure why, but it ust works, needs to be there
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        // Draw the button on the board
        //Graphics2D g2d = (Graphics2D)g;
        drawSpriteFrame(background.getImage(),g2d, 0, 0, 1,0,960,480);
        //Finish the draw
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        //creats button 1
        //controls loading text
        /**
        if (playPressed){
        	System.out.println("Loading..");
        	g2d.drawString("Time regression sequence intiated. Please wait...", 10,10);
        
        }
        */
        }
    

    //Progress bar\
    /**
    public void drawProgressBar(Graphics2D g2d,int x,int y,int l,int h,int percent){
        //in decimal percent
        g2d.drawRect(x,y,l,h);
        g2d.setColor(Color.black);
        g2d.fillRect(x,y,l,h);
        int pix = (int)(percent*l);//get length of green health bar
        g2d.setColor(Color.blue);
        g2d.fillRect(x,y,pix,h);

        //set back to black
        g2d.setColor(Color.black);
    }
    */
    
    
    
    
    public void drawSpriteFrame(Image source, Graphics2D g2d, int x, int y,
    int columns, int frame, int width, int height)
    {
        //creates frame size
        int frameX = (frame % columns) * width;
        int frameY = (frame / columns) * height;
        //draws image according to the box 
        g2d.drawImage(source, x, y, x+width, y+height,frameX, frameY, frameX+width, frameY+height, this);
    }

    /**
     * Acts as timer tick
     */
    public void actionPerformed(ActionEvent e) {
        repaint();  
    }

    /**
     * Handles the mouse input
     */
    private class TAdapter extends MouseAdapter implements MouseListener{

        public void mousePressed(MouseEvent e) 
        {
            //gets the x and the y size for the button and draws then checks to see if the 
            //  `click is in that range, AKA check to see if picture is clicked on 

            //how to play button
            if (e.getX() >= 10 && e.getX() <= 295){
                if(e.getY() <= 363 && e.getY() >= 266){
                    System.out.println("a and d to move left and right");
                    System.out.println("Space to jump. Press Space again to double-jump");
                    System.out.println("s to pick up an item");
                    System.out.println("Click to use an item");
                    System.out.println("Collect the artifact, then go to goal- the compass will point to your objective");
                    System.out.println("0 to reset the level");
                }
            }
            //play button
            if (e.getX() >= 350 && e.getX() <= 669){
                if(e.getY() <= 363 && e.getY() >= 266){
                	playPressed = true;
                	repaint();
                     Exe run= new Exe();
                     
                }
            }

            // quit button
            if (e.getX() >= 662 && e.getX() <= 949){
                if(e.getY() <= 363 && e.getY() >= 266){
                    System.out.println("q"); 

                }
            }
        }
    }
}
