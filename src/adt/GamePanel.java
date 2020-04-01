package adt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * adt.TDPanel, or Tower Defense Panel, is the method that handles displaying all the graphics from the game.
 *
 * adt.TDPanel is in charge of graphics. This includes, at the moment, buttons for placing towers, starting rounds,
 * and a display of the current money and lives that the player has. It is also the method that is in charge of
 * detecting player input, with mouse and button listeners. It extends JPanel to allow it to be added to the JFrame,
 * and implements Runnable so it can utilize a thread to display graphics.
 */
public class GamePanel extends JPanel implements Runnable {

    private int state = 0;
    private JButton placeTower;
    /**
     *startRound is a variable that allows for an enemy to be placed at the start of the path.
     *
     * This button is currently not planned to be used as it is, but functionality will be added to make the
     * game progress using this button.
     */
    private JButton startRound;
    /**
     *model is the representation of the game logic that this class will use to show information to the player.
     *
     * model contains the lists of enemies and towers, the current amount of money and lives the player has, and
     * multiple methods to assist this panel in calculations necessary for the game.
     */
    private TDModel model;
    /**
     * An instantiation of the private class MListener, used for getting information about the mouse from the player.
     */
    private MListener mouse;
    /**
     * Boolean instance variable that keeps track of when the Place Tower JButton is pressed, to allow the player to
     * click where they want to place the tower.
     */
    private boolean isPlaceTower;
    /**
     * The instance variable that contains the image of the first version of the map.
     */
    private BufferedImage map;

    /**
     * Button used for testing the animations of the enemies, is now used for testing different aspects of the game logic.
     */
    private JButton oneStep;
    /**
     * Label to convey to the player how much money they have.
     *
     * This is updated every time the amount of money changes.
     */
    private JLabel moneyLabel;
    /**
     * Label to convey to the player how many lives they have.
     *
     * This is updated every time the amount of lives changes.
     */
    private JLabel livesLabel;
    /**
     * Label to convey to the player how much score they have.
     *
     * This is updated every time a enemy dies or a new round is started.
     */
    private JLabel scoreLabel;
    private Thread graphicsThread;
    private final int delay = 17;
    private long frameCount = 0;
    /*
    Constructor calls addMouseListener, addButtons, and addImages. AddMouseListener is a JPanel method, while addButtons
    and addImages are just private methods made to compartmentalize the code a little better.
     */

    /**
     * Constructor that sets all variables to their default or generic state.
     *
     * Initializes all images and labels used by this panel, and initializes all listeners necessary, as well as setting
     * instance variables to their desired initial state for every game.
     */
    GamePanel(){
        model = new TDModel();
        mouse = new MListener();
        addMouseListener(mouse);
        Listener listener = new Listener();
        addButtons(listener);
        addLabels();
        addImages();
        isPlaceTower = false;

        //thread for animations, adding the panel to have the thread run
        graphicsThread = new Thread(this);
        graphicsThread.start();

    }
    /*
    addButtons adds the button with defining text, adds it to the panel, and adds an actionListener to it.
     */
    private void addButtons(Listener listener){
        placeTower = new JButton("Place Tower");
        add(placeTower);
        placeTower.addActionListener(listener);

        startRound = new JButton("START ROUND");
        add(startRound);
        startRound.addActionListener(listener);
    }

    private void addImages(){
        try {
            map = ImageIO.read(new File(getClass().getResource("resources/map.png").toURI()));

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void addLabels(){
        moneyLabel = new JLabel("money: " + model.money());
        add(moneyLabel);

        scoreLabel = new JLabel("Score: " + model.score());
        add(scoreLabel);

        livesLabel = new JLabel("lives: " + model.lives());
        add(livesLabel);
    }

    //helper methods to put drawing enemies on the screen somewhere else for readability
    private void drawEnemy(Enemy enemy, Graphics g){
        g.drawImage(enemy.graphic(), enemy.position().x - (enemy.graphic().getWidth() / 2), enemy.position().y - (enemy.graphic().getHeight() / 2), null);
        g.drawImage(enemy.healthBar(), (int)enemy.position().getX() - (enemy.graphic().getWidth() / 2), (int)enemy.position().getY() - (enemy.graphic().getHeight() / 2),null);
    }
    private void drawTower(Tower tower, Graphics g){
        g.drawImage(tower.graphic(), tower.position().x - tower.graphic().getWidth() / 2, tower.position().y - tower.graphic().getHeight() / 2,null );
    }

    /**
     * The method required by extending JFrame to draw the graphics on the screen. Not called directly.
     * @param g is provided by java when calling repaint().
     */
    @Override
    public void paintComponent(Graphics g){
        updateLabels();
        super.paintComponent(g);
        g.drawImage(map, 0, 0, null);
        if (model.getTowers() != null) {
            for (Tower eachTower : model.getTowers()) {
                drawTower(eachTower, g);
            }
        }
        if (model.getEnemies() != null) {
            for (Enemy eachEnemy : model.getEnemies()) {
                drawEnemy(eachEnemy, g);
            }
        }
    }
    private void updateLabels(){
        moneyLabel.setText("Money: " + model.money());
        scoreLabel.setText("Score: " + model.score());
        livesLabel.setText("Lives: " + model.lives());
    }
    //This method is the method outlined by the Runnable interface. This makes it so that this method is run on a separate
    //thread from the game logic, allowing animations to happen simultaneously with the logic. Needs more comments about
    //why it works


    /**
     * Method that is outlined by the implementation of Runnable.
     *
     * This method keeps track of the time since the last call of model.update(), and delays to attempt to keep a 60
     * frame per second refresh rate going, and repaints after every attempt.
     */
    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        while(graphicsThread.isAlive()) {
            long time = System.currentTimeMillis();

            model.update(frameCount);
            long currDelay = System.currentTimeMillis() - time;
            if (currDelay < delay) {

                try {
                    Thread.sleep(delay - currDelay);
                    frameCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            repaint();
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
                model.beginRound(frameCount);

                repaint();

            }
            /*
            My attempt to watch the 'animation' in a step by step process.
             */
            else if(e.getSource() == oneStep){

                model.getEnemies().add(new Enemy(new Point(400, 400)));
                repaint();
                Enemy mine = model.getEnemies().get(model.getEnemies().size() - 1);
                mine.takeDamage(mine.health() / 2);

                getGraphics().drawImage(mine.healthBar(), (int) mine.position().getX(), (int) mine.position().getY(), null);
                System.out.println("here");
            }
        }
    }
    /*
    Class that listens to the mouse
     */
    private class MListener extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            //When the mouse is clicked on the map after clicking place tower, a tower is placed centered around the mouse
            //and shown.
            if(isPlaceTower){
                Point p = new Point(mouseEvent.getX(), mouseEvent.getY());
                Tower t = new Tower(p);
                if(!model.placeTower(t)){
                    //TODO: make this game graphics instead of JOptionPane, makes it look cheap
                    JOptionPane.showMessageDialog(null, "Not Enough Money");
                }
                repaint();
                isPlaceTower = false;
            }
            else{
                System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
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
