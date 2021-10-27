
package src;


import java.lang.Object;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings({ "unused" })
public class NPC implements IWorldInterface
{
    //Required Infomation
    private String name = "npc";
    private String player = "SpriteSheet.png";

    //Position
    private int x;
    private int y;
    public Player guy;

    //Movement
    private double velX = 0;
    private double velY = 0;
    private double velYPlat = 0;
    private double maxVel = 6;
    private int moveX;
    private int moveY;
    private boolean isDJ;
    private boolean isDJLand;
    private boolean moving;
    private boolean movingY;

    //Graphics
    private int playerSheetColumns = 33;
    private int playerHeight = 64;
    private int playerWidth = 96;
    private int frame = 0;
    private boolean drankUp;

    //Stats

    private boolean weaponInHand;
    //Platforms
    private boolean onPlat = false;
    private double slope;
    ArrayList<Platform> platformList;
    ArrayList<VerticalPlatform> vertPlatformList;
    ArrayList<Door> doorList;
    ArrayList<TiledPlatform> tileList;
    int platIntersectBound = 5;
    int lastX= 0;

    //Collisions
    private int collX;
    private int collY;
    private int collXStart;
    private int collYStart;
    private int baseX = 48;
    private int baseY = 64;

    //JASON-Physics/Platform
    private static double G = 3;//gravity
    private boolean canFall = true;
    ///
    ///
    private int jumpCount = 1;
    private Image image;
    //private boolean canFall = false;
    private int jumpValue = 0;
    private Timer jumpTimer;
    boolean canJump = true;
    //Braden
    private int startY = 0;
    private int startX = 0;
    private int health;
    private int maxhealth = 0;
    private char type = 'n';
    private boolean keyReleased;
    //
    //jason for holding object;
    WObject objInHand = null;//initially null
    //to hold the current artifact
    Artifact targetArt = null;
    //LAST DIRECTION FACING
    private int lastVel = 1;
    //invicibility
    private boolean invincibility = false;
    private int lives = 3;//start with 3
    private Board myBoard;
    Sound soundPlayer = new Sound();
    
    private int tileX;
    private int tileY;
    
    int c = 12;
    boolean attacking;
    
    private World world;
    //positive is to the right || negative to the left
    private int facing = 1;
    
    private boolean isSwimming, isClimbing, isDescending;
    
    public NPC(World wld,Board b,int inix,int iniy, int healthSet,int damage,int speed,boolean enemy,Player bob) {
    	myBoard = b;//give player board
        ImageIcon npcImage = new ImageIcon(this.getClass().getResource(player));
        image = npcImage.getImage();
        x = inix;
        y = iniy;
        
        maxVel = speed;
        world = wld;
        maxhealth = healthSet;
        health = maxhealth;
        guy = bob;
        
        name = "NPC_" + Math.random();
        //jumpTimer = new Timer(1, this);

    }
    //Jason used by board to set whether the guy should fall or not
    public void setFallValue(boolean f) {
        //will set his fall value depending if he is on the platform
        canFall = f;
    }
    //Jason removed isOnPlatform and now uses canFall
    
    public void act(ArrayList<IWorldInterface>  objs, Player p)
    {
        think(p);
        
        if (health == 0)
        	die();
        
        checkTileCollision();
        checkSlopedCollision();
        
        double tX = (x + baseX - world.getOffsetX())/32.0;
        double tY = (y + baseY - world.getOffsetY())/32.0;
        
        tileX = (int)Math.floor(tX);
        tileY = (int)Math.floor(tY);
        
        move();
    }
        
    public void move() {
    	if(!moving){
            x += velX;
        }

        if(!movingY){
            y += velY;
        }

        if (canFall)
        {
            //increment velocity
            if (velY < 8)
            {
                velY +=3;
            }
            else
            {
                velY = 8;//cap
            }

        }
        else if(checkLadders())
        {
        	if (isClimbing)
        		velY = -4;
        	else if (isDescending)
        		velY = 4;
        	else
        		velY = 0;
        }
        else if(checkWater())
        {
        	if (isSwimming)
        		velY = -3;
        	else
        		velY = 3;
        }
        else
        {
            if (keyReleased == false)
            {
                velY = velYPlat;
            }
            else
            {
                velY = 0;
            }
        }
        
        /**
        if(weaponInHand)
        {
        System.out.println("here");
        ImageIcon playerImage = new ImageIcon(this.getClass().getResource("playerSheetAx.png"));
        image = playerImage.getImage();
        }
         **/
        
        setFrame();
    }
    
