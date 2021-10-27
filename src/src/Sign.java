package src;


import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
/**
 * 
 * Sign stuff
 */
@SuppressWarnings({ "unused" })
public class Sign implements IWorldInterface
{
    // instance variables - replace the example below with your own
    private String name;
    private int objX,objY;
    private Image image=null;//probally not even use this
    public int columns,height,width,frame;
    public char type = 'S';
    public String message;//no message now
  
    /**
     * Constructor for objects of class Sign
     * Hopefully will create a colored rectangle with corresponding text
     */
    public Sign(int x, int y,int w, int h,String img, String n)
    {
      //regular object stuff
      name = n;
      objX = x;
      objY = y;
      //freaking useless stuff
      columns =1;
      frame = 0;
      //
      //height and width of it
      height = h;
      width = w;
      //Message
     // message = text;
      image = loadImg(img);
      
    }
    /**
     * Return the signs text to board
     */
    public String getText(){
        return "nah";
    }
    
     public void act(ArrayList<IWorldInterface> objs, Player p){
        //maybe dont use this after all :I
    }
    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return objX;
    }

    public int getY()
    { return objY;
    }

    public void setX(int x){
        objX = x;
    }

    public void setY(int y){
        objY = y;
    }
    //wont do shit! wow
    public Image getImage(){
        return image;
    }

    public int getColumns(){
        return columns;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getFrame(){
        return frame;
    }
    public char getType(){
        return type;
    }
    
    public Image loadImg(String s){///load an image from 

        ImageIcon icon = new ImageIcon(this.getClass().getResource(s));
        Image img = icon.getImage();
        return img;

    }
}
