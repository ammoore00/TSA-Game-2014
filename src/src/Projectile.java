package src;

import java.awt.Image;
import java.awt.Point;
/**
 /Projectile - Jason Cyrus
 */
public class Projectile 
{
    private Image myImg;
    Point position;//my position
    Point velocity;//my velocity
    int damage;
    public Projectile(Image pic,Point pos,Point vel,int d)
    {
      position =  pos;
      velocity = vel;
      damage = d;
    }
    //increment off velocity
    public void increment(){
        position.translate((int)velocity.getX(),(int)velocity.getY());//move prjtile
    }
    public int getX()
    {
        return (int)position.getX();
    }
    public int getY()
    {
        return (int)position.getY();
    }
    public int getDamage(){
        return damage;
    }
    public Image getImage(){
        return myImg;
    }
    public Point getVel(){
        return velocity;
    }
}