    public void setFrame()
    {
    	if (!canFall) //If walking
		{
			if (velX < 0)
			{
				if (isDJLand) //Double Jump land (roll) frames 70-80
				{
					if (frame < 4)
						frame = 4;
					else if (frame > 13)
						frame = 13;
					else
					{
						if (frame < 13)
							frame++;
						else if(frame == 13)
						{
							frame = 18;
							isDJLand = false;
						}
					}
				}
				else //Normal Running frames 66-69
				{
					if (frame < 0)
						frame = 0;
					else if (frame > 3)
						frame = 3;
					else
					{
						if (frame < 3)
							frame++;
						else if(frame == 3)
						{
							frame = 0;
						}
					}
				}
			}
			else if (velX > 0)
			{
				if (isDJLand) //Double Jump land (roll) frames 334-343
				{
					if (frame < 217)
						frame = 226;
					else if (frame > 226)
						frame = 226;
					else
					{
						if (frame > 217)
							frame--;
						else if(frame ==217)
						{
							frame = 212;
							isDJLand = false;
						}
					}
				}
				else //Normal Running frames 330-333
				{
					if (frame < 161)
						frame = 164;
					else if (frame > 164)
						frame = 164;
					else
					{
						if (frame > 161)
							frame--;
						else if(frame == 161)
						{
							frame = 146;
						}
					}
				}
			}
			else if (velX == 0)
			{
				isDJLand = false;
				{
					if (lastVel < 0)
						if (attacking)
						{
							if (frame < 24)
							{
								frame = 24;
							}
							else if (frame > 32)
							{
								frame = 32;
							}
							else
							{
								if (frame < 32)
								{
									frame++;
								}
								else if (frame == 32)
								{
									frame = 18;
									attacking = false;
								}
							}
						}
						else
							frame = 18;
					else
						if (attacking)
						{
							if (frame < 132)
							{
								frame = 140;
							}
							else if (frame > 140)
							{
								frame = 140;
							}
							else 
							{
								if (frame > 132)
								{
									frame--;
								}
								else if (frame == 132)
								{
									frame = 146;
									attacking = false;
								}
							}
						}
						else
							frame = 146;
				}
			}
		}
		else //If in the air
		{
			if (velX < 0)
			{
				if (isDJ)
				{
					if (frame < 70)
						frame = 70;
					else if (frame > 79)
						frame = 70;
					else
					{
						if (frame < 79)
							frame++;
						else if(frame == 79)
						{
							frame = 82;
							isDJ = false;
							isDJLand = true;
						}
					}
				}
				else
				{
					frame = 82;
				}
			}
			else if (velX > 0)
			{
				if (isDJ)
				{
					if (frame < 217)
						frame = 226;
					else if (frame > 226)
						frame = 226;
					else
					{
						if (frame > 217)
							frame--;
						else if(frame ==217)
						{
							frame = 214;
							isDJ = false;
							isDJLand = true;
						}
					}
				}
				else
				{
					frame = 214;
				}
			}
			else if (velX == 0)
			{
				if (lastVel < 0)
    			{
    				if (isDJ)
    				{
    					if (frame < 70)
    						frame = 70;
    					else if (frame > 79)
    						frame = 70;
    					else
    					{
    						if (frame < 79)
    							frame++;
    						else if(frame == 79)
    						{
    							frame = 82;
    							isDJ = false;
    							isDJLand = true;
    						}
    					}
    				}
    				else
    				{
    					frame = 82;
    				}
    			}
    			else if (lastVel > 0)
    			{
    				if (isDJ)
    				{
    					if (frame < 217)
    						frame = 226;
    					else if (frame > 226)
    						frame = 226;
    					else
    					{
    						if (frame > 217)
    							frame--;
    						else if(frame ==217)
    						{
    							frame = 214;
    							isDJ = false;
    							isDJLand = true;
    						}
    					}
    				}
    				else
    				{
    					frame = 214;
    				}
    			}
			}
		}
    }

    public int getX() {
        return x;
    }

    public void setX(int sentX) {
        x = sentX;
    }

    public int getY() {
        return y;
    }

