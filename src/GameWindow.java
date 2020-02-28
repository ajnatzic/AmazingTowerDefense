import javax.swing.*;
import java.awt.Graphics;

/**
 * Basic JFrame implementation that allows for graphical representation in Java.
 */
public class GameWindow extends JPanel{

    /**
     * Necessary paing function for the JPanel extension.
     * @param g - graphics provided by Java
     */
    public void paint(Graphics g){

    }

    /**
     * Main method that the whole program runs through.
     * @param args
     */
    public static void main(String[] args){
        JFrame frame = new JFrame("Amazing Tower Defense");
        TDPanel panel = new TDPanel();
        frame.getContentPane().add(panel);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        }
}