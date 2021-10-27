package src;


import java.awt.Image;
/**
 *Potion Class - Jason Cyrus
 */
import javax.swing.ImageIcon;
import java.util.ArrayList;
@SuppressWarnings({ "unused" })
public class Goal extends WObject implements IWorldInterface
{
 private String active = "Resources\\Images\\Objectives\\goalActive.png";
 private String inactive = "Resources\\Images\\Objectives\\goalInactive.png";
 private Image myImage;
 private String target;//artifact name were looking for
 private boolean open = true;//intially cant get through
 
    /**
     * THIS SHOULD EVENTUALLY DO SOMETHING MORE THAN JUST BE A PLACE HOLDER.
     */
   public Goal(int x,int y,String n,String t){//t =  target name of NAME NOW - Final Edit Jason Cyrus
       super(x*32,y*32,n);
       System.out.println(t);
       ImageIcon gIcon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Objectives\\" + t));
       Image gIMG = gIcon.getImage();
        myImage = gIMG;//initially set as active
       
        //set up sprite variables
        type = 'G';//THIS IS THE GOAL
        columns = 1;
        height = 50;
        width = 50;
        frame = 0;
    }
   public void act(ArrayList<IWorldInterface> objs, Player p){
      
    }
    //to set goal as active
    public void setActive(){
        myImage = loadImg(active);
        open = true;
    }
    //to see if its active
    public boolean isActive(){
        return open;
    }
    //if this is the right artifact
    public boolean isTarget(String s){
        if (s.equals(target)){
            return true;
        }
        else{
            return false;
        }
    }
    public void use(Player p){
       //NOTHING! wow!
    }
    public Image getImage(){
        return myImage;
    }
}
