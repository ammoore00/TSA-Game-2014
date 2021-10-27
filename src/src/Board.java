package src;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//handles all timer events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.ImageIcon;

import src.Resources.*;
@SuppressWarnings({ "serial", "unused" })
public class Board extends JPanel implements ActionListener
{
    //define the world ARRAY with 5 levels
    private World[] world = new World[5];
    private int currentLevel = 0;//Keep track of current level ranging 0-4, default first level

    private ImageIcon icon;
    private Image img;
    private Timer timer;
    private int time = 1;
    int j;
    PlatformTile t;
    //Spikes s = new Spikes(0,0,world[currentLevel].getPlayer(),world[currentLevel]);
    private Image compass;
    private Image needle;
    //player stuff
    private int invincibilityTimer = 0;
    private boolean invincibility = false;
    Sound sound = new Sound();
    
    private boolean drawGrid = false;
    
    Graphics2D G2D;
    
    boolean drawSign;
    int signNumToDraw;
    
    int score;
    
    boolean drawLock;
    
    public Board() {

        //set all present to get keyboard input
        addKeyListener( new TAdapter() );
        addMouseListener(new mouseClicker());
        //sets the jFrame to all of the parameters
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        
        //if(currentLevel
        //creates the world
        //that will access the txt file number n corresponding to -> i
        for (int i = 1;i<=5;i++){ 
            world[i-1] = new World(i,this);
        }//load each file using format world1.txt world2.txt etc
        //We now have an array of all the world objects...
        playSongs();

        ///
        timer = new Timer(40, this);
        //starts timer
        timer.start();
    }

    /**
     * Handles all drawing
     * WE CAN FIRST HAVE A MENU SCREEN OF SORTS THAT IS DRAWN - > (!GAMESTARTED)
     */
    public void paint(Graphics g) {
        //call recursive, not sure why, but it ust works, needs to be there
        super.paint(g);
        // Draw the player on the board
        Graphics2D g2d = (Graphics2D)g;
        //G2D = g2d;
        
        drawBG(g2d);
        //might change when we add multiple levels, ie different world objects to access ex: world[i].getPlayer();

        ///ACCESS ALL THE OBJECTS IN THE WORLD[currentlevel#]
        ArrayList<IWorldInterface> objs = world[currentLevel].getObjects();
        for (int i=0;i< objs.size();i++){
            char objType = objs.get(i).getType();///access the type variable
            
            switch (objType){
                case 'S':
                //aight lets draw the sign
                drawSign((Sign)objs.get(i),g2d);

                break;
                
                
                case 't': //Tiled Platform
                	this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight(), 2);
                break;
                
                case 'p': 
                //g2d.drawLine(objs.get(i).getX(),objs.get(i).getY(),((Platform)objs.get(i)).getEndX(), ((Platform)objs.get(i)).getEndY());
                this.drawTile(g2d,(Platform)(objs.get(i)));
                //g2d.drawString(objs.get(i).getName(),objs.get(i).getX(),objs.get(i).getY());
                /**
                if(((Platform)(objs.get(i))).isSpiked())
                {
                this.drawSpikes(g2d,(Platform)(objs.get(i)));
                }
                 */
                break;

                case 'v':
                //g2d.drawLine(objs.get(i).getX(),objs.get(i).getY(),((VerticalPlatform)objs.get(i)).getEndX(), ((VerticalPlatform)objs.get(i)).getEndY());
                this.drawVertTile(g2d,(VerticalPlatform)(objs.get(i)));
                g2d.drawString(objs.get(i).getName(),objs.get(i).getX(),objs.get(i).getY());
                break;

                


                

                
                //for objects
                case 'h'://health potion yo
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                break;
                case 'w'://sword
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                break;
                case 'r'://bow
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                break;
                case 'G'://bow
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                break;
                case 'z'://background

                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX() -500, objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX() +500, objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX() -1000, objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX() +1000, objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX() +1500, objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                case 'f'://rock thingy
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                break;
                case 'A'://dat artifact
                this.drawSpriteFrame(objs.get(i).getImage(), g2d ,objs.get(i).getX(), objs.get(i).getY(), objs.get(i).getColumns(), objs.get(i).getFrame(), objs.get(i).getWidth(), objs.get(i).getHeight());
                break;
            }
        }
        //DRAW PROJECTILES
        for (Projectile a: world[currentLevel].getProjectiles()){
            //maybe work?
            ///System.out.println(a.getX());

            Image img = null;
            if (a.getVel().getX() > 0){
                ImageIcon icon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Entities\\ARROWR.PNG"));
                img = icon.getImage();
            }
            else{
                ImageIcon icon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Entities\\ARROWL.PNG"));
                img = icon.getImage();
            }
            this.drawSpriteFrame(img,g2d,a.getX(),a.getY(),1,0,40,10);
            //g2d.drawImage(a.getImage(),a.getX(),a.getY(), null);//hopefully draw all the arrows
        }
        //player moved
        Player p = world[currentLevel].getPlayer();///find player of certain level
        //FOR THE PLAYER
        if (!invincibility){
            this.drawSpriteFrame(p.getImage(), g2d ,p.getX(), p.getY(), p.getColumns(), p.getFrame(), p.getWidth(), p.getHeight());
        }  
        else{
            invincibilityTimer++;//start the timer
            if (invincibilityTimer % 3==0){//every third we actually draw 
                this.drawSpriteFrame(p.getImage(), g2d ,p.getX(), p.getY(), p.getColumns(), p.getFrame(), p.getWidth(), p.getHeight());
            }
            if (invincibilityTimer > 50){//hooza mortality has returned!
                invincibility = false;
                p.setInvicibility(false);//immortal no more
                p.setHealth(100);
                invincibilityTimer = 0;
            }
        }
        
