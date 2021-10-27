package src;



/**
 * Write a description of class Terrain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.awt.Image;
public class Terrain  //implements IWorldInterface
{
    // instance variables - replace the example below with your own
    ArrayList<Platform> platformList = new ArrayList<Platform>();
    private String name;
    private int objX,objY;
    private Image image;
    private int columns,height,width,frame;
    public Terrain()
    {
        /*
        needs to be 
         */
        /*
        Platform floor = new Platform(300,350,200);
        Platform floor2 = new Platform(10,400,200);
        Platform floor3 = new Platform(10,300,200);
        
        platformList.add(floor);
        platformList.add(floor2);
        platformList.add(floor3);
        */
    }

    /*
    private Platform checkPlatforms()
    {
    //init temp variables

    Platform nearestPlat = null;

    for (Platform current: platformList)
    {
    int x = player.getX()+30;
    //if player is in the current platform x domain
    if (x > current.getX() && x < (current.getX() + current.getLength()))
    {
    //if player is right above the current platform
    int y = player.getY();
    int feet = y + 50;
    int d =current.getY() - feet;
    //check the displacementd
    if (d < 10&& d >-10)
    {
    //success were near enough to platform to be on it
    nearestPlat = current;
    }
    }
    }
    return nearestPlat;
    }
     */

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

   // public int getBaseX();

   // public int getBaseY();

   // public int getCollisionX();

   // public int getCollisionY();

   // public int getCollisionXStart();

   // public int getCollisionYStart();

   // public boolean isVisible();
}
