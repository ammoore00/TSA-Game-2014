package src;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.awt.Image;

public class TiledPlatform extends WObject implements IWorldInterface
{
	private int tileType;
	private char type = 't';
	/* Types:
	 * 0-Solid
	 * 1-Sloped
	 * * data = slope
	 * * * 0- one
	 * * * 1- negative one
	 * * * 2- half top
	 * * * 3- half bottom
	 * * * 4- negative half top
	 * * * 5- negative half bottom
	 * * * Add 6 for water
	 * 2-Background
	 * 3-One Way
	 * * data = water: none/still/flowing (0/1/2)
	 * 4-Water
	 * * data = normal/flowing (0/1)
	 * 5-Spikes
	 * 6-Ladder
	 * * data = normal/top (0/1)
	 * 7-Moving controller
	 * * data = max displacement in # of tiles
	 * 8-Moving part
	 * 9-Conveyer
	 * * data = velocity
	 * * * 6th bit (32) indicates direction: left/right (0/1)
	 * 10-Lava
	 * * data = normal/flowing (0/1)
	 * 11-Trampolines
	 * 12-Coins
	 * 13-Signs
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * All tiles: Add 128 to green to use second sprite sheet- blue values remain the same
	 */
	
	private Image image;
	
	private int data;
	private int initTileX;
	private int tileX;
	private int tileY;
	
	int dir = 1;
	int velX = 0;
	int offset = 0;
	
	private String imageName = "Resources\\Images\\tileSheet.png";
	
	private int columns = 16;
	private int frame = 0;
	
	private boolean pause;
	private boolean isActive;
	
	int num;
	
	public TiledPlatform(int x, int y, int t, int d, int img, String n)
	{
		super(x * 32, y * 32, n);
		
		//If 8th bit is on, use the second tile sheet (i.e. add 128 to green)
		if (d >= 128)
		{
			imageName = "Resources\\Images\\tileSheet2.png";
			d -= 128;
		}
		
		tileX = initTileX = x;
		tileY = y;
		
		tileType = t;
		data = d;
		
		frame = img;
		
		if (t == 7 || t == 8)
		{
			velX = 3;
		}
		
		if (t == 9)
		{
			//If 6th bit is on, conveyer goes right, else it goes left
			if (d >> 5 == 0)
			{
				dir = -1;

				if (d >> 7 == 1)
				{
					velX = data - 128;
				}
				else
				{
					velX = data;
				}
			}
			else if (d >> 7 == 1)
			{
				velX = data - 160;
			}
			else
			{
				velX = data - 32;
			}
			
			velX *= dir;
		}
		
        ImageIcon pIcon = new ImageIcon(this.getClass().getResource(imageName));
		
        image = pIcon.getImage();
	}
	
