package src;


import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
public class WObject implements IWorldInterface
{
    protected String name;
    protected int objX,objY;
    private Image image;
    public int columns,height,width,frame;
    public char type = 'o';
    protected String picL,picU,picR;
    //in hand fun stuff
    protected Image[] inHand = new Image[3];//3 positions
    //for use with weapons
    
    public WObject(int x, int y,String n,String l,String u,String r)
    {
        objX = x;
        objY = y;
       name = n;
       
       picL = l;
       picU = u;
       picR = r;
        //load in hand
        //left
        inHand[0] = loadImg(picL);
        //up
        inHand[1] = loadImg(picU);
        //right
        inHand[2] = loadImg(picR);
    }
    
    public WObject(int x, int y,String n)
    {
        objX = x;
        objY = y;
       name = n;
      
    }
    public void act(ArrayList<IWorldInterface> objs, Player p){
        //maybe dont use this after all :I
    }
    //use object
    public void use(){
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
    //get in hand graphic
    public Image inHand(int n){
        return inHand[n];
    }  
}
