package src;



import java.awt.Image;
/**
 *Range Class - Jason Cyrus
 */
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Point;
@SuppressWarnings({ "unused" })
public class Ranged extends WObject implements IWorldInterface
{
 private String iString = "Resources\\Images\\Items\\bow.png";
 private String ammoName = "Resources\\Images\\Entities\\arrow.png";
 private Image myImage;

 int attack = 50;//30 health
// Projectile ammo;//what he fires
 int magazine;//amount of bullets/arrow
 //speed at which projectile flies off
 int fireVel;
 //damage the arrow does
 int damage;

    // instance variables - replace the example below with your own
   public Ranged(int x,int y,int clip,int vel,int d,String n){
         
       super(x,y,n);
      //load pic
        myImage = loadImg(iString);
       
        //set up sprite variables
        columns = 1;
        height = 45;
        width = 45;
        frame = 0;
        //
        type ='r';//r for RANGED ATTACK OBJECTS
        magazine = clip;
        fireVel =  vel;
        damage = d;
    }
   
   public void act(ArrayList<IWorldInterface> objs, Player p){
      //apparently notting
    }
     //use object
    public void use(World w,int x,int y,int dir){//what world were in
        //System.out.println("using " + getName());
       
        if (magazine >0){//if there are any arrows left
            int v = 0;
           if (dir >= 0){
               v = 1;
            }
            else{
                v = -1;
            }
            
            Point vel = new Point(fireVel * v,0);
           
            Projectile arrow =  new Projectile(loadImg(ammoName),new Point(x,y),vel,damage);///x is our projectile
            if(w.addProjectile(arrow)){
            magazine--;//decrement ammo
        }
        }
       
    }
   //for our sexy hud method
    public int getAmmo(){
        return magazine;
    }

    public Image getImage(){
        return myImage;
    }
   
  
    
}