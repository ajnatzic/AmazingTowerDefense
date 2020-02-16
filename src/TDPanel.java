import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TDPanel extends JPanel {
    private JButton placeTower;
    private TDModel model;
    private MListener m = new MListener();
    private Boolean isPlaceTower = false;
    private ImageIcon tower;
    public TDPanel(){
        model = new TDModel();
        placeTower = new JButton("Place Tower");
        add(placeTower);
        addMouseListener(m);
        Listener listener = new Listener();
        placeTower.addActionListener(listener);
        addImages();
    }

    private void addImages(){
        tower = new ImageIcon("Images/Tower.png");
    }


    public void displayGame(){
        for(Tower eachTower : model.getTowers()){


        }
    }

    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()  == placeTower){
                isPlaceTower = true;
                System.out.println("Place tower clicked");
            }
        }
    }
    private class MListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(isPlaceTower){
                Point p = new Point(MouseInfo.getPointerInfo().getLocation());
                Tower t = new Tower(p);
                model.placeTower(t);
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}
