package src;


import java.awt.Image;
/**
 *Potion Class - Jason Cyrus
 */
import javax.swing.ImageIcon;
import java.util.ArrayList;
public class Potion extends WObject implements IWorldInterface
{
 private String iString = "Resources\\Images\\Items\\heart.png";
 private Image myImage;
 int c;
 
    // instance variables - replace the example below with your own
   public Potion(int x,int y,String n){
       super(x,y,n);
        ImageIcon pIcon = new ImageIcon(this.getClass().getResource(iString));
        myImage = pIcon.getImage();
       // need to at least set this..
       inHand[1]= myImage;
        //set up sprite variables
        type = 'h';
        columns = 1;
        height = 25;
        width = 25;
        frame = 0;
    }
   public void act(ArrayList<IWorldInterface> objs, Player p){
      
    }
    //
    public void use(Player p){
        p.addHealth(20);//give player 20 health
        //System.out.println("20 health added");
    }
    public Image getImage(){
        return myImage;
    }
}
