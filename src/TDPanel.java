import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * TDPanel, or Tower Defense Panel, is the method that handles putting all of the graphics from the game up.
 * This includes, at the moment, buttons for placing towers, starting rounds, and stepping once with enemy movement.
 * It is also the method that is in charge of detecting player input, with mouse and button listeners.
 *
 *  **Will probably have to eventually implement Runnable to make smooth animations.
 */
public class TDPanel extends JPanel {
    private JButton placeTower;
    private JButton startRound;
    private TDModel model;
    private MListener m = new MListener();
    private Boolean isPlaceTower = false;
    private ImageIcon tower;
    private ImageIcon map;
    private ImageIcon enemy;
    private JButton oneStep;

    /*
    Constructor calls addMouseListener, addButtons, and addImages. AddMouseListener is a JPanel method, while addButtons
    and addImages are just private methods made to compartmentalize the code a little better.
     */
    /*TODO:
    -Decide on a layout model (gridbaglayout, gridlayout, etc.)
    -Make animations smooth/ work in loops (repaint() doesn't want to update the graphics while its in a loop)
    -Possible make other panel for buttons, and this panel for the actual game like bloons
    -Make towers and enemies interact using a range between their two positions
    -display enemy health when hovered over/ permanently / display at bottom?
    -Make enemy position the center of the graphic if it is not already
    -Make isPlaceTower flag able to be set to false without placing a tower
    -Add checking to placeTower to make sure its not on the path.
    -Draw up a general form for how rounds go in terms of number and pace of enemies, and implement that.
     */
    public TDPanel(){
        model = new TDModel();
        addMouseListener(m);
        Listener listener = new Listener();
        addButtons(listener);
        addImages();
    }
    /*
    addButtons adds the button with defining text, adds it to the panel, and adds an actionlistener to it.
     */
    private void addButtons(Listener listener){
        placeTower = new JButton("Place Tower");
        add(placeTower);
        placeTower.addActionListener(listener);

        startRound = new JButton("START");
        add(startRound);
        startRound.addActionListener(listener);

        oneStep = new JButton("Step");
        add(oneStep);
        oneStep.addActionListener(listener);
    }
    /*
    addImages assigns the ImageIcons an actual image, which are all stored in the Images directory at the moment.
     */
    private void addImages(){
        tower = new ImageIcon("Images/tower.png");
        map = new ImageIcon("Images/map.png");
        enemy = new ImageIcon("Images/enemy.png");
    }
    /*
    Spawns an enemy then shows it. The commented out portion was an attempt to make the model move the way I wanted it
    to, but this is where the Runnable class will come in handy.
     */
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


    /*
    This is the method that is eventually called by repaint(). It paints the map since I can't figure out how to only
    paint the map once, then goes through the list of towers and enemies and draws them on top.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        map.paintIcon(this, g, 0, 0);
        //Probably add an "If towers has changed, update towers, otherwise just keep it the same
        //In typing that, I realized that it has to redraw the whole thing every time since that's how the method works
        //but I'll leave the comment in case we figure out how to only redraw the enemies.
        if(model.getTowers() != null) {
            for (Tower eachTower : model.getTowers()) {
                tower.paintIcon(this, g, eachTower.getPosition().x, eachTower.getPosition().y );
            }
        }
        if(model.getEnemies() != null) {
            for (Enemy eachEnemy : model.getEnemies()) {
                enemy.paintIcon(this, g, eachEnemy.position().x, eachEnemy.position().y - 15);
            }
        }
    }

    /*
    A class that only exists in the panel itself to listen for actions from the player.
     */
    private class Listener implements ActionListener{
        /*
        The general function that gets called whenever something with the listener added to it gets interacted with
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            //Push the button, set the flag so that the next mouse click actually places a tower. Impossible to get
            //the program to wait for the mouse to be clicked, so the best course for now is to set a flag so the next
            //place clicked is the location of the tower.
            if(e.getSource()  == placeTower){
                isPlaceTower = true;
                System.out.println("Place tower clicked");
            }
            //Currently just places one enemy at the start of the path and displays it.
            else if(e.getSource() == startRound){
                startRound();
                repaint();

            }
            /*
            My attempt to watch the 'animation' in a step by step process.
             */
            else if(e.getSource() == oneStep){
                for(int i = 0; i < 10; i++) {
                    model.getEnemies().get(0).setPosition(model.getEnemies().get(0).position().x + 10, model.getEnemies().get(0).position().y);
                    repaint();
                    try{
                        Thread.sleep(1000);
                    }catch (Exception ignored){

                    }

                }
            }
        }
    }
    /*
    Class that listens to the mouse
     */
    private class MListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            //When the mouse is clicked on the map after clicking place tower, a tower is placed centered around the mouse
            //and shown.
            if(isPlaceTower){
                Point p = new Point(mouseEvent.getX() - tower.getIconWidth() / 2, mouseEvent.getY() - tower.getIconHeight() / 2);
                Tower t = new Tower(p);
                model.placeTower(t);
                isPlaceTower = false;
                repaint();
            }
        }
        //all methods that need to be overwritten in the mouse listener class
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