	public void act(ArrayList<IWorldInterface> objs, Player p)
	{
		double dtX = (objX - p.getWorld().getOffsetX())/32.0;
        double dtY = (objY - p.getWorld().getOffsetY())/32.0;
        
        tileX = (int)Math.floor(dtX);
        tileY = (int)Math.floor(dtY);
        
    	ArrayList<TiledPlatform> tileList = p.getTileList();
    	TiledPlatform left = null;
    	TiledPlatform left2 = null;
    	
    	for (TiledPlatform t : tileList)
    	{
    		int tX = t.getTileX();
    		int tY = t.getTileY();
    		
    		if ((tX == tileX - 1 && tY == tileY)/*Check left*/)
    		{
    			left = t;
    		}
    		
    		if ((tX == tileX - 2 && tY == tileY)/*Check left*/)
    		{
    			left2 = t;
    		}
    	}
        
        //Moving platform controller
        if (tileType == 7)
        {
        	if ((tileX < initTileX + data - 1 && velX > 0) || (tileX > initTileX - 1 && velX < 0))
        	{
        		if (!pause)
        			objX += velX;
        		offset = (objX % 32);
        	}
        	else
        	{
        		velX *= -1;
        		dir *= -1;
        	}
        }
        
        //Moving platform
        if (tileType == 8)
        {
        	TiledPlatform controller = null;
        	
        	int count = 1;
        	while (controller == null)
        	{
        		for (TiledPlatform t : tileList)
            	{
            		int tX = t.getTileX();
            		int tY = t.getTileY();
            		
            		if (tX == tileX - count && tY == tileY && t.getTileType() == 7)
            		{
            			controller = t;
            		}
            	}
        		
        		count ++;
        	}
        	
        	dir = controller.getDir();
        	offset = controller.getOffset();
        	
        	if (left != null && (left.getTileType() == 7 || left.getTileType() == 8))
        	{
        		objX = left.getX() + 32;
        	}
        	else if (left2 != null && (left2.getTileType() == 7 || left2.getTileType() == 8))
        	{
        		objX = left2.getX() + 64;
        	}
        }
        
        //Flowing water animation
        if ((tileType == 4 && data == 1) || (tileType == 3 && data == 2))
        {
        	if (tileType == 4)
        	{
        		if (frame < 255)
        			frame++;
        		else
        			frame = 252;
        	}
        	else if (tileType == 3)
        	{
        		if (frame / 16 < 7)
        			frame += 16;
        		else
        			frame -= 48;
        	}
        	
        	//Lines up animations horizontally
        	if (left != null && left.getTileType() == this.getTileType() && left.getData() == this.getData())
        	{
        		frame = left.getFrame();
        	}
        }
        
        //Flowing lava animation
        if (tileType == 10 && data == 1)
        {
        	if (tileType == 4)
        	{
        		if (frame < 57)
        			frame++;
        		else
        			frame = 54;
        	}
        	
        	//Lines up animations horizontally
        	if (left != null && left.getTileType() == this.getTileType() && left.getData() == this.getData())
        	{
        		frame = left.getFrame();
        	}
        }
        
        //Conveyer animations
        if (tileType == 9 && !pause)
        {
        	if (data >> 5 == 1)
        	{
        		if (frame / 16 < 11)
        			frame += 64;
        		else
        			frame -= 128;
        	}
        	else
        	{
        		if (frame / 16 > 6)
        			frame -= 64;
        		else
        			frame += 128;
        	}
        	
        	//Lines up animations horizontally
        	if (left != null && left.getTileType() == this.getTileType())
        	{
        		frame = left.getFrame();
        	}
        }
        //TRAMPOLINES
        if (tileType == 11) {
        	frame = 110;
        	if ((tileX == p.getTileX() && tileY > p.getTileY() && tileY <= (double)p.getTileY() + 1.0001)) {
        		frame = 111;
    	}
        }
        //COINS
        if (tileType == 12) {
        	
        		if (data == 128) {
        			frame = 14;
        		}
        		else if (data == 129) {
        			frame = 15;
        		}
        		else if (data == 130) {
        			frame = 30;
        		}
        		else if (data == 131) {
        			frame = 31;
        		}
        		else if (data == 132) {
        			frame = 46;
        		}
        		else if (data == 133) {
        			frame = 47;
        		}
        		else if (data == 134) {
        			frame = 62;
        		}
        		else if (data == 135) {
        			frame = 63;
        		}
       
        if (p.getTileX() == getTileX() && p.getTileY() == getTileY()) {
        	p.getWorld().getBoard().addScore(10);
        	
        	imageName = "Resources\\Images\\tileSheet.png";
        	ImageIcon pIcon = new ImageIcon(this.getClass().getResource(imageName));
    		
            image = pIcon.getImage();
        	System.out.println(data);
        	if (data == 72) {
    			tileType = 2;
    			frame = 127;
    		}
    		else if (data == 1) {
    			tileType = 2;
    			frame = 16;
    		}
    		else if (data == 2) {
    			tileType = 2;
    			frame = 17;
    		}
    		else if (data == 3) {
    			tileType = 2;
    			frame = 18;
    		}
    		else if (data == 4) {
    			tileType = 2;
    			frame = 19;
    		}
    		else if (data == 5) {
    			tileType = 2;
    			frame = 20;
    		}
    		else if (data == 6) {
    			tileType = 2;
    			frame = 155;
    		}
    		else if (data == 7) {
    			tileType = 2;
    			frame = 248;
    		}
        	
        }
        }
        
        ///sign checking!
        if (tileType == 13)
        {
        	int px = p.getX() + 48;
        	int py = p.getY() + 64;
        	
        	int dist = (int)(Math.sqrt(Math.pow((objX + 16) - px, 2) + Math.pow((objY + 16) - py, 2)));
        	
        	if (dist < 50)
        	{
        		p.getWorld().getBoard().setDraw(true, getData());
        	}
        	else
        	{
        		if (!p.getWorld().getBoard().getLock())
        			p.getWorld().getBoard().setDraw(false, getData());
        	}
        	
        	if (data == 128) {
        		frame = 62;
        	}
        	else if (data == 129) {
        		frame = 63;
        	}
        	else if (data == 130) {
        		frame = 78;
        	}
        	else if (data == 131) {
        		frame = 79;
        	}
        	else if (data == 132) {
        		frame = 94;
        	}
        	else if (data == 133) {
        		frame = 95;
        	}
        	else if (data == 134) {
        		frame = 110;
        	}
        	else if (data == 135) {
        		frame = 111;
        	}
        }
	}
	
	public void setActive(boolean a)
	{
		isActive = a;
	}
	
	public boolean getActive()
	{
		return isActive;
	}
	
	public void setNum(int n)
	{
		num = n;
	}
	
	public int getNum()
	{
		return num;
	}
	
	public char getType()
	{
		return type;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public String getImageName()
	{
		return imageName;
	}
	
	public int getColumns()
	{
        return columns;
    }
	
	public int getFrame()
	{
        return frame;
    }
	
	public int getWidth()
	{
		return 16;
	}
	
	public int getHeight()
	{
		return 16;
	}
	
	public int getTileX()
	{
		return tileX;
	}
	
	public int getTileY()
	{
		return tileY;
	}
	
	public int getTileType()
	{
		return tileType;
	}
	
	public int getData()
	{
		return data;
	}
	
	public int getVelX()
	{
		return velX;
	}
	
	public int getDir()
	{
		return dir;
	}
	
	public int getOffset()
	{
		return offset;
	}
	
	public String toString()
	{
		return "(" + tileX + ", " + tileY+ ")";
	}
	
	public void pause(boolean p)
	{
		pause = p;
	}
	
	public boolean getPause()
	{
		return pause;
	}
}