    public void setY(int sentY) {
        y = sentY;
    }

    public Image getImage() {
        return image;
    }

    public void keyPressedd(char key) {

        
        //JASON- messed with velocities
        if (key == 'A') {
            //if (velX > -10){
            //    velX-= 3;
            //}
            //else{

            velX = -maxVel;

            //}
            //player.equals("playerLeft.jpg");
            //image.setImage(this.getClass().getResource(player));
            keyReleased = false;
            facing = -1;
        }

        if (key == 'D') {
            //if (velX < 10){
            //    velX+=3;
            //}
            //else{

            velX = maxVel;
            //}
            //player.equals("playerRight.jpg");
            //image.setImage(this.getClass().getResource(player));
            keyReleased = false;
            facing = 1;
        }

        if (key == ' ') {
            if(canJump)
            {
                this.jump();
            }
            else
            {
            }

        }
        
        if (key == 'w')
        {
        	if (checkWater())
        		isSwimming = true;
        	if (checkLadders())
        		isClimbing = true;
        }
        
        if (key == 's')
        {
        	TiledPlatform t = checkNearbyTiles().get(2);
        	
        	if (t != null && (t.getTileType() == 3 || (t.getTileType() == 6)))
        	{
        		y += 3;
        	}
        	
        	if(checkLadders())
        		isDescending = true;
        }
        
        if (key == 'p')
        {
        	for (TiledPlatform t : tileList)
        	{
        		if (t.getPause() == false)
        			t.pause(true);
        		else
        			t.pause(false);
        	}
        }
    }
    
    public void keyReleasedd(char key) {
        

        if (key == 'A') {
            lastVel = (int)velX;//get last velocity before stop, use for direction facing
            velX = 0;
            keyReleased = true;
        }

        if (key == 'D') {
            lastVel = (int)velX;
            velX=0;
            keyReleased = true;
        }
        
        if (key == 'W')
        {
        	isSwimming = isClimbing = false;
        }
        
        if (key == 'S')
        {
        	isDescending = false;
        }
    }

    public void decelD() {
        if (velX > 0) {
            velX-=1;
            x += velX;
        }
    }

    public void decelA() {
        if (velX < 0) {
            velX+=1;
            x += velX;
        }
    }
    
    public void jump() 
    {
        if (onPlat)
        {
            isDJ = false;
            velY = -17;
            canFall = true;
        }
    }
    
    public void jump(int v) 
    {

        //Braden
        //Jason made it so can fall is the determining factor
        if (onPlat)
        {
            isDJ = false;
            velY = -v;
            //y -= 8;
            //jason make jump count work
            jumpCount = 1;
            canFall = true;
        }
        else if(jumpCount == 1) 
        {
            isDJ = true;
            velY = -v;
            //y -= 8;
            jumpCount = 0;
        }
    }
    
    //Braden- Will check if player is on a platform- hardcoded to start location is floor
    public boolean isOnPlatform()
    {
        if (!canFall)
            return false;
        jumpCount = 1;
        //y = startY;// dont need this anymore
        return true;
    }

    public int getColumns() {
        return playerSheetColumns;
    }

    public int getHeight() {
        return playerHeight;
    }

    public int getWidth() {
        return playerWidth;
    }

    public int getFrame() {
        return frame;
    }

    public String getName() {
        return name;
    }

    public char getType()
    {
        return type;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int h)
    {   //if hes losing health
        if (h<health){
            //if im vunerable
            if (!invincibility){
                health = h;
            }
        }
        else{
            health = h;
        }
        if (health > maxhealth)
        {
            health = maxhealth;
        }
        if (h < 0)
        {
        	health = 0;
        }
        //check death?
        die();
    }

    public void addHealth(int h)
    {
        health += h;
        if (health > maxhealth)
        {
            health = maxhealth;
        }
        
        if (health + h < 0)
        {
        	health = 0;
        }
    }

    public void setMaxHealth(int h)
    {
        maxhealth = h;
    }

    public int getMaxHealth(){
        return maxhealth;
    }

    public int getCollisionX()
    {
        return collX;
    }

    public int getCollisionY()
    {
        return collY;
    }

    public int getCollisionXStart()
    {
        return collXStart;
    }

    public int getCollisionYStart()
    {
        return collYStart;
    }

    public int getBaseX()
    {
        return baseX;
    }

