package src;


import javax.swing.JFrame;

import java.io.File;
@SuppressWarnings({ "serial", "unused" })
public class Exe extends JFrame 
{
    public Exe() 
    {
        add(new Board());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(1000, 750);
        setLocationRelativeTo(null);
        setTitle("TSA Video Game");
        setResizable(false);
        setVisible(true);
        //Desktop.getDesktop().open(new File("VidIntro2.mp4"));
    }

    public static void main(String[] args) 
    {
        new Exe();
    }
}