package src;


import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
@SuppressWarnings({ "unused" })
public class Spikes extends Interactable
{
    private String name;
    
    private Image image;
    private int startY = 100;
    private int startX = 100;
    private int spikeWidth = 60;
    private int spikeHeight = 57;
    private int frame = 1;
    private int columns = 1;
    private int height  = 57;
    private int width = 60;
    private String spikes = "spikesTEST.png";
    private char type = 's';
    private boolean isX;
    private boolean isY;
    
   public Spikes(int x, int y, Player p, World w) {
       super(x,y,p, w);
       startY = x;
       startX = y;
       ImageIcon spikesImage = new ImageIcon(this.getClass().getResource(spikes));
       image = spikesImage.getImage();  
       setImage(loadImg());
    }
    
    public Image getImage() {
        return image;
    }
    
    public void act(ArrayList<IWorldInterface>  objs, Player p) {
        if (isPlayerOnSpikes())
            player.setHealth(0);
        
    }
    
    public int getX() {
        return startX;
    }
    
    public int getY() {
        return startY;
    }
    
    public char getType(){
        return type;
    }
    
    public boolean isPlayerOnSpikes() {
        isX = false;
        isY = false;
        if (player.getX() <= startX + 30 && player.getX() + 45>= startX) {
            isX = true;
        }
        else {
            isX = false;
        }
        
        if (player.getY() + player.getHeight() - 45 >= startY && player.getY() <= startY + spikeHeight) {
            isY = true;
        }
        else {
            isY = false;
        }
        
        if (isX && isY) {
            return true;
        }
        
        return false;
        
        
    }
    
    public Image loadImg(){//load an image from 
        
         ImageIcon icon = new ImageIcon(this.getClass().getResource(spikes));
         Image img = icon.getImage();
        return img;
    }
    
    
    // public int getBaseX();

   // public int getBaseY();

  //
   // public int getCollisionY();

  //  public int getCollisionXStart();

  //  public int getCollisionYStart();

  //  public boolean isVisible();
}
