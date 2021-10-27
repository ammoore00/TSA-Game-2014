package src;


import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;
public class PlatformTile implements IWorldInterface{
    //Required Infomation
    private String name;
    private String backImage;
    //Position
    int x = 0;
    int y = 0;
    private int height;
    private int width;
    // Graphics
    private Image image;
    public PlatformTile(String imageName, int heightG, int widthG){
        backImage = imageName;
        ImageIcon imageBackImage = new ImageIcon(this.getClass().getResource("Resources\\Images\\OldPlatforms\\" + backImage));
        image = imageBackImage.getImage();
        

        height = heightG;
        width = widthG;
    }
    public String getName(){return name;}

    public int getX(){return x;}

    public int getY(){return y;}

    public void setX(int sx){x = sx;}

    public void setY(int sy){y = sy;}

    public char getType(){return 't';}
    ///for use with sprites
    public Image getImage(){return image;}

    public int getColumns(){return 1;}

    public int getHeight(){return height;}

    public int getWidth(){return width;}

    public int getFrame(){return 0;}

    public void act(ArrayList<IWorldInterface>  objs, Player p){}
}