import javax.swing.*;
import java.awt.Graphics;

public class GameWindow extends JPanel{

    public void paint(Graphics g){
        }
    public static void main(String[] args){
        JFrame frame = new JFrame("Please work");
        frame.getContentPane().add(new GameWindow());
        }
}