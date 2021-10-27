package src;


import java.awt.Image;
/**
 *Potion Class - Jason Cyrus
 *
 */
import javax.swing.ImageIcon;
import java.util.ArrayList;
@SuppressWarnings({ "unused" })
public class Artifact extends WObject implements IWorldInterface
{
 private String iString;
 private Image myImage;
 int c;
 
    /**
     * The mother fluffing Artifact of truth
     */
   public Artifact(int x,int y,String img,String n){
       super(x,y,n);
       iString = img;
        myImage = loadImg(iString);
       // need to at least set this..
      // inHand[1]= myImage;
        //set up sprite variables
        type = 'A';//THIS IS THE ARTIFACT
        columns = 1;
        height = 50;
        width = 50;
        frame = 0;
    }
   public void act(ArrayList<IWorldInterface> objs, Player p){
      
    }
    //
    public void use(Player p){
      
       
    }
    public Image getImage(){
        return myImage;
    }
}
