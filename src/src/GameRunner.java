package src;


import javax.swing.JFrame;

import java.io.File;
@SuppressWarnings({ "serial", "unused" })
public class GameRunner extends JFrame 
{

    public  GameRunner() 
    {
        add(new MainMenuBoard());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 480);
        setLocationRelativeTo(null);
        setTitle("Home Page");
        setResizable(false);
        setVisible(true);
    }

    public  void newBoard() 
    {
        add(new Board());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Chrononaut");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) 
    {
        new GameRunner();
    }

}