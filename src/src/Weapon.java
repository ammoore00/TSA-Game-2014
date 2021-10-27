package src;


import java.awt.Image;
/**
 *Weapon Class - Jason Cyrus
 */
import javax.swing.ImageIcon;
import java.util.ArrayList;
@SuppressWarnings({ "unused" })
public class Weapon extends WObject implements IWorldInterface
{
 private String iString = "Resources\\Images\\Items\\SWORD.png";
 private Image myImage;
 private Image[] inHand = new Image[3];//3 positions
 int c;
 int attack = 30;//30 health
 int AtkRange = 50;//50 pix range
 //

    // instance variables - replace the example below with your own
   public Weapon(int x,int y,int a,int r,String n){
       	super(x,y,n);
        ImageIcon pIcon = new ImageIcon(this.getClass().getResource(iString));
        myImage = pIcon.getImage();
       
        //set up sprite variables
        columns = 1;
        height = 50;
        width = 50;
        frame = 0;
        type ='w';//w for weapon
        attack =  a;
        AtkRange = r;
    }
    /*
    //just use default
    public Weapon(int x,int y,String n){
       //super(x,y,n);
        ImageIcon pIcon = new ImageIcon(this.getClass().getResource(iString));
        myImage = pIcon.getImage();
        //must set to something
        
        //set up sprite variables
        columns = 1;
        height = 50;
        width = 50;
        frame = 0;
        type ='w';//w for weapon
    }
    */
   public void act(ArrayList<IWorldInterface> objs, Player p){
      //apparently notting
    }
     //use object
    public void use(){
    }
    public Image getImage(){
        return myImage;
    }
    public int range(){
        return AtkRange;
    }
    public int attackP(){
        return attack;
    }
    
}