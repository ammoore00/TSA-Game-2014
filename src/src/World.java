package src;


import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.lang.Object.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
//import org.apache.commons.lang.*;
@SuppressWarnings({ "unused" })
public class World
{
    //levels data file
    private String filePath;
    //defines player
    public Player guy;
    //
    public ArrayList<String> combo1 = new ArrayList<String>();
    public ArrayList<String> combo2 = new ArrayList<String>();
    public ArrayList<String> combo3 = new ArrayList<String>();
    public ArrayList<String> combo4 = new ArrayList<String>();
    public ArrayList<String> combo5 = new ArrayList<String>();
    //list of all possible objects
    public ArrayList<IWorldInterface> worldObjects = new ArrayList<IWorldInterface>();
    public ArrayList<IWorldInterface> activeObjects = new ArrayList<IWorldInterface>();
    public ArrayList<Lever> leverObjects = new ArrayList<Lever>();
    public ArrayList<Door> doorObjects = new ArrayList<Door>();
    //falling obj
    public ArrayList<FallingObj> fallingStuff = new ArrayList<FallingObj>();
    //Objects that player can work with
    public ArrayList<WObject> worldDrops= new ArrayList<WObject>();
    ArrayList <Platform> platformList = new ArrayList<Platform>();
    ArrayList <VerticalPlatform> vertPlatformList = new ArrayList<VerticalPlatform>();
    ArrayList <TiledPlatform> tileList = new ArrayList<TiledPlatform>();
    ArrayList<TiledPlatform> signList = new ArrayList<TiledPlatform>();
    //List of all NPCS
    public ArrayList<NPC> npcList = new ArrayList<NPC>();
    //PROJECTILES!
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    //last attacked npc
    private NPC currentEnemy = null;
    private Goal endGoal = null;
    //jason goal stuff
    private String goalName;
    private Image goal_image;
    private Artifact endArtifact = null;
    //OVERSEEING BOARD CLASS WE BELONG TOO
    Board myBoard;
    String level;
    ///cool down for weapons
    int coolDown = 3;//250 paint cycles
    
    private int tileOffsetX;
    private int tileOffsetY;
    
    private int length;
    //define the world
    //ArrayList<List<String>> tempLevel = new ArrayList<List<String>>();//creates the list that will hold all the objects being defined line by line on the
    //world constructor text
    //define the board to paint on
    //http://www.javaprogrammingforums.com/file-i-o-other-i-o-streams/5856-reading-lines-text-file-into-string-array.html
    /**
     * Everything that is necessary to start a level should be stored here.
     */
    public World(int worldNum,Board b){
        //add the player to the board
        myBoard = b;//set myBoard to current board
        guy = new Player(this,b);
        initializeLists();//INIT THE DAMN ARRAYLISTS
        //f
        //Decide which world file to load
        level = ("world_" + worldNum + ".png");
        goalName = ("world" + worldNum +".png");
        ////
        /////ADD all things into our list of world objects.
        ///change later to implement the other classes
        ///////TO ADD OBJECTS BASED ON A TEXT FILE.
        
        try 
        {
        	InputStream fstream_school = getClass().getClassLoader().getResourceAsStream(level);
            //FileInputStream fstream_school = new FileInputStream(level); //LOAD THE CORRESPONDING LEVEL
            DataInputStream data_input = new DataInputStream(fstream_school);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
            String str_line; 
            //System.out.println("Rendering world please wait...");
            while ((str_line = buffer.readLine()) != null) 
            { 
                ///read each line

                //System.out.println(str_line);
                //pass the potential object
                addObject(str_line);

            }
        }
        catch (Exception e)
        {//Catch exception if any
            //System.out.println("Error: " + e.getMessage());
        }
        
        //Bitmap reader
        try
        {
        	//Gets image
        	InputStream stream = getClass().getClassLoader().getResourceAsStream(level);
        	ImageInputStream imageStream = ImageIO.createImageInputStream(stream);
        	BufferedImage image = ImageIO.read(imageStream);
        	
        	//Gets size of the image
        	int numX = image.getTileWidth();
        	int numY = image.getTileHeight();
        	
        	length = numX;
        	
        	int signNum = 0;
        	
        	int startX = 0;
        	int startY = 0;
        	
        	//System.out.println("Size = (" + numX + ", " + numY + ")");
        	
        	//Arrays to store data
        	Color[][] colors = new Color[numX][numY];
        	
        	//Adds background tiles behind non-tile objects
        	for (int y = 0; y < numY; y++)
            {
                for (int x = 0; x < numX; x++)
                {
                	//Gets color
                    int color = image.getRGB(x, y);
                    
                    Color c = new Color(color);
                    
                    colors[x][y] = c;
                    
                    int red = colors[x][y].getRed();
                    int green = colors[x][y].getGreen();
                    int blue = colors[x][y].getBlue();
                    
                    if (red == 255 && green != 255)
                    {
                    	if (red == 255)
                    	{
                    		startX = x - 16;
                    		startY = y - 12;
                    	}
                    	
                    	//tileOffsetX = x - 16;
                    	//tileOffsetY = y - 12;
                    	if (blue != 0)
                    	{
                    		if (blue < 251)
                    		{
                    			IWorldInterface iwi = addObjectFromImage(x - startX, y - startY, 24, 0, blue);
                    		}
                    		else
                    		{
                    			addObjectFromImage(x - startX, y - startY, 40, 0, blue);
                    		}
                    	}
                    }
                }
            }
            
            //Searches through image pixel by pixel and adds objects
            for (int y = 0; y < numY; y++)
            {
                for (int x = 0; x < numX; x++)
                {
                	//Gets color
                    int color = image.getRGB(x, y);
                    
                    Color c = new Color(color);
                    
                    colors[x][y] = c;
                    
                    int red = colors[x][y].getRed();
                    int green = colors[x][y].getGreen();
                    int blue = colors[x][y].getBlue();
                    
                    if (red % 8 != 0 && green != 255)
                    {
                    	if (red == 255)
                    	{
                    		startX = x - 16;
                    		startY = y - 12;
                    	}
                    	
                    	//tileOffsetX = x - 16;
                    	//tileOffsetY = y - 12;
                    	if (blue != 0)
                    	{
                    		if (blue < 251)
                    		{
                    			IWorldInterface iwi = addObjectFromImage(x - startX, y - startY, 24, 0, blue);
                    		}
                    		else
                    		{
                    			addObjectFromImage(x - startX, y - startY, 40, 0, blue);
                    		}
                    	}
                    }
                    
                    if (!(red == 255 && green == 255 && blue == 255))//if the pixel isn't white
                    {
                    	IWorldInterface iwi = addObjectFromImage(x - startX, y - startY, red, green, blue);
                    	
                    	if (iwi.getType() == 't' && ((TiledPlatform)iwi).getTileType() == 13)
                    	{
                    		((TiledPlatform)iwi).setNum(signNum);
                    		signNum++;
                    	}
                    }
                }
            }
            
            System.out.println("Objects added to world");
        }
        catch (IllegalArgumentException e)
        {
        	//System.out.println("Error: " + e);
        }
        catch (IOException e)
        {
        	//System.out.println("Error: " + e);
        }
        
        
        
        /////////////
        ////////
        ///Create objects based on info
        guy.setArrayList(platformList);
    }
    
