import javax.swing.*;
import java.awt.Graphics;

public class GameWindow extends JPanel{

    public void paint(Graphics g){
        }
    public static void main(String[] args){
        JFrame frame = new JFrame("Please work");
        TDPanel panel = new TDPanel();
        frame.getContentPane().add(panel);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        }
}