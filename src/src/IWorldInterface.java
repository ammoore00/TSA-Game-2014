package src;


/**
 * Write a description of interface IWorldinterface here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.awt.Image;

public interface IWorldInterface
{

    public int isInteractive = 0;

    public String getName();

    public int getX();

    public int getY();

    public void setX(int x);

    public void setY(int y);

    public char getType();
    ///for use with sprites
    public Image getImage();

    public int getColumns();

    public int getHeight();

    public int getWidth();

    public int getFrame();

    public void act(ArrayList<IWorldInterface>  objs, Player p);

   // public int getBaseX();

   // public int getBaseY();

   // public int getCollisionX();

  // public int getCollisionY();

   // public int getCollisionXStart();

   // public int getCollisionYStart();

   // public boolean isVisible();
    
}


