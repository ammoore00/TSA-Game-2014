package src;


import java.awt.*;
import java.util.ArrayList;
@SuppressWarnings({ "unused" })
public class Interactable implements IWorldInterface
{
    private String name;
    public Player player;
    public int objX,objY;
    private Image image;
    private boolean above;
    private boolean left;
    public int columns,height,width,frame;
    private int spikeWidth;
    private int spikeHeight;
    private int rotationDown = 0;
    private int rotationUp = 180;
    public World world;
    public char type = 'i';

    public Interactable(int x, int y, Player p, World w)
    {
        setX(x);
        setY(y);
        player = p;
        world = w;

    }

    //checks if object is to the right of the player    
    public boolean isOnLeft() {
        if (player.getX() < this.getX())
            return true;
        return false;

    }
    //checks if object is to the right of the player
    public boolean isOnRight() {
        if (player.getX() > this.getX())
            return true;
        return false;
    }
    //checks if object is below the player
    public boolean isBelow() {
        if (player.getY() < this.getY())
            return true;
        return false;

    }
    //checks if object is above the player
    public boolean isAbove() {
        if (player.getY() > this.getY())
            return true;
        return false;
    }

    public Image getImage() {
        return image;
    }

    public boolean xEqualToPlayer(int width) {
        if (isOnRight() && player.getX() + player.getWidth() == this.getX()) {
            return true;
        }
        else if (isOnLeft() && player.getX() >= this.getX() && player.getX() <= this.getX() + this.getWidth()) {
            return true;
        }
        else {
            return false;
        }

    }

    public boolean yEqualToPlayer(int height) {
        if (isAbove() && player.getY() + player.getHeight() == this.getY()) {
            return true;
        }
        else if (isBelow() && player.getY() == this.getY()+ height) {
            return true;
        }
        else {
            return false;
        }
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
      //System.out.println("DX:   -----" + x);
        objX = x;
    }

    public void setY(int y){
        objY = y;
    }

    public void setName(String n) {
        name = n;
    }

    public void setImage(Image image1) {
        image = image1;
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

    /**
    public void createSpikes() {
    Interactable one = new Spikes(objX, objY, player);
    }

    public void createDoor() {
    Interactable one = new Door(objX, objY, player);
    }

    public void createBox() {
    Interactable one = new Boxes(objX, objY, player);
    }
     */
    public char getType(){
        return type;
    }

    public void act(ArrayList<IWorldInterface>  objs, Player p) {

    }

    //public int getBaseX();

   // public int getBaseY();

  //  public int getCollisionX();

   // public int getCollisionY();

  //  public int getCollisionXStart();

  //  public int getCollisionYStart();

  //  public boolean isVisible();

}
