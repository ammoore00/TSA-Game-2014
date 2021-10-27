package src;


import java.awt.Image;
import javax.swing.ImageIcon;
/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
@SuppressWarnings({ "unused" })
public class Door extends Interactable
{
    private String name;
    private int objX = 200;
    private int objY = 100;
    private Image image;
    private int columns = 2;
    private int height = 50;
    private int width = 20;
    private int frame = 0;
    private String doorImages = "Resources\\Images\\Interactables\\DoorSprite.png";
    private char type = 'd';
    private boolean doorIsOpen = false;
    private boolean doorIsClosed = false;
    private boolean locked = false;
    private String combo;
    private int order;
    
   public Door(int x, int y, Player p, World w, boolean lock, String n, String c, int o) {
        super(x, y, p, w);
        objX = x;
        objY = y;
        name = n;
        order = o;
        combo = c;
        ImageIcon door1 = new ImageIcon(this.getClass().getResource(doorImages));
        image = door1.getImage();
        doorIsClosed = true;
        locked = lock;
        setImage(loadImg());
    }
    
     public void switchImage(boolean active, boolean active2) {
        //closes door
        
        if (isDoorOpen() && active == true && active2 == false) {
            frame = 0;
            //System.out.println("Door Closed");
            doorIsOpen = false;
            doorIsClosed = true;
            locked = false;
        }
        
        else if (isDoorClosed() && locked == false) {
            frame = 1;
           //System.out.println("Door Opened");
            doorIsOpen = true;
            doorIsClosed = false;

        }

        else if (isDoorClosed() && locked == true && active == true) {
            frame = 1;
            locked = false;
            doorIsOpen = true;
            doorIsClosed = false;
            active = false;
        }
        
    }
    public String getName() {
        return name;
    }
    
    private boolean isDoorOpen() {
        return doorIsOpen;
    }
    
    private boolean isDoorClosed() {
        return doorIsClosed;
    }
    
     public Image getImage() {
        return image;
    }
    
    public char getType(){
        return type;
    }
    
    public boolean isClosed() {
        if (doorIsClosed)
            return true;
        return false;
    }
    
    public Image loadImg(){//load an image from 
        
         ImageIcon icon = new ImageIcon(this.getClass().getResource(doorImages));
         Image img = icon.getImage();
        return img;
    }
    
    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getFrame() {
        return frame;
    }

    public int getColumns() {
        return columns;
    }
    
    public String getCombo() {
        return combo;
    }
    
    public int getOrder() {
        return order;
    }
        
   //  public int getBaseX();

   // public int getBaseY();

   // public int getCollisionX();

   // public int getCollisionY();

  //  public int getCollisionXStart();

   // public int getCollisionYStart();

  //  public boolean isVisible();
}