    public IWorldInterface addObjectFromImage(int x, int y, int r, int g, int b)
    {
    	IWorldInterface iwi = null;
    	
    	switch (r)
    	{
    		
    		//Player
    		case 255:
            	guy.setX(x * 32 - 48);
            	guy.setY(y * 32 + 56);
            	iwi = guy;
    		break;
    		
    		//Tiled Platform
    		case 8:case 16:case 24:case 32:case 40:case 48:case 56:case 64:case 72:case 80:case 88:case 96:case 104:case 112:
    			TiledPlatform tile = new TiledPlatform(x, y, r/8 - 1, g, b, "");
            	worldObjects.add(tile);
            	tileList.add(tile);
            	
            	if (r == 112)
            	{
            		signList.add(tile);
            	}
            	
            	iwi = tile;
    		break;
    		
    		//goal
    		case 250:
    			//yep the goal
    			if (g == 200){
    				Goal thisGoal = new Goal(x,y,"end_goal",goalName);
                    worldObjects.add(thisGoal);

                    endGoal = thisGoal;//set this levels goal
                    
                    iwi = thisGoal;
    			}
    		break;

    		//NPC
    		case 42:
    			NPC npcTemp = new NPC(this,myBoard,x*32,y*32,100, 1,100, true, guy);
    			worldObjects.add(npcTemp);
                npcList.add(npcTemp);
                
                iwi = npcTemp;
    		break;
    	}
    	
    	return iwi;
    }

    public Player getPlayer(){
        return guy;
    }
    
    public int getLength()
    {
    	return length;
    }
    
    public Board getBoard()
    {
    	return myBoard;
    }
    