        for (IWorldInterface a : world[currentLevel].getObjects())
        {
        	if (a.getType() == 'n')
        	{
                this.drawSpriteFrame(a.getImage(), g2d ,a.getX(), a.getY(), a.getColumns(), a.getFrame(), a.getWidth(), a.getHeight());
                drawHealthBar(g2d,a.getX() + 39, a.getY() + 22,20,3,((NPC)a).getHealth(),((NPC)a).getMaxHealth());
            }
        }
        
        if (drawGrid)
        	drawGrid(g2d);
        
        //drawBG(g2d);
        
        ///DRAW HEADS UP DISPLAY
        drawHUD(g2d);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }
    
    public void drawGrid(Graphics2D g2d)
    {
    	//Draw Base Grid
        for (int i = 0; i < 1000; i += 32)
        {
        	g2d.drawLine(i + world[getCurrentLevel()].getOffsetX() % 32, 0, i + world[getCurrentLevel()].getOffsetX() % 32, 750);
        }
        for (int i = 0; i < 750; i += 32)
        {
        	g2d.drawLine(0, i + world[getCurrentLevel()].getOffsetY() % 32, 1000, i + world[getCurrentLevel()].getOffsetY() % 32);
        }
    }
    
    public void drawBG(Graphics2D g2d)
    {
    	for (int i = -1000; i < world[0].getLength() * 32; i += 1000)
    	{
    		Image image = null;
    		
    		if(currentLevel == 0)
    		{
    			image = (new ImageIcon(this.getClass().getResource("Resources\\Images\\Backgrounds\\Mesopotamia.PNG"))).getImage();
    		}
    		if(currentLevel == 1)
    		{
    			image = (new ImageIcon(this.getClass().getResource("Resources\\Images\\Backgrounds\\ROME.PNG"))).getImage();
    		}
    		if(currentLevel == 2)
    		{
    			image = (new ImageIcon(this.getClass().getResource("Resources\\Images\\Backgrounds\\ITALY.PNG"))).getImage();
    		}
    		if(currentLevel == 3)
    		{
    			image = (new ImageIcon(this.getClass().getResource("Resources\\Images\\Backgrounds\\TALAS.PNG"))).getImage();
    		}
    		if(currentLevel == 4)
    		{
    			image = (new ImageIcon(this.getClass().getResource("Resources\\Images\\Backgrounds\\PERU.PNG"))).getImage();
    		}
    	

		drawSpriteFrame(image, g2d, i + world[getCurrentLevel()].getOffsetX()/2 % 1000, 0, 1, 0, 500, 500,2);
		}
    }
    
    //draw the sign
    public void drawSign(Sign s,Graphics2D g2d){
        //205-133-63 brown
        /*
        g2d.setColor(new Color(205,133,63));//brown sign
        g2d.fillRect(s.getX(),s.getY(),s.getWidth(),s.getHeight());//make the rectangle
        g2d.setColor(Color.black);

        g2d.drawString(s.getText(),(int)(s.getX()+(s.getWidth()/2)),(int)(s.getY()+(s.getHeight()/2)));//probaly wont work
         */
        g2d.drawImage(s.getImage(),s.getX(),s.getY(),s.getWidth(),s.getHeight(),this);
    }

    public void drawTile(Graphics2D g2d,Platform p)
    {
        if(currentLevel == 0)
        {
            if(p.isSpiked()){
                t = new PlatformTile("BrickMesoSpikesTransparent.png",20,10);
            }
            else{
                t = new PlatformTile("BrickMeso.png",20,10);
            }

        }
        else if(currentLevel == 1)
        {
            if(p.isSpiked()){
                t = new PlatformTile("BrickRomeSpikesTransparent.png",20,10);
            }
            else{
                t = new PlatformTile("BrickRome.png",20,10);
            }

        }
        else if(currentLevel == 2)
        {
            if(p.isSpiked()){
                t = new PlatformTile("BrickTalasSpikesTransparent.png",20,10);
            }
            else{
                t = new PlatformTile("BrickTalas.png",20,10);
            }

        }
        else if(currentLevel == 3)
        {
            if(p.isSpiked()){
                t = new PlatformTile("BrickEuroSpikesTransparent.png",20,10);
            }
            else{
                t = new PlatformTile("BrickEuro.png",20,10);
            }

        }
        else if(currentLevel == 4)
        {
            if(p.isSpiked()){
                t = new PlatformTile("BrickPeruSpikesTransparent.png",20,10);
            }
            else{
                t = new PlatformTile("BrickPeru.png",20,10);
            }

        }

        if(p.getSlope() <  0)
        {
            //Make it so that the y values are changed to the slope of the plat
            j = p.getY() + ((int)(t.getHeight()* p.getSlope())) + 10;
        }
        else if(p.getSlope() > 0)
        {
            j = p.getY() - ((int)(t.getHeight() * p.getSlope())) + 5;
        }
        else
        {
            j = p.getY();
        }
        int i = p.getX() - t.getWidth();
        if(p.getSlope() > 0)
        {
            for(i = p.getX(); i < p.getEndX(); i+= t.getWidth())
            {
                //System.out.println(j);
                g2d.drawImage(t.getImage(),i ,(int)(j += t.getHeight() * p.getSlope()/2),null);

            }

        }

        else if(p.getSlope() < 0)
        {
            for(i = p.getX(); i < p.getEndX(); i+= t.getWidth())
            {
                g2d.drawImage(t.getImage(),i,(int)(j += t.getHeight() * p.getSlope()/2),null);

            }
        }
        else if(p.getSlope()==0)
        {
            for(i = p.getX(); i < p.getEndX(); i+= t.getWidth())
            {

                g2d.drawImage(t.getImage(),i,p.getY(),null);
                //g2d.drawImage(t.getImage(),i + t.getHeight(),p.getY(),null);
            }
        }

    }

    /**
    public void drawSpikes(Graphics2D g2d,Platform p) {
    for(int i = p.getX(); i < p.getEndX(); i+= s.getWidth())
    {

    g2d.drawImage(s.getImage(),i,p.getY() - s.getHeight(),null);

    }
    }
     */

    public void drawVertTile(Graphics2D g2d,VerticalPlatform v)
    {
        if(currentLevel == 0)
        {
            t = new PlatformTile("BrickMeso.png",20,10);
        }
        else if(currentLevel == 1)
        {
            t = new PlatformTile("BrickRome.png",20,10);
        }
        else if(currentLevel == 2)
        {
            t = new PlatformTile("BrickTalas.png",20,10);
        }
        else if(currentLevel == 3)
        {
            t = new PlatformTile("BrickEuro.png",20,10);
        }
        else if(currentLevel == 4)
        {
            t = new PlatformTile("BrickPeru.png",20,10);
        }
        int i = v.getY() - t.getHeight();
        for(i = v.getY();i > v.getEndY(); i-=t.getHeight())
        {
            g2d.drawImage(t.getImage(),v.getX() - 10,i,null);
        }
    }

    public void drawSpriteFrame(Image source, Graphics2D g2d, int x, int y,
    int columns, int frame, int width, int height)
    {
        //creates frame size
        int frameX = (frame % columns) * width;
        int frameY = (frame / columns) * height;
        //draws image according to the box 
        g2d.drawImage(source, x, y, x+width, y+height,frameX, frameY, frameX+width, frameY+height, this);
    }
    
    public void drawSpriteFrame(Image source, Graphics2D g2d, int x, int y,
    	    int columns, int frame, int width, int height, double scale)
    	    {
    	        //creates frame size
    	        int frameX = (frame % columns) * width;
    	        int frameY = (frame / columns) * height;
    	        //draws image according to the box 
    	        g2d.drawImage(source, x, y, (int)(x+(width) * scale), (int)(y+(height) * scale),frameX, frameY, frameX+width, frameY+height, this);
    	    }
    
    public void drawSignText(int num, TiledPlatform sign)
    {
    	Graphics2D g2d = G2D;
    	
    	Image text = getSignText(sign.getNum());
    	
    	drawSpriteFrame(text, g2d, 740, 0, 1, 0, 260, 140);
    }
    
    public Image getSignText(int num)
    {
    	ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Signs\\SIGN_" + currentLevel + "_" + num + ".PNG"));
    	
    	return imageIcon.getImage();
    }

    /**
     * Handles the keyboard input
     */
    private class TAdapter extends KeyAdapter implements KeyListener{
        //Jason, I think im gonna pass the world what keys were pressing for object manipulation.
        public void keyReleased(KeyEvent e) {
            //key released event
            world[currentLevel].getPlayer().keyReleased(e);

        }

        public void keyPressed(KeyEvent e) {
            //key Pressed event
            world[currentLevel].getPlayer().keyPressed(e);
            world[currentLevel].keyPressed(e);//pass the world what the player wants to do
        }
    }
    private class mouseClicker extends MouseAdapter implements MouseListener{

        //when mouse is pressed get angle
        public void mousePressed(MouseEvent e) 
        {
            Player p = world[currentLevel].getPlayer();

            //Get angle to player and set it
            double theta = Math.atan2(e.getY()-p.getY(),e.getX()-p.getX());

            p.playerUse(theta);
        }
    }
    
    /**FOR SIGNS!
     * 
     * @param draw
     * @param num
     */
    public void setDraw(boolean draw, int num)
    {
    	if (!drawLock)
    	{
    		drawSign = draw;
    		drawLock = draw;
    	}
    	
    	System.out.println("Draw: " + drawSign + ", Lock: " + drawLock);
    	signNumToDraw = num;
    }
    
    public void setDrawLock(boolean lock)
    {
    	drawLock = lock;
    }
    
    public boolean getLock()
    {
    	return drawLock;
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        //updates the world
        world[currentLevel].updateWorld();
        //paint the board
        repaint();  
        time++;

    }

    public void drawHUD(Graphics2D g2d){//taking the graphics object
        //Maybe draw a menu backGround
       g2d.setColor(Color.LIGHT_GRAY);
       g2d.fillRect(0,0,240,160);
       //Draw Hud Graphic C:\Users\Jason\Desktop\TSA Game Nationals\src\src\Resources\Images\HUD
       ImageIcon hudIcon = new ImageIcon(this.getClass().getResource("Resources\\Images\\HUD\\HUDBACK.PNG"));
       Image HudIMG = hudIcon.getImage();
       //g2d.drawImage(HudIMG,0,0,this);
       drawSpriteFrame(HudIMG, g2d, 0, 0, 1, 0, 240, 160, 1.1);
       
        //draw player stats
        g2d.setColor(Color.white);
        Player p = world[currentLevel].getPlayer();
        g2d.drawString("Player health: " + p.getHealth(),12,54);//health stat
        //draw health bar
        drawHealthBar(g2d,12,12,200,30,p.getHealth(),p.getMaxHealth());
        
        ///LIVES OF PLAYER
        g2d.setColor(Color.white);
        g2d.drawString("lives: " + p.getLives(),115,54); 
        g2d.drawString("score: " + score, 160, 54);
       
        ///DRAW OBJECT IN HAND
        WObject o = p.getObjInHand();
        
       
        //draw rectangles
        //Sword
        g2d.setColor(Color.black);
        g2d.drawRect(32,77,60,60);
        g2d.setColor(Color.white);
        g2d.drawString("[1]",55,71); 
        //sword image
        //C:\Users\Jason\Desktop\TSA Game Nationals\src\src\Resources\Images\Items
        ImageIcon swordIcon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Items\\SWORD.PNG"));
        Image SwordIMG = swordIcon.getImage();
        g2d.drawImage(SwordIMG,35,80,this);
        
        g2d.setColor(Color.black);
        g2d.drawRect(132,75,60,60);
        g2d.setColor(Color.white);
        g2d.drawString("[2]",155,71);
        //bow image
        ImageIcon bowIcon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Items\\BOW AND ARROW.PNG"));
        Image BowIMG = bowIcon.getImage();
        g2d.drawImage(BowIMG,140,78,this);
        
        
        if (o!=null){//if he has object in hand
           //Highlights appropriate weapon
            if (o instanceof Weapon){
            	g2d.setPaint(Color.white);
            	g2d.setStroke(new BasicStroke(4)); // 8-pixel wide pen
            	g2d.drawRect(32,75,60,60);
            }
            else if (o instanceof Ranged){
            	g2d.setPaint(Color.white);
            	g2d.setStroke(new BasicStroke(4)); // 8-pixel wide pen
            	g2d.drawRect(132,75,60,60);
            }
        }
        
        //to draw signssss
        if (drawSign)
        {
        	ImageIcon icon = new ImageIcon(this.getClass().getResource("Resources\\Images\\Signs\\SIGN_" + currentLevel + "_" + signNumToDraw + ".PNG"));
        	
        	g2d.drawImage(icon.getImage(), 760, 0, this);
        }
        
        ///outdated stuff
        
        
        //Artifact
       // Artifact a = p.artifactInHand();

        /*
        if (a!=null){//if he has object in hand

            this.drawSpriteFrame(a.getImage(), g2d ,410, 15, a.getColumns(), a.getFrame(), a.getWidth(), a.getHeight());
        }
        g2d.drawString("Artifact",400,70); 
        */
       
       // drawObjectiveCompass(g2d);
    }
    
    //Draw a health bar                         //////length height
    public void drawHealthBar(Graphics2D g2d,int x,int y,int l,int h,int Health,int max){
        //first draw red bar part
        g2d.drawRect(x,y,l,h);
        g2d.setColor(Color.red);
        g2d.fillRect(x,y,l,h);
        double d = ((double)Health)/((double)max);
        int pix = (int)(d*l);//get length of green health bar
        g2d.setColor(Color.green);
        g2d.fillRect(x,y,pix,h);

        //set back to black
        g2d.setColor(Color.black);
    }

    /**
     * WHEN THE PLAYER HAS REACHED THE END OF THE LEVEL
     */
    public void endCurrentLevel(){
        if (currentLevel < 4){
            currentLevel++;
            repaint();//redraw the new world yo
            playSongs();
        }
        else{//then it should equal 4 i guess
            //then end game they win!
        }
    }
    //draw the compass that will correspond of where to go
    private void drawObjectiveCompass(Graphics2D g2d){
        World w = world[currentLevel];
        Player p = w.getPlayer();
        Artifact a = w.getArtifact();
        
        if(a != null)
        {
            double theta = Math.atan2(a.getY()-p.getY(),a.getX()-p.getX());
            g2d.drawImage(compass, 840, 20, 60, 60, this);
            //find theta between player and objective
            g2d.translate(870,50); // Translate the center of our coordinates.
            g2d.rotate(theta+Math.PI/2);  // Rotate the image by 1 radian.
            //g2d.drawImage(image, 0, 0, 200, 200, this);
            g2d.drawImage(needle, -8, -25, 16, 50, this);
        }
    }

    public Image loadImg(String s){///load an image from 
    	//Points image to correct directory    
        ImageIcon icon = new ImageIcon(this.getClass().getResource(s));
        Image img = icon.getImage();
        return img;
    }
    
    public void addScore(int s)
    {
    	score += s;
    }

    public int getTime(){
        return time;
    }
    //will start players invicibility countdown
    public void startInvincibility(){
        invincibility = true;
    }
    ///FOR RESTARTING THE LEVEL
    public void restart(){
        world[currentLevel] = new World(currentLevel+1,this);//literally create a new instance of the level were on.
        playSongs();
    }

    @SuppressWarnings("static-access")
	public void playSongs(){
        if(currentLevel == 0)
        {
            sound.play("RisingSun.wav");
        }
        else if(currentLevel == 1)
        {
            sound.play("The Coming Storm (Rome).wav");
        }
        else if(currentLevel == 2)
        {
            sound.play("Holding On (China).wav");
        }
        else if(currentLevel == 3)
        {
            sound.play("556191_The-War-Nobody-Won (South America).wav");
        }
        else if (currentLevel == 4)
        {
            sound.play("To Catch A Falling Star (Medeival Europe).wav");
        }
    }
    
    public int getCurrentLevel()
    {
    	return currentLevel;
    }
    
    public void setDrawGrid(boolean draw)
    {
    	drawGrid = draw;
    }
    
    public boolean getDrawGrid()
    {
    	return drawGrid;
    }
}