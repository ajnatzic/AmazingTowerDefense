import javax.swing.*;
import java.awt.Graphics;

/**
 *
 * Basic JFrame implementation
 */
public class GameWindow extends JPanel{

    public void paint(Graphics g){

        }
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