    /**
     * Responsible for handling all of the updates for the world
     * !!!!DOES NOT PAINT ANYTHING!!!!
     */
    public void updateWorld()//Probably more for camera movement, or really any object with a variable draw x,y
    {
        guy.setVertArrayList(vertPlatformList);
        guy.setArrayList(platformList);
        guy.setDoorArrayList(doorObjects);
        guy.setTileList(tileList);
        
        guy.setWorld(this);
        
        guy.move();
        guy.act(worldObjects, guy);
        checkNPClives();
        //Give npcs array lists of platforms.
        for(int i = 0; i <worldObjects.size();i++)
        {
            if(worldObjects.get(i).getType() == 'n')
            {
                ((NPC)worldObjects.get(i)).setArrayList(platformList);
                ((NPC)worldObjects.get(i)).setVertArrayList(vertPlatformList);
                ((NPC)worldObjects.get(i)).setDoorArrayList(doorObjects);
                ((NPC)worldObjects.get(i)).setTileList(tileList);
            }
        }
        //System.out.println(worldObjects.get(0));
        //PUT IN REDRAW METHODS FOR WORLD OBJECTS
        //loops through all opjects and calls act
        activeObjects = getActiveObjects(worldObjects);
        for( int i =0; i < activeObjects.size();i++){
            int range = distToPlayer(activeObjects.get(i));
            
            if (activeObjects.get(i).getType() != 't' || (((TiledPlatform)activeObjects.get(i)).getTileType() == 4 || ((TiledPlatform)activeObjects.get(i)).getTileType() == 3 || ((TiledPlatform)activeObjects.get(i)).getTileType() >= 7))
            	activeObjects.get(i).act(activeObjects,guy);
            if (activeObjects.get(i).getType() == 'f'){
                ((FallingObj)activeObjects.get(i)).act();
            }
            //System.out.println("Done acting");
        }
        
        myBoard.setDrawLock(false);
        //update platforms
        platformList.clear();
        vertPlatformList.clear();
        leverObjects.clear();
        doorObjects.clear();
        fallingStuff.clear();
        for(int i =0; i < worldObjects.size(); i++){
            if(worldObjects.get(i).getType() == 'p'){
                platformList.add(((Platform)worldObjects.get(i)));
            }
            if(worldObjects.get(i).getType() == 'v')
            {
                vertPlatformList.add(((VerticalPlatform)worldObjects.get(i)));
            }
            if(worldObjects.get(i).getType() == 'l'){
                leverObjects.add(((Lever)worldObjects.get(i)));
            }
            if(worldObjects.get(i).getType() == 'd'){
                doorObjects.add(((Door)worldObjects.get(i)));
            }
            if(worldObjects.get(i).getType() == 'f'){
                fallingStuff.add((FallingObj)worldObjects.get(i));
            }
        }
        checkCamera();
        //move projectiles
        moveProjectiles();
        //see if there is heart collisions
        checkPotions();
        //check to see if we won
        checkGoal();
        //cooldown control
        if (coolDown <= 7){
            //System.out.println(coolDown);
            coolDown++;
        }
    }