    public int getBaseY()
    {
        return baseY;
    }

    public boolean isVisible()
    {
        return true;
    }

    public void setVelX(double vx)
    {
        velX = x;
    }

    public void setVelY(double vy)
    {
        velY = y;
    }

    public double getVelX()
    {
        return velX;
    }

    public double getVelY()
    {
        return velY;
    }

    public void kill() {
        y = 225;
        x = 200;
    }

    public void setVelYByPlatform(double vyp)
    {
        if (vyp > maxVel)
        {
            velYPlat = 0;
        }
        else
        {
            velYPlat = vyp;
        }
    }

    public void actionPerformed(ActionEvent e) {}
    //Code for object manipulation, Jason Cyrus
    //used by world to give player obj
    public void setObjInHand(WObject obj){

        objInHand = obj;//put obj in hand

    }
    //am i holding an object true or false?
    public boolean holdingObj(){
        if (objInHand == null){
            return false;
        }
        else{
            return true;
        }
    }
    //retrieve obj in guys hand
    public WObject getObjInHand(){
        return objInHand;
    }
    
	public void die(){
        /*//then oh dear im k-ed
        if (health <= 0){
            if (!invincibility) lives--;//if im a mortal being
            invincibility = true;//create period of invicibility
            myBoard.startInvincibility();//start it graphicallyFdi
            soundPlayer.play("Scream.wav");
        }
        if (lives < 0){
            //System.out.println("YOU SUCK! LOSER!");
            myBoard.restart();
        }*/
    }

    public void setArrayList(ArrayList<Platform> a)
    {
        platformList = a;    
    }

    public void setVertArrayList(ArrayList<VerticalPlatform> a)
    {
        vertPlatformList = a;    
    }

    public void setDoorArrayList(ArrayList<Door> a)
    {
        doorList = a;
    }

    public Platform getNearestPlat(){/*
    
    	
        for(Platform p: platformList)
        {
            //int x = guy.getX() + 50;
            //if player is within domain of platform
            if(x+baseX >= p.getX() && x+baseX <= p.getEndX())
            {
                slope = p.getSlope();
                //range of the platform

                if (slope < 0)
                {
                    if(y + baseY <= p.getYOnPlat(x + baseX) + platIntersectBound && y + baseY >= p.getYOnPlat(x + baseX) - platIntersectBound)
                    {
                        setFallValue(false);
                        onPlat = true;
                        //setY(p.getY());
                        //lastX = getX();
                        //velYPlat = p.getYOnPlat(getX());
                        velY = slope * velX;
                        return p;
                    }
                    else
                    {
                        setFallValue(true);
                        onPlat = false;
                    }
                }
                else if (slope > 0)
                {
                    if(y + baseY >= p.getYOnPlat(x + baseX) - platIntersectBound && y + baseY <= p.getYOnPlat(x + baseX) + platIntersectBound)
                    {
                        setFallValue(false);
                        onPlat = true;
                        //setY(p.getY());
                        //lastX = getX();
                        velY = slope * velX;
                        return p;
                    }
                    else
                    {
                        setFallValue(true);
                        onPlat = false;
                    }
                }
                else if(slope ==0)
                {
                    if((y + baseY) > p.getY() -10 && (y + baseY) < (p.getY() + 10))
                    //if((y + baseY) > p.getY() && (y + baseY) <(p.getY() + 10))

                    {

                        setFallValue(false);
                        onPlat = true;
                        //setY(p.getY());
                        lastX = getX();
                        //setY(p.getY() - baseY);
                        return p;
                    }
                    else
                    {
                        setFallValue(true);
                        onPlat = false;
                    }
                    // }
                    //  else
                    //      {
                    //         setFallValue(true);
                    //         System.out.println("Why do you skip you 4");
                    //     }

                }
                /**
                if(p.getYOnPlat(getX()) < (getY() + baseY + 25) && p.getYOnPlat(getX()) > (getY() + baseY - 25))
                {
                else
                {
                setFallValue(true);
                }

                }
                 **//*
            }
            else
            {
                setFallValue(true);
                onPlat = false;
            }

        }*/
        
        lastX = getX();
        return null;
    }

