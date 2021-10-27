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
public class Boxes extends Interactable
{
    private String name;
    private int objX;
    private int objY;
    private Image image;
    private int height = 32;
    private int width = 50;
    private String box = "Box1.png";
    private char type = 'b';
    private boolean isPushLeft;
    private boolean isPushRight;
    private boolean isY;
    private int velocity = 0;

    public Boxes(int x, int y, Player p, World w) {
        super(x, y, p, w);
        objX = x;
        objY = y;
        ImageIcon box1 = new ImageIcon(this.getClass().getResource(box));
        image = box1.getImage();
    }

    public Image getImage() {
        return image;
    }

    public void act() {
        if (isPlayerPushing())
            setVelocity((int)player.getVelX());

    }

    public int getX() {
        return objX;
    }

    public int getY() {
        return objY;
    }

    public char getType(){
        return type;
    }

    public boolean isPlayerPushing() {
        isPushRight = false;
        isPushLeft = false;
        isY = false;
       
        //right push
        if ((player.getX() >= objX + 20 && player.getX() <= objX + width - 25)) {
            isPushRight = true;
        }
        else {
            isPushRight = false;
        }
            
        //left push
        if ((player.getX() + 50 >= objX && player.getX() + 40 <= objX)) {
            isPushLeft = true;
        }
        else {
            isPushLeft = false;

        }

        
        
        
        
        
        if (player.getY() + player.getHeight() - 45 >= objY && player.getY() <= objY + height) {
            isY = true;
        }
        else {
            isY = false;
        }

        if ((isPushRight && isY) || (isPushLeft && isY)) {
            return true;
        }

        return false;

    }
    private boolean isLeft() {
        if (player.getX() <= objX)
            return true;
        return false;
    }

    private boolean isRight() {
        if (player.getX() >= objX)
            return true;
        return false;
    }

    private void setVelocity(int vel) 
    {

        if (isLeft() && vel > 0)
        {
            objX += vel;
        }
        else if (isRight() && vel < 0) {
            objX += vel;
        }

    }
     //public int getBaseX();

    //public int getBaseY();

    //public int getCollisionX();

    //public int getCollisionY();

    //public int getCollisionXStart();

    //public int getCollisionYStart();

    //public boolean isVisible();
}