    /**
     * we should dynamically assign a random name to objects instead of relying on hardcoded stuff in the .txt
     * 
     */
    public void addObject(String obj){
        //will find amount of params the certain object needs based on data separations
        int paramCount = 0;
        for (int i = 0; i < obj.length(); i ++) {
            if (obj.charAt(i) == ',') {//count commas m
                paramCount++;
            }
        }
        if (paramCount > 0) {//if its valid
            //split into its parameters
            String[] newObj = new String[paramCount];
            newObj = obj.split(",");//split object into its parameter components
            char id = newObj[0].charAt(0);//first entry is always the object type
            // System.out.println(newObj.length);
            switch(id){//depending on what type of object determines what block of code we want//////////////////////JASON for using Name ---------->last thing= name
                case 'q':
                    System.out.println("file loaded!");
                break;
                
                case 't':
                	//TiledPlatform tile = new TiledPlatform(Integer.parseInt(newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Integer.parseInt(newObj[6]),newObj[7]);
                	//worldObjects.add(tile);
                	//tileList.add(tile);
                break;
                
                case 'P'://player cordinates.
                	guy.setX(Integer.parseInt(newObj[1]) * 32);
                	guy.setY(Integer.parseInt(newObj[2]) * 32);
                break;
                case 'p': worldObjects.add(new Platform((newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Double.parseDouble(newObj[6])));//,Integer.parseInt(newObj[7])));//,Integer.parseInt(newObj[8]),Integer.parseInt(newObj[9])));
                platformList.add(new Platform((newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Double.parseDouble(newObj[6])));//,Integer.parseInt(newObj[7])));//,(newObj[7]),Integer.parseInt(newObj[8]),Integer.parseInt(newObj[9])));
                //System.out.println("found Platform!");
                break;

                case 'v':worldObjects.add(new VerticalPlatform((newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Double.parseDouble(newObj[6])));//,(newObj[7]),Integer.parseInt(newObj[8]),Integer.parseInt(newObj[9])));
                vertPlatformList.add(new VerticalPlatform((newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Double.parseDouble(newObj[6])));//,(newObj[7]),Integer.parseInt(newObj[8]),Integer.parseInt(newObj[9])));
                //System.out.println("found Vert Platform!");
                break;
                case 'n':
                if( newObj[1].charAt(0)=='b'){
                    //NPCBird npcBirdTemp = new NPCBird(Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Boolean.parseBoolean(newObj[4]),guy,Integer.parseInt(newObj[5]),Integer.parseInt(newObj[6]),newObj[newObj.length-1]);
                    //worldObjects.add(npcBirdTemp);
                    //npcList.add(npcBirdTemp);
                    //System.out.println("Found NPC Bird!");
                }
                else{
                    //NPC npcTemp = new NPC(Integer.parseInt(newObj[1]),Integer.parseInt(newObj[2]),Boolean.parseBoolean(newObj[3]),guy,Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Integer.parseInt(newObj[6]),newObj[newObj.length-1]);
                    //worldObjects.add(npcTemp);
                    //npcList.add(npcTemp);
                    //System.out.println("Found NPC!");

                }
                // worldObjects.add(new NPC(Integer.parseInt(newObj[1]),Integer.parseInt(newObj[2]),Boolean.parseBoolean(newObj[3]),guy,Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5])));
                // System.out.println("Found NPC!");
                break;

                case 'b': //worldObjects.add(new Boxes(Integer.parseInt(newObj[3]), Integer.parseInt(newObj[2]), guy, this));
                //System.out.println("Found Box!");
                break;

                case 'l': //System.out.println("Found Lever!");
                Lever levTemp = new Lever (Integer.parseInt(newObj[2]), Integer.parseInt(newObj[3]), guy, this, newObj[4], Integer.parseInt(newObj[5]), Integer.parseInt(newObj[6]));
                worldObjects.add(levTemp);
                leverObjects.add(levTemp);
                // System.out.println("Found Lever!");
                break;

                case 's': worldObjects.add(new Spikes(Integer.parseInt(newObj[3]), Integer.parseInt(newObj[2]), guy, this));

                //System.out.println("Found Spike!");

                break;

                //doors

                case 'd': 
                boolean locked = false;
                if (newObj[4].equals("locked")) {
                    locked = true;
                }
                Door doorTemp = new Door(Integer.parseInt(newObj[2]), Integer.parseInt(newObj[3]), guy, this, locked, newObj[1], newObj[5], Integer.parseInt(newObj[6]));
                //System.out.println(newObj[1]);
                worldObjects.add(doorTemp);
                doorObjects.add(doorTemp);
                // System.out.println("Found Door!");
                break;
                // objects
                case 'o'://1/12/2014 added code to add to worldDrops aswell
                //check to see if obj
                //System.out.println("object found");
                if (newObj[1].charAt(0) == 'p'){//see what type of object
                    //System.out.println("found a potion");                                             //name of potion
                    Potion p = new Potion(Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),newObj[newObj.length-1]);
                    worldObjects.add(p);
                    worldDrops.add(p);

                }
                if (newObj[1].charAt(0) == 'w'){//see if weapon
                    //System.out.println("found a sword");                            //x,y             //attack value                 //attack range       //obj name
                    Weapon w = new Weapon(Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),newObj[newObj.length-1]);
                    worldObjects.add(w);
                    worldDrops.add(w);

                }
                if (newObj[1].charAt(0) == 'r'){//see if weapon
                    //System.out.println("found a bow!");                            //x,y             //clip                //muzzle velocity      //                  //Damage       ////Name
                    Ranged r = new Ranged(Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5]),Integer.parseInt(newObj[6]),newObj[newObj.length-1]);
                    worldObjects.add(r);
                    worldDrops.add(r);

                }
                if (newObj[1].charAt(0) == 'G'){//OH golly its the goal
                    //System.out.println("found the goal!");                            //x,y    name       //NAME of target
                    Goal g = new Goal(Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),newObj[4],newObj[newObj.length-1]);
                    worldObjects.add(g);

                    endGoal = g;//set this levels goald
                }
                break;
                //add background    DREW
                case 'z': worldObjects.add(new Background((newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),Integer.parseInt(newObj[5])));
                //System.out.println("found Background!");
                break;
                //add in a falling rock
                case 'f': 
                FallingObj f = new FallingObj(Integer.parseInt(newObj[1]),Integer.parseInt(newObj[2]),guy,this,Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]));
                worldObjects.add(f);//add this falling rock to the world.
                break; 
                //Add in Artifact 
                case 'A':
                //System.out.println("found the artifact!");
                Artifact a = new Artifact(Integer.parseInt(newObj[1]),Integer.parseInt(newObj[2]),newObj[3],newObj[4]);
                worldObjects.add(a);
                endArtifact = a;
                break;
                //sign
                case 'S':
                //System.out.println("Found sign: " + newObj[5]);//x,y,h,w,message,name
                Sign s = new Sign(Integer.parseInt(newObj[1]),Integer.parseInt(newObj[2]),Integer.parseInt(newObj[3]),Integer.parseInt(newObj[4]),newObj[5],newObj[6]);
                worldObjects.add(s);//add to world
            }
        }
        else{
            return;//obviously too small to be a relevant object
        }
        guy.setArrayList(platformList);
        guy.setVertArrayList(vertPlatformList);
    }
    //for board to get the objects so it can paint them
    public ArrayList<IWorldInterface> getObjects(){
        return worldObjects;
    }

    //use bounds of form!!!!
    
    public void checkCamera()
    {
    	TiledPlatform down = guy.checkNearbyTiles().get(2);
    	
        //use bounds of form!!!!
        if(guy.getX() < 400 && (guy.getVelX() < 0 || (down != null && (down.getTileType() != 7 || down.getTileType() != 8))))
        {
            moveObjectsX();
            guy.moving(true);
        }
        else if(guy.getX() > 600 && (guy.getVelX() > 0 || (down != null && (down.getTileType() != 7 || down.getTileType() != 8))))
        {
            moveObjectsX();
            guy.moving(true);
        }
        else
        {
            guy.moving(false);
        }

        if(guy.getY() < 200 && guy.getVelY()< 0)
        {

            moveObjectsY();
            guy.movingY(true);
        }
        else if(guy.getY() > 500 && guy.getVelY() > 0)
        {
            moveObjectsY();
            guy.movingY(true);
        }
        else
        {
            guy.movingY(false);
        }
    }

    /*
    public void checkCamera()
    {
    	if (guy.getX() != 500)
    	{
    		guy.setX(500);
    		moveObjectsX(500 - guy.getX());
    	}
    	
    	if (guy.getY() != 375)
    	{
    		guy.setY(375);
    		moveObjectsY(375 - guy.getX());
    	}
    }*/
    
    //JASON calculates distance to player
    public int distToPlayer(IWorldInterface obj){
        int dist = 0;
        //calculate distance using dist equation
        dist = (int)(Math.sqrt(Math.pow(((guy.getX() + 48) - obj.getX()),2) + Math.pow(((guy.getY() + 48)- obj.getY()),2)));
        return dist;
    }
    
    public int distToPlayer2(IWorldInterface obj)
    {
        int dist = 0;
        //calculate distance using dist equation
        dist = (int)(Math.sqrt(Math.pow((guy.getX() - obj.getX()),2) + Math.pow((guy.getY()- obj.getY()),2)));
        return dist;
    }
    
    public int angleToPlayer(IWorldInterface obj)
    {
    	int theta = 0;
    	theta = (int)Math.atan2(guy.getY() - obj.getY(), guy.getX() - obj.getX());
    	theta *= 180/Math.PI;
    	return theta;
    }

    public int distToObject(IWorldInterface obj1,IWorldInterface obj2){
        int dist = 0;
        //calculate distance using dist equation
        dist = (int)(Math.sqrt(Math.pow((obj2.getX() - obj1.getX()),2) + Math.pow((obj2.getY() - obj1.getY()),2)));
        return dist;
    }

    public void moveObjectsX()
    {
        //moves the objects in the X direction according to the players movement 
        for( int i =0; i < worldObjects.size();i++)
        {
            if(worldObjects.get(i).getType() == 'p')
            {
                ((Platform)worldObjects.get(i)).movePlatform(-(int)guy.getVelX(),0);

            }
            else if(worldObjects.get(i).getType() == 'v')
            {
                ((VerticalPlatform)worldObjects.get(i)).movePlatform(-(int)guy.getVelX(),0);
            }
            else
            {
            	if(worldObjects.get(i).getType() == 'z')
                {
                    worldObjects.get(i).setX(worldObjects.get(i).getX() - ((int)guy.getVelX()/2));
                }
                else
                {
                    worldObjects.get(i).setX(worldObjects.get(i).getX() - (int)guy.getVelX());
                }
            }
        }
        
        tileOffsetX -= guy.getVelX();
    } 

    public void moveObjectsY(){
        //moves the objects in the X direction according to the players movement 
        for( int i =0; i < worldObjects.size();i++){
            if( guy.getVelY() < 0){
                if(worldObjects.get(i).getType() == 'p'){
                    ((Platform)worldObjects.get(i)).movePlatform(0, -(int)guy.getVelY());

                }
                else if(worldObjects.get(i).getType() == 'v'){
                    ((VerticalPlatform)worldObjects.get(i)).movePlatform(0, -(int)guy.getVelY());
                }
                else if(worldObjects.get(i).getType() == 'f'){
                    ((FallingObj)worldObjects.get(i)).move(-(int)guy.getVelY());
                }
                else{

                    if(worldObjects.get(i).getType() == 'z'){
                        //worldObjects.get(i).setX(worldObjects.get(i).getX() - ((int)guy.getVelX()/2));
                        worldObjects.get(i).setY(worldObjects.get(i).getY() - ((int)guy.getVelY()));
                    }
                    else{
                        //worldObjects.get(i).setX(worldObjects.get(i).getX() - (int)guy.getVelX());
                        worldObjects.get(i).setY(worldObjects.get(i).getY() - (int)guy.getVelY());
                        //guy.setX(guy.getX() - (int)guy.getVelX());
                    }

                }
            }
            else
            {
                if(worldObjects.get(i).getType() == 'p'){
                    ((Platform)worldObjects.get(i)).movePlatform(0, -(int)guy.getVelY());

                }
                else if(worldObjects.get(i).getType() == 'v'){
                    ((VerticalPlatform)worldObjects.get(i)).movePlatform(0, -(int)guy.getVelY());
                }
                else if(worldObjects.get(i).getType() == 'f'){
                    ((FallingObj)worldObjects.get(i)).move(-(int)guy.getVelY());
                }
                else{

                    if(worldObjects.get(i).getType() == 'z'){
                        //worldObjects.get(i).setX(worldObjects.get(i).getX() - ((int)guy.getVelX()/2));
                        worldObjects.get(i).setY(worldObjects.get(i).getY() - ((int)guy.getVelY()));
                    }
                    else{
                        //worldObjects.get(i).setX(worldObjects.get(i).getX() - (int)guy.getVelX());
                        worldObjects.get(i).setY(worldObjects.get(i).getY() - (int)guy.getVelY());
                        //guy.setX(guy.getX() - (int)guy.getVelX());
                    }

                }
            }

        }
        
        tileOffsetY -= (int)guy.getVelY();
    }
    
    /*public void moveObjectsY(int diff)
    {
    	for (int i =0; i < worldObjects.size();i++)
    	{
			 worldObjects.get(i).setX(worldObjects.get(i).getX() + diff);
    	}
    	
    	tileOffsetY -= (int)guy.getVelY();
    }*/

    /**
    for( int i =0; i < fallingStuff.size();i++){
    fallingStuff.get(i).move(0, (int)guy.getVelY());
    }
     */

    //worldObjects.get(i).setY(    worldObjects.get(i).getY() - (int)guy.getVelY()  );
    //if(guy.getVelY() ==0){
    //    if(guy.getY() < 50){
    //       worldObjects.get(i).setY(    worldObjects.get(i).getY() + 4);
    //       guy.setY(guy.getY() + 4);
    //   }
    //   else{
    //       worldObjects.get(i).setY(    worldObjects.get(i).getY() - 4);
    //      guy.setY(guy.getY() - 4);

    //}
    
    /*
    public ArrayList<IWorldInterface> getActiveObjects(ArrayList<IWorldInterface> obj){
        ArrayList<IWorldInterface> worldBack = new ArrayList<IWorldInterface>();
        for( int i =0; i < obj.size();i++)
        {
            for(int j = 0; j < npcList.size();j++)
            {
                if(obj.get(i).getName() == npcList.get(j).getName())
                {

                    npcList.get(j).setArrayList(platformList);
                    /*
                    else if(obj.get(i).getType() == ('v'))
                    {
                    npcList.get(j).setVertArrayList(vertPlatformList);
                    }

                     *//*

                }
            }

            if(obj.get(i).getX() + obj.get(i).getWidth()  > -50 && obj.get(i).getX()  < 1050)
            {

                worldBack.add(obj.get(i));
            }
        }
        return worldBack;
    }
    */
    
    public ArrayList<IWorldInterface> getActiveObjects(ArrayList<IWorldInterface> obj)
    {
    	return worldObjects;
    }

    public ArrayList<Lever> getLeverObjects() 
    {
        return leverObjects;
    }

    private void givePlayerObj(WObject obj)
    {
        guy.setObjInHand(obj);//place object in hand
    }
    //name created on initialization of object
    public IWorldInterface removeByName(String TargetName)
    {//assuming there is no duplicate names
        IWorldInterface tempObj = null;
        for (int i = 0;i < worldObjects.size();i++)
        {
            if (TargetName.equals(worldObjects.get(i).getName()))
            {
                //then this is the target obj
                tempObj = worldObjects.get(i);
                worldObjects.remove(i);//remove desired object
            }
        }
        return tempObj;
    }

    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_R)
        {
            myBoard.restart();
        }
        
        if (key == KeyEvent.VK_G)
        {
        	if (myBoard.getDrawGrid() == false)
        		myBoard.setDrawGrid(true);
        	else
        		myBoard.setDrawGrid(false);
        }
        
        //to pick up and drop objects, press 'q'
        if (key == KeyEvent.VK_Q) 
        {//called when player trys to pick something up
            //System.out.println("attempt pick up");
            //trying for artifact
           /* if (distToPlayer(endArtifact) < 30)
            {
                ///we found the elusive artifact
                endGoal.setActive();//activate goal
                endArtifact.setX(endGoal.getX());//For use with the compass pointing to the goal after pick up
                endArtifact.setY(endGoal.getY());//
                guy.giveArtifact(endArtifact);//give the guy the artifact
                removeByName(endArtifact.getName());//remove the artifact
                return;
            }*/
          /*  if (guy.holdingObj())
            {//if he was holding something drop it now
                //System.out.println("already holding" + guy.getObjInHand().getName());
                //place obj where guy was but offset a bit
                putInWorld(guy.getObjInHand(),guy.getX()+5,guy.getY()+5);
                guy.setObjInHand(null);//set what he has to nothing
            }
            else
            {//his hands were empty so try to pick up obj
                //System.out.println("empty hands");
                WObject closestObj = null;//if it stays null no object is close enough
                int closest = Integer.MAX_VALUE;
                for (WObject cur : worldDrops) {//check each obj
                    int d = distToPlayer(cur);
                    // System.out.println(d);
                    if (d <= 30){//closer than 20 pixels
                        if (d <= closest)
                        {
                            closest = d;
                            closestObj = cur;//this is now the closest obj so set it too that
                        }
                    }
                }*/
        	/*
                if (closestObj != null)
                {//if there was actually an object in range
                    guy.setObjInHand(closestObj);
                    //We then need to remove this obj from the world
                    removeByName(closestObj.getName());
                    if(closestObj.getType() == 'w')
                    {
                        guy.weaponInHand(true);
                    }
                    else 
                    {
                        guy.weaponInHand(false);
                    }
                }
                */
            }
        
      /**DEBUG!*/
        if (key == KeyEvent.VK_N)
        {
            myBoard.endCurrentLevel();
        }
    }
    //will take an object and put it in the WorldObjects list.
    private void putInWorld(WObject obj,int x,int y)
    {
        obj.setX(x);
        obj.setY(y);
        worldObjects.add(obj);
        //do i need to add back to world drops?
    }
    ///take object clas object out of object list
    public void removeObj(String TargetName)
    {//assuming there is no duplicate names
        IWorldInterface tempObj = null;
        for (int i = 0;i < worldDrops.size();i++)
        {
            if (TargetName.equals(worldDrops.get(i).getName()))
            {
                //then this is the target obj
                tempObj = worldDrops.get(i);
                worldDrops.remove(i);//remove desired object
            }
        }
        //return tempObj;
    }

    //pick up objects for npc
    public void npcCheckToPickUp(ArrayList<NPC> npcList)
    {
        for(int i = 0; i < npcList.size();i++)
        {
            for (WObject cur : worldDrops) {//check each obj
                int d = distToObject(cur, npcList.get(i));
                //System.out.println(d);
                if (d <= 30)
                {//closer than 20 pixels
                    int closest = Integer.MAX_VALUE;
                    WObject closestObj = null;//if it stays null no object is close enough
                    if (d <= closest)
                    {
                        closest = d;
                        closestObj = cur;//this is now the closest obj so set it too that
                        if (closestObj != null)
                        {
                            npcPickUp(npcList.get(i));
                        }
                    }
                }
            }
        }
    }

    public void npcPickUp(NPC npc) 
    {

        //to pick up and drop objects, press 's'

        //System.out.println("attempt pick up");
        if (npc.holdingObj())
        {//if he was holding something drop it now
            //System.out.println("already holding" + npc.getObjInHand().getName());
            //place obj where npc was but offset a bit
            putInWorld(npc.getObjInHand(),npc.getX()+5,npc.getY()+5);
            npc.setObjInHand(null);//set what he has to nothing
        }
        else
        {//his hands were empty so try to pick up obj
            //System.out.println("empty hands");
            WObject closestObj = null;//if it stays null no object is close enough
            int closest = Integer.MAX_VALUE;

            if (closestObj != null)
            {//if there was actually an object in range
                npc.setObjInHand(closestObj);
                //We then need to remove this obj from the world
                removeByName(closestObj.getName());
            }
        }

    }

    //player wants to make an attack at something in the world
    public void playerAttack(int direction,Weapon w)
    {
        char weap = w.getType();
        //System.out.println("looking for npc targets...");

        //access all npcs NPCS!
        //to do add health to npcs and make them not just sit there.
        NPC closestNPC = null;//if it stays null no object is close enough
        int closest = Integer.MAX_VALUE;
        for (NPC cur : npcList) 
        {//check each obj

            int d = distToPlayer2(cur);//distance to player
            //System.out.println(d);
            if (d <= w.range())
            {//in range
                if (d <= closest)
                {
                    closest = d;
                    closestNPC = cur;//this is now the closest obj so set it too that
                }
            }
        }
        if (closestNPC != null)
        {//if we found a guy
            //System.out.println("Were attacking" + closestNPC.getName() + " with " + closestNPC.getHealth());
            closestNPC.damage(w.attackP());//damage guy
            currentEnemy = closestNPC;
            //System.out.println(closestNPC.getName() + " has " + closestNPC.getHealth());
        }
        //last thing we do is check npc lives

    }

    public void killNPC(String s)
    {
        IWorldInterface tempObj = null;
        for (int i = 0;i < npcList.size();i++)
        {
            if (s.equals(npcList.get(i).getName()))
            {
                //then this is the target obj
                tempObj = npcList.get(i);
                npcList.remove(i);//remove desired object
                //System.out.println("PLAYER HAS SMITTED " + tempObj.getName()+"!!! \n\n###############");
                //Create and add potion
                Potion p = new Potion(tempObj.getX() + 36,tempObj.getY() + 32,"Pot_" + myBoard.getTime());
                worldObjects.add(p);
                worldDrops.add(p);
                myBoard.addScore(50);
            }
        }
    }
    //checks whether npcs are alive
    public void checkNPClives()
    {
        for(int i = 0; i<npcList.size();i++) 
        {//check each obj
            if (npcList.get(i).getHealth()<=0)
            {//if dead
                removeByName(npcList.get(i).getName());
                killNPC(npcList.get(i).getName());
                currentEnemy=null;
            }
        }
    }
    //USE WITH PROJECTILES
    public boolean addProjectile(Projectile p)
    {
        if (coolDown >= 3){//if it has cooled down long enough
            projectiles.add(p);//add to projectile list
            coolDown = 0;//reset
            return true;
        }
        return false;

    }

    public void moveProjectiles()
    {
        for (int i = 0; i < projectiles.size();i++)
        {
            projectiles.get(i).increment();//move arrows
            //Bounds of window
            if (projectiles.get(i).getX() > 1000 || projectiles.get(i).getX() < 0)
            {
                projectiles.remove(i);//if out of bounds
            }
            else{
                if (checkNpcProjCollisions(projectiles.get(i)))
                {//check to see if this has collided with anything
                    projectiles.remove(i);//take it away from list
                }
            }
        }
    }

    public boolean checkNpcProjCollisions(Projectile p)
    {
        boolean collided =  false;
        for (NPC cur : npcList)
        {
            int dist = (int)(Math.sqrt(Math.pow((p.getX() - (cur.getX()+10)),2) + Math.pow((p.getY() - (cur.getY()+20)),2)));
            if (dist < 24)
            {
                cur.damage(p.getDamage());
                currentEnemy = cur;
                collided = true;
                checkNPClives();
                break;
            }
        }
        return collided;
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;//return list of projectiles
    }
    //get last attacked guy
    public NPC getEnemy()
    {
        return currentEnemy;
    }

    public void checkPotions()
    {
        Potion closestObj = null;//if it stays null no object is close enough
        int closest = Integer.MAX_VALUE;
        for (WObject cur : worldDrops) 
        {//check each obj
            int d = distToPlayer(cur);
            //System.out.println(d);
            if (d <= 20 && cur instanceof Potion)
            {//closer than 20 pixels and its a potion
                if (d <= closest)
                {
                    closest = d;
                    closestObj = (Potion)cur;//this is now the closest obj so set it too that
                }
            }
        }
        if (closestObj != null)
        {
            closestObj.use(guy);//use the potion on player;
            removeByName(closestObj.getName());
            removeObj(closestObj.getName());//get rid of it after
        }
    }

    /**
     * WHEN THE PLAYER HAS REACHED THE OBJECTIVE...
     * TELL THE BOARD GOD THAT WE NEED THE NEXT LEVEL!
     */
    //assuming someone has added one goal
    private void checkGoal()
    {
        if (endGoal!= null && distToPlayer(endGoal) < 35)
        {
        	myBoard.endCurrentLevel();//bye bye
        }
    }

    public Artifact getArtifact()
    {
        return endArtifact;
    }

    public void addCombo1(String num, int pos) 
    {
        combo1.set(pos, num);
    }

    public void addCombo2(String num, int pos) 
    {
        combo2.set(pos, num);
    }

    public void addCombo3(String num, int pos) 
    {
        combo3.set(pos, num);
    }

    public void addCombo4(String num, int pos) 
    {
        combo4.set(pos, num);
    }

    public void addCombo5(String num, int pos) 
    {
        combo5.set(pos, num);
    }

    public String getCombo1() 
    {
        String one = "";
        for (String item : combo1) 
        {
            one = one + item;

        }

        return one;
    }

    public String getCombo2() 
    {
        String one = "";
        for (String item : combo2) 
        {
            one = one + item;
        }

        return one;
    }

    public String getCombo3() 
    {
        String one = "";
        for (String item : combo3) 
        {
            one = one + item;

        }

        return one;
    }

    public String getCombo4() 
    {
        String one = "";
        for (String item : combo4) 
        {
            one = one + item;

        }

        return one;
    }

    public String getCombo5() 
    {
        String one = "";
        for (String item : combo5) 
        {
            one = one + item;

        }

        return one;
    }

    private void initializeLists() 
    {
        for (int i = 0; i < 3; i++) 
        {
            combo1.add("");
        }

        for (int i = 0; i < 3; i++) 
        {
            combo2.add("");
        }

        for (int i = 0; i < 3; i++) 
        {
            combo3.add("");
        }

        for (int i = 0; i < 3; i++) 
        {
            combo4.add("");
        }

        for (int i = 0; i < 3; i++) 
        {
            combo5.add("");
        }
    }

    public ArrayList<Door> getDoorObjects() 
    {
        return doorObjects;
    }
    
    public ArrayList<TiledPlatform> getTileList()
    {
    	return tileList;
    }
    
    public int getOffsetX()
    {
    	return tileOffsetX;
    }
    
    public int getOffsetY()
    {
    	return tileOffsetY;
    }
}