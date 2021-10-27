package src;


import java.lang.Object;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
@SuppressWarnings({ "unused" })
public class Platform implements IWorldInterface
{
    private int startX,startY;
    private int endX, endY;
    private double length;
    private String name;
    private Image image;
    private int columns,width,frame;
    private int collX;
    private int collY;
    private int collXStart;
    private int collYStart;
    private int baseX;
    int tileWidth;
    int tileHeight;
    String tileName;
    Image tile;
    private int baseY;
    private double height;
    private boolean visible = true;
    boolean isSpiked;
    int platType =3;
    int platIntersectBound = 5;
    //for use with rendering to know how to draw it
    private char type = 'p';
    public Platform(String n, int x,int y, int x2, int y2, double h)//int s)//, String in, int tw,int th)
    {
        //initialize the platform's variables
        startX = x;
        startY = y;
        endX = x2;
        endY = y2;
        height = h;
        name = n;
        setLength(x,y,x2,y2);
        platType = (int)h;
        
        if(platType == 1)
        {
            isSpiked = true;
        }
        else
        {
            isSpiked = false;
        }
        
        
        /*
        tileName = in;
        tileWidth= tw;
        tileHeight =th;
        if (tileName != null){
            //System.out.println(tileName);
        }
        ImageIcon imageTile= new ImageIcon(this.getClass().getResource(tileName));
        tile = imageTile.getImage();
        
        */
    }

    public void act(ArrayList<IWorldInterface>  objs, Player p)
    {
    }

    public void setLength(int x, int y, int x2, int y2)
    {
        length = Math.sqrt((Math.pow(x2 - x, 2)) + (Math.pow(y2 - y,2)));
    }
    //return x
    public String getName(){
        return name;
    }
    
    public boolean isSpiked()
    {
        return isSpiked;
    }

    public int getX(){
        return startX;
    }
    //return Y
    public int getY(){
        return startY;
    }

    public int getEndX(){
        return endX;
    }
    //return Y
    public int getEndY(){
        return endY;
    }
    //SET THESE
    public void setY(int y){
        startY = y;
    }

    public void setX(int x){
        startX = x;
    }

    public void setEndY(int y){
        endY = y;
    }

    public void setEndX(int x){
        endX = x;
    }
    
    public Image getTile()
    {
        return tile;
    }
    
    public int getTileWidth()
    {
        return tileWidth;
    }
    
    public int getTileHeight()
    {
        return tileHeight;
    }
    //return length
    public double getLength(){
        return length;
    }
    //return image
    public Image getImage()
    {
        return image;
    }
    //return columns
    public int getColumns()
    {
        return columns;
    }
    //return height
    public int getHeight()
    {
        return 0;
    }

    public double getThickness()
    {
        return height;
    }
    //return width
    public int getWidth()
    {
        return width;
    }
    //return frame
    public int getFrame()
    {
        return frame;
    }

    public char getType(){
        return type;
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
        return visible;
    }

    public int getYOnPlat(int x)
    {

        double slope = (double)(getEndY() - getY())/(getEndX() - getX());
        ////System.out.println(slope);

        double diffX = x - getX();
        int platY = (int)(slope * diffX);
        ////System.out.println(platY);
        platY = getY() + platY;
        // //System.out.println(platY);
        return platY;
    }

    public void checkPlatform(Platform p, Player guy)
    {
        double slope = p.getSlope();
        int plX = guy.getX();
        int plY = guy.getY();
        //guy.setY(p.getY() - platY - 50);
        guy.setFallValue(false);

    }

    public double getSlope()
    {
        return ((double)getEndY() - getY())/(getEndX() - getX());
    }

    public String printSlope(Platform p)
    {
        return ("Slope=" + p.getSlope());
    }

    public void movePlatform(int shiftValueX, int shiftValueY)
    {
        setX(getX() + shiftValueX);
        setEndX(getEndX() + shiftValueX);
        setY(getY() + shiftValueY);
        setEndY(getEndY() + shiftValueY);
        //System.out.println(getX());

    }
}
