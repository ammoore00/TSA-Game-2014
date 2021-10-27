package src;


import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
@SuppressWarnings({ "unused" })
public class Lever extends Interactable
{
    private String name;
    private int objX;
    private int objY;
    private Image image;
    private int columns = 2;
    private int height = 30;
    private int width = 20;
    private int frame = 1;
    private String lever = "Resources\\Images\\Interactables\\LeverSprite.png";

    private boolean leverIsDown = false;
    private boolean leverIsUp = true;
    private char type = 'l';
    private String door;
    private World world;
    private int doorNumber;
    private int sequence;
    ///STEVEN U SUCK!
    //which door------# in combo sequence
    public Lever(int x, int y, Player p, World w, String d, int s, int seq) {
        super(x, y, p, w);
        doorNumber = s;
        sequence = seq;
        objX = x;
        objY = y;
        ImageIcon lever1 = new ImageIcon(this.getClass().getResource(lever));
        image = lever1.getImage();
        leverIsUp = true;
        world = w;
        door = d;
        setImage(loadImg());
    }

    public Image getImage() {
        return image;
    }

    public void switchImage() {
        //switches lever image to down
        //System.out.println("   " + doorNumber + "!!!");

        if (isLeverDown()) {
            switch (doorNumber) {
                case 1:
                world.addCombo1("0", sequence);
                frame = 1;
                //System.out.println("Lever Moved Down");
                leverIsDown = false;
                leverIsUp = true;
                this.switchDoor(true);
                break;

                case 2:
                world.addCombo2("0", sequence);
                frame = 1;
                //System.out.println("Lever Moved Down");
                leverIsDown = false;
                leverIsUp = true;
                this.switchDoor(true);
                break;

                case 3:
                world.addCombo3("0", sequence);
                frame = 1;
                //System.out.println("Lever Moved Down");
                leverIsDown = false;
                leverIsUp = true;
                this.switchDoor(true);
                break;

                case 4:
                world.addCombo4("0", sequence);
                frame = 1;
                //System.out.println("Lever Moved Down");
                leverIsDown = false;
                leverIsUp = true;
                this.switchDoor(true);
                break;

                case 5:
                world.addCombo5("0", sequence);
                frame = 1;
                //System.out.println("Lever Moved Down");
                leverIsDown = false;
                leverIsUp = true;
                this.switchDoor(true);
                break;

                default:

                //System.out.println("Lever Moved Down");
                leverIsDown = false;
                leverIsUp = true;
                this.switchDoor(false);
                frame = 1;
                break;
            }
        }

        else if (isLeverUp()) {

            switch (doorNumber) {
                case 1:
                world.addCombo1("1", sequence);
                frame = 0;
                //System.out.println("Lever Moved Up");
                leverIsUp = false;
                leverIsDown = true;
                switchDoor(true);
                break;

                case 2:
                world.addCombo2("1", sequence);
                frame = 0;
                //System.out.println("Lever Moved Up");
                leverIsUp = false;
                leverIsDown = true;
                switchDoor(true);
                break;

                case 3:
                world.addCombo3("1", sequence);
                frame = 0;
                //System.out.println("Lever Moved Up");
                leverIsUp = false;
                leverIsDown = true;
                switchDoor(true);
                break;

                case 4:
                world.addCombo4("1", sequence);
                frame = 0;
                //System.out.println("Lever Moved Up");
                leverIsUp = false;
                leverIsDown = true;
                switchDoor(true);
                break;

                case 5:
                world.addCombo5("1", sequence);
                frame = 0;
                //System.out.println("Lever Moved Up");
                leverIsUp = false;
                leverIsDown = true;
                switchDoor(true);
                break;

                default:

                //System.out.println("Lever Moved Up");
                leverIsDown = true;
                leverIsUp = false;
                this.switchDoor(false);
                frame = 0;
                break;
            }
        }
    }

    /**
    private void switchSingleDoor() {
    if (isLeverDown()) {
    frame = 1;
    System.out.println("Lever Moved Down");
    leverIsDown = false;
    leverIsUp = true;
    this.switchDoor();
    }

    else if (isLeverUp()) {
    frame = 0;
    System.out.println("Lever Moved Up");
    leverIsUp = false;
    leverIsDown = true;
    this.switchDoor();
    }
    }
     */

    private boolean isLeverDown() {
        return leverIsDown;
    }

    private boolean isLeverUp() {
        return leverIsUp;
    }

    public char getType(){
        return type;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getFrame() {
        return frame;
    }

    public int getColumns() {
        return columns;
    }

    public void switchDoor(boolean comboDoor) {
        for (Door d : world.getDoorObjects()) {
            //System.out.println("The door length is " + world.getDoorObjects().size());
            if (comboDoor) {
                switch (doorNumber) {
                    case 1:

                    if (d.getName().equals(door) && world.getCombo1().equals(d.getCombo())) {
                        //System.out.print("SUCCESS");
                        d.switchImage(true, false);
                        break;
                    }
                    break;

                    case 2:

                    if (d.getName().equals(door) && world.getCombo2().equals(d.getCombo())) {
                        d.switchImage(true, false);
                        break;
                    }
                    break;

                    case 3:

                    if (d.getName().equals(door) && world.getCombo3().equals(d.getCombo())) {
                        d.switchImage(true, false);
                        break;
                    }
                    break;

                    case 4:

                    if (d.getName().equals(door) && world.getCombo4().equals(d.getCombo())) {
                        d.switchImage(true, false);
                        break;
                    }
                    break;

                    case 5:

                    if (d.getName().equals(door) && world.getCombo5().equals(d.getCombo())) {
                        d.switchImage(true, false);
                        break;
                    }
                    break;
                }
            }
            else if (d.getName().equals(door) && !comboDoor) {
                d.switchImage(true, true);
                break;

            }

        }
    }

    public Image loadImg(){//load an image from 

        ImageIcon icon = new ImageIcon(this.getClass().getResource(lever));
        Image img = icon.getImage();
        return img;
    }

    private String getValue() {
        return Integer.toString(frame);
    }

    //   public int getBaseX();
    //  public int getBaseY();
    //  public int getCollisionX();
    //  public int getCollisionY();
    //  public int getCollisionXStart();
    // public int getCollisionYStart();
    // public boolean isVisible();
}