    public VerticalPlatform getNearestVertPlat()
    {   /*
        for(VerticalPlatform p: vertPlatformList)
        {
            if(x+baseX >= p.getX() - 5 && x+baseX <= p.getEndX() + 5)
            {
                if(y + baseY <= p.getY() && y + baseY >= p.getEndY()+5)
                {
                    canJump = false;
                    //lastX = getX();
                    return p;

                }

            }

        }*/
        
        return null;
    }

    public Door getNearestDoor()
    {/*
        for(Door d: doorList)
        {
            if(!d.isClosed())
            {
            }
            else
            {
                if(x + baseX >= d.getX()- 10 && x+ baseX <= d.getX() + 10)
                {
                    if(y+ baseY > d.getY()+5 && y+baseY < d.getY()+50)
                    {
                        return d;
                    }
                }
            }
        }*/
        
        return null;
    }

    public void weaponInHand(boolean i){
        weaponInHand = i;
    }

    public void moving(boolean i){
        moving = i;
    }

    public void movingY(boolean i){
        movingY = i;
    }
    
    public void setWorld(World w)
    {
    	world = w;
    }
    
    public World getWorld()
    {
    	return world;
    }
    
    /**Retrieves 3x3 grid of tiles near the player and returns an ArrayList, with index in the ArrayList indicating relative position*/
    public ArrayList<TiledPlatform> checkNearbyTiles()
    {
    	//indices for tiles in list
    	/////////
    	//8|0|5//
    	//3|4|1//
    	//7|2|6//
    	/////////
    	ArrayList<TiledPlatform> a = new ArrayList<TiledPlatform>();
    	
    	for (int i = 0; i < 9; i++)
    	{
    		a.add(null);
    	}
    	
    	for (TiledPlatform t : tileList)
    	{
    		int tX = t.getTileX();
    		int tY = t.getTileY();
    		
    		if ((tX == tileX && tY == tileY - 1)/*Check above*/)
    		{
    			a.set(0, t);
    		}
    		
    		if ((tX == tileX + 1 && tY == tileY)/*Check right*/)
    		{
    			a.set(1, t);
    		}
    		
    		if ((tX == tileX && tY == tileY + 1)/*Check below*/)
    		{
    			a.set(2, t);
    			//System.out.println("Found tile below!");
    		}
    		
    		if ((tX == tileX - 1 && tY == tileY)/*Check left*/)
    		{
    			a.set(3, t);
    		}
    		
    		if ((tX == tileX && tY == tileY)/*Check player's position*/)
    		{
    			a.set(4, t);
    		}
    		
    		if ((tX == tileX + 1 && tY == tileY - 1)/*Check top right*/)
    		{
    			a.set(5, t);
    		}
    		
    		if ((tX == tileX + 1 && tY == tileY + 1)/*Check bottom right*/)
    		{
    			a.set(6, t);
    		}
    		
    		if ((tX == tileX - 1 && tY == tileY + 1)/*Check bottom left*/)
    		{
    			a.set(7, t);
    		}
    		
    		if ((tX == tileX - 1 && tY == tileY - 1)/*Check top left*/)
    		{
    			a.set(8, t);
    		}
    	}
    	
    	return a;
    }
    
    /**handles all tile collisions except top surface of slopes, which is handled in a separate method getSlopedCollision()*/
    public void checkTileCollision()
    {
    	ArrayList<TiledPlatform> a = checkNearbyTiles();
    	
    	//Stores nearby tiles to individual variables
    	TiledPlatform up = a.get(0);
    	TiledPlatform right = a.get(1);
    	TiledPlatform down = a.get(2);
    	TiledPlatform left = a.get(3);
    	TiledPlatform center = a.get(4);
    	TiledPlatform toprt = a.get(5);
    	TiledPlatform botrt = a.get(6);
    	TiledPlatform botlft = a.get(7);
    	TiledPlatform toplft = a.get(8);
    	
    	//System.out.println(down);
    	//Lower collisions
    	
    	int ypos = (y + baseY - world.getOffsetY()) % 32;
    	
    	if (ypos < 0)
    		ypos += 32;
    	
    	if (down != null && down.getTileType() != 2 /*Check if it is a background tile*/ && down.getTileType() != 1 /*Check for slopes- special case*/ 
    			&& down.getTileType() != 4 && (down.getTileType() != 6 || down.getData() == 1) && down.getTileType() != 10 && ypos >= 24)
    	{
    		canFall = false;
    		onPlat = true;
    		
    		if (velY >= 0)
    		{
    			velY = 0;

    			y = down.getY() - baseY - 1;
    			
    			//If the tile is spikes, damage the player and launch them up
    			if (down.getTileType() == 5)
    			{
           			addHealth(-20);
           			jump(25);
    			}
    			
    			if (!down.getPause())
    			{
    				if (down.getTileType() == 7 || down.getTileType() == 9)
    					x += down.getVelX();
    				else if (down.getTileType() == 8)
    					x += down.getVelX() * down.getDir();
    			}
    		}
    		
    		//System.out.println("Colliding!");
    	}
    	else
    	{
    		canFall = true;
    		onPlat = false;
    	}
    	
    	//Checks bottom left to see if a moving platform is partially in one tile
    	if (down == null && botlft != null && (botlft.getTileType() == 7 || botlft.getTileType() == 8) && ((x + baseX + world.getOffsetX())) % 32 < botlft.getOffset())
    	{
    		canFall = false;
    		onPlat = true;
    		
   			if (botlft.getTileType() == 7)
   				x += botlft.getVelX();
   			else if (botlft.getTileType() == 8)
   				x += botlft.getVelX() * botlft.getDir();
    	}
    	
    	//Upper Collisions
    	if (up != null && up.getTileType() != 2 && up.getTileType() != 3 /*Allows passage through one way tiles*/ && up.getTileType() != 4 
    			&& up.getTileType() != 6 && up.getTileType() != 7 && up.getTileType() != 8 && up.getTileType() != 10)
    	{
    		velY = 0;
    		if (down == null || down.getTileType() == 2 || ypos < 24)
    		{
    			y += 8;
    		}
    	}
    	
    	if (center == null || (center.getTileType() != 1))
    	{
    		//Right Collisions
    		if (right != null && right.getTileType() != 2 && right.getTileType() != 3 /*Allows passage through one way tiles*/ 
    				&& right.getTileType() != 4 && right.getTileType() != 6 && right.getTileType() != 10 && Math.abs(right.getX() - x) < 59)
    		{
    			double s = 0;
    			int d = right.getData();
    		
    			if (d == 0)
    				s = 1;
    			if (d == 1)
    				s = -1;
    			if (d == 2)
    				s = .5;
    			if (d == 3)
    				s = .5;
    			if (d == 4)
    				s = -.5;
    			if (d == 5)
    				s = -.5;
    		
    			if (right.getTileType() != 1)
    			{
    				if (facing == 1)
    				{
    					x -= velX;
    					velX = 0;
    					
    					//isSliding = !keyReleased;
    				}
    			}
    		}
    		else if (left != null && left.getTileType() != 2 && left.getTileType() != 3 /*Allows passage through one way tiles*/
    				&& left.getTileType() != 4 && left.getTileType() != 6 && left.getTileType() != 10 && Math.abs(x - left.getX()) < 24)
    		{
    			double s = 0;
    			int d = left.getData();
    		
    			if (d == 0)
    				s = 1;
    			if (d == 1)
    				s = -1;
    			if (d == 2)
    				s = .5;
    			if (d == 3)
    				s = .5;
    			if (d == 4)
    				s = -.5;
    			if (d == 5)
    				s = -.5;
    		
    			if (left.getTileType() != 1)
    			{
    				if (facing == -1)
    				{
    					x -= velX;
    					velX = 0;

    					//isSliding = !keyReleased;
    				}
    			}
    		}
			else
			{
				//isSliding = false;
			}
    	}
    	
    	if (center != null && (center.getTileType() == 0 || center.getTileType() == 5))
    	{
    		//y -= (y + baseY + world.getOffsetY()) % 32 + 32;
    		y -= 20;
    	}
    	
    	if (checkWater() || checkLadders())
    	{
    		if (up != null && up.getTileType() == 4 || checkLadders())
    		{
    			canFall = false;
    			onPlat = false;
    		}
    		else
    		{
    			canFall = false;
    			onPlat = true;
    		}
    		
    		//Damages the player in lava
    		if (center.getTileType() == 10)
    		{
    			addHealth(-5);
    		}
    	}
    }
    
    public void checkSlopedCollision()
    {
    	//Calculates movement along sloped tiles
        TiledPlatform t = checkNearbyTiles().get(4);
        
        if (t != null && t.getTileType() == 1)
        {
    		double s = 0;
    		int d = t.getData();
    		
    		if (d == 0 || d == 6)
    			s = 1;
    		if (d == 1 || d == 7)
    			s = -1;
    		if (d == 2 || d == 8)
    			s = .5;
    		if (d == 3 || d == 9)
    			s = .5;
    		if (d == 4 || d == 10)
    			s = -.5;
    		if (d == 5 || d == 11)
    			s = -.5;
    		
    		int dx = x + baseX - t.getX();
    		int dy = y + baseY - t.getY();
    		
    		int yt = 0;
    		
    		if (s < 0)
    		{
    			yt = -(int)(dx * s);
    			
    			if (d == 5)
    				yt += 16;
    		}
    		else
    		{
    			yt = (int)((32 - dx) * s);
    			
    			if (d == 3)
    				yt += 16;
    		}
    		
    		boolean collide = (yt - dy <= 16);
    		
    		if (t.getData() < 6 || !isSwimming)
    		{
    			if (collide)
    			{
    				canFall = false;
    				onPlat = true;
    				y = t.getY() - baseY + yt - 3;
    			}
    			else
    			{
    				canFall = true;
    				onPlat = false;
    			}
    		}
        }
    }
    
    public boolean checkWater()
    {
    	TiledPlatform t = checkNearbyTiles().get(4);
    	
    	boolean isWater = false;
    	
    	if (t != null && (t.getTileType()  == 4 || (t.getTileType() == 3 && (t.getData() == 1 || t.getData() == 2)) || (t.getTileType() == 1 && t.getData() > 5)))
    		isWater = true;
    	
    	return isWater;
    }
    
    public boolean checkLadders()
    {
    	TiledPlatform t = checkNearbyTiles().get(4);
    	
    	return t != null && t.getTileType()  == 6;
    }
    
    public ArrayList<TiledPlatform> getTileList()
    {
    	return tileList;
    }
    
    public void setTileList(ArrayList<TiledPlatform> a)
    {
    	tileList = a;
    }
	
    public void damage(int d){
        setHealth(getHealth() - d);
    }
       
	public void setImage(String img) 
	{
		ImageIcon NPCImage = new ImageIcon(this.getClass().getResource("Resources\\Images\\Entities\\" + img));
        image = NPCImage.getImage();
	}
	
	public void think(Player p)
	{
		int dist = world.distToPlayer2(this);
		int dx = guy.getX() - x;
		int dy = guy.getY() - y;
		
		ArrayList<TiledPlatform> a = checkNearbyTiles();
    	
    	//Stores nearby tiles to individual variables
    	TiledPlatform up = a.get(0);
    	TiledPlatform right = a.get(1);
    	TiledPlatform down = a.get(2);
    	TiledPlatform left = a.get(3);
    	TiledPlatform center = a.get(4);
    	TiledPlatform toprt = a.get(5);
    	TiledPlatform botrt = a.get(6);
    	TiledPlatform botlft = a.get(7);
    	TiledPlatform toplft = a.get(8);
		
		if (dist < 256 && dist > 30)
		{
			if (dx > 0)
			{
				velX = 4;
				lastVel = 1;
				
				if (down != null && down.getTileType() != 1 && ((botrt == null || botrt.getTileType() == 2)
						|| (right != null && right.getTileType() != 2 && right.getTileType() != 1) || (dy < 0 && toprt != null && toprt.getTileType() != 2
						&& toprt.getTileType() != 1)))
				{
					jump();
				}
			}
			else
			{
				velX = -4;
				lastVel = -1;
				
				if (down != null && down.getTileType() != 1 && ((botlft == null || botlft.getTileType() == 2)
						|| (left != null && left.getTileType() != 2 && left.getTileType() != 1) || (dy < 0 && toplft != null && toplft.getTileType() != 2
						&& toplft.getTileType() != 1)))
				{
					jump();
				}
			}
		}
		else
		{
			velX = 0;
		}
		
		if (dist <= 30)
		{
			actOnPlayer(p);
		}
	}
	
	private void actOnPlayer(Player p)
    {
        if (c >= 12)
        {
            attacking = true;
        	if (c >= 15)
        	{
        		int rand = (5 + (int)(Math.random()*5));
            	p.addHealth(-rand);
            	c = 0;
        	}
            else 
            {
                c ++;
            }
        }
        else 
        {
            c ++;
        }
    }
}