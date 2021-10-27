package src;


import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *Falling OBJ 
 *
 */
@SuppressWarnings({ "unused" })
public class FallingObj extends Interactable implements IWorldInterface
{
    //name variable
   private String name;
   //image name
   private String iString = "Resources\\Images\\Entities\\rock.png";
   private Image image;
   private int fallDist;
   private int fallSpeed;
   //private int amountFell = 0;//initially 0
   private int startX,startY;
   private int endY;
   
    
   public FallingObj(int x, int y, Player p, World w,int eY,int speed) {
       super(x, y, p, w);
       startX = x;
       startY = y;
       objY = y;
       endY = eY;
       //Set the fall speed of everything and fall dist
       //fallDist = dist;
       fallSpeed = speed;
       //set image
       ImageIcon rock = new ImageIcon(this.getClass().getResource(iString));
       image = rock.getImage();
       //
       type = 'f';
       columns = 1;
       height = 40;
       width = 40;
       frame = 0;
      
    }
    
    public void act() {
       //System.out.println("rock falling");
        //increment rock down
       moveRock();
       //if has reached fall dist
       if (objY > endY){
           objY = startY;//put rock back up 
           //amountFell = 0; //reset
       }
        //if player is close enough to the rock
       if (world.distToPlayer(this) < 20){
           player.setHealth(0);//kill em yo
        }
    }
    private void moveRock(){
        //increment objY down by fallSpeed
        objY += fallSpeed;
        //amountFell += fallSpeed;
    }
     public void move(int y)
    {
        objY += y;
        //objY += y;
        startY += y;
        endY += y;
    }
    public void setY(int y){
        startY += y;
        
    }
    public int getY(int y){
        return startY;
    }
     public Image getImage() {
        return image;
    }
    
   
}