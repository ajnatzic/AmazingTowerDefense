import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TDPanel extends JPanel {
    private JButton placeTower;
    private JButton startRound;
    private TDModel model;
    private MListener m = new MListener();
    private Boolean isPlaceTower = false;
    private ImageIcon tower;
    private ImageIcon map;
    private ImageIcon enemy;


    public TDPanel(){
        model = new TDModel();
        addMouseListener(m);
        Listener listener = new Listener();
        addButtons(listener);
        addImages();
    }
    private void addButtons(Listener listener){
        placeTower = new JButton("Place Tower");
        add(placeTower);
        placeTower.addActionListener(listener);

        startRound = new JButton("START");
        add(startRound);
        startRound.addActionListener(listener);

    }
    private void addImages(){
        tower = new ImageIcon("Images/towerAS.png");
        map = new ImageIcon("Images/map.png");
        enemy = new ImageIcon("Images/enemy.png");
    }

    private void startRound(){
        model.spawnEnemy();
        /*while(model.getEnemies().get(0).position() != model.path().get(1) && model.getEnemies().get(0).position().x < 800 ){
            model.getEnemies().get(0).setPosition(model.getEnemies().get(0).position().x + 1, model.getEnemies().get(0).position().y);
            repaint();
            try {
                Thread.sleep(200);
            }
            catch(Exception ignored){

            }
            System.out.println(model.getEnemies().get(0).position().x);
        }*/
        repaint();
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        map.paintIcon(this, g, 0, 0);
        if(model.getTowers() != null) {
            for (Tower eachTower : model.getTowers()) {
                tower.paintIcon(this, g, eachTower.getPosition().x - 15, eachTower.getPosition().y - 15);
            }
        }
        if(model.getEnemies() != null) {
            for (Enemy eachEnemy : model.getEnemies()) {
                enemy.paintIcon(this, g, eachEnemy.position().x, eachEnemy.position().y - 15);
            }
        }
    }


    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()  == placeTower){
                isPlaceTower = true;
                System.out.println("Place tower clicked");
            }
            else if(e.getSource() == startRound){
                startRound();
                repaint();

            }
        }
    }
    private class MListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(isPlaceTower){
                Point p = new Point(mouseEvent.getX(), mouseEvent.getY());
                Tower t = new Tower(p);
                model.placeTower(t);
                isPlaceTower = false;
                repaint();
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
