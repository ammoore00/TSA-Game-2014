package src;


import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Background implements IWorldInterface{
    //Required Infomation
    private String name = "background";
    private String backImage = "";

    //Position
    private int backX;
    private int backY;
    private int height;
    private int width;
    // Graphics
    private Image image;
    public Background(String imageName, int heightG, int widthG,int startX, int startY){
        backImage.equals(imageName);
        ImageIcon imageBackImage = new ImageIcon(this.getClass().getResource(imageName));
        image = imageBackImage.getImage();
        backX = startX;
        backY = startY;
        

        height = heightG;
        width = widthG;
    }
    public String getName(){return name;}

    public int getX(){return backX;}

    public int getY(){return backY;}

    public void setX(int x){backX = x;}

    public void setY(int y){backY = y;}

    public char getType(){return 'z';}
    ///for use with sprites
    public Image getImage(){return image;}

    public int getColumns(){return 1;}

    public int getHeight(){return height;}

    public int getWidth(){return width;}

    public int getFrame(){return 0;}

    public void act(ArrayList<IWorldInterface>  objs, Player p){}
}