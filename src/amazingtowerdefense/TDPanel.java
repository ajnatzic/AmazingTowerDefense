package amazingtowerdefense;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
/*TODO:
    -Possible make other panel for buttons, and this panel for the actual game like bloons
    -Make isPlaceTower flag able to be set to false without placing a tower
    -Add checking to placeTower to make sure its not on the path.
    -Draw up a general form for how rounds go in terms of number and pace of enemies, and implement that.
    -Start round spawns enemies based on round number
    -Fix exceptions when:
        -stopping animation button (add new button for stopping)
    -Make a level class potentially, to hold info about how many and
        of what type the enemies in a round number should be
    -test all range functions to ensure they output the right values
     */

/**
 * amazingtowerdefense.TDPanel, or amazingtowerdefense.Tower Defense Panel, is the method that handles displaying all the graphics from the game.
 *
 * amazingtowerdefense.TDPanel is in charge of graphics. This includes, at the moment, buttons for placing towers, starting rounds,
 * and a display of the current money and lives that the player has. It is also the method that is in charge of
 * detecting player input, with mouse and button listeners. It extends JPanel to allow it to be added to the JFrame,
 * and implements Runnable so it can utilize a thread to display graphics.
 */
public class TDPanel extends JPanel implements Runnable {


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
     * Boolean instance variable that keeps track of when the Place amazingtowerdefense.Tower JButton is pressed, to allow the player to
     * click where they want to place the tower.
     */
    private boolean isPlaceTower;
    /**
     * The instance variable that contains the image of the generic tower.
     */
    private BufferedImage tower;
    /**
     * The instance variable that contains the image of the first version of the map.
     */
    private BufferedImage map;
    /**
     * The instance variable that contains the image of the generic enemy.
     */
    private BufferedImage enemy;
    /**
     * Button used for testing the animations of the enemies, is now used for testing different aspects of the game logic.
     */
    private JButton oneStep;
    /**
     * Button used to start the animation of the enemies moving down the path.
     *
     * This button sets a flag that allows the run() method containint all logic for moving an enemy and hit detection
     * to run, until the flag is set to false.
     */
    private JButton startStop;
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
    private boolean animationState;
    private Thread thread1;
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
    public TDPanel(){
        super();
        model = new TDModel();
        mouse = new MListener();
        addMouseListener(mouse);
        Listener listener = new Listener();
        addButtons(listener);
        addLabels();
        addImages();
        isPlaceTower = false;
        animationState = false;
        //thread for animations, adding the panel to have the thread run
        thread1 = new Thread(this);


    }
    /*
    addButtons adds the button with defining text, adds it to the panel, and adds an actionlistener to it.
     */
    private void addButtons(Listener listener){
        placeTower = new JButton("Place amazingtowerdefense.Tower");
        add(placeTower);
        placeTower.addActionListener(listener);

        startRound = new JButton("START");
        add(startRound);
        startRound.addActionListener(listener);

        oneStep = new JButton("Step");
        add(oneStep);
        oneStep.addActionListener(listener);

        startStop = new JButton("start/stop animation");
        add(startStop);
        startStop.addActionListener(listener);
    }
    /*
    addImages assigns the ImageIcons an actual image, which are all stored in the Images directory at the moment.
     */
    private void addImages(){

        try {
            tower = ImageIO.read(new File(getClass().getResource("resources/tower.png").toURI()));
            map = ImageIO.read(new File(getClass().getResource("resources/map.png").toURI()));
            enemy = ImageIO.read(new File(getClass().getResource("resources/enemy.png").toURI()));
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
    /*
    Spawns an enemy then shows it.
     */
    private void startRound(){
        model.spawnEnemy();
        repaint();
    }
    //helper methods to put drawing enemies on the screen somewhere else for readability
    private void drawEnemy(Enemy enemyToDraw, Graphics g){
        g.drawImage(this.enemy, enemyToDraw.position().x - (this.enemy.getWidth() / 2), enemyToDraw.position().y - (this.enemy.getHeight() / 2), null);
        g.drawImage(enemyToDraw.healthBar(), (int)enemyToDraw.position().getX() - (this.enemy.getWidth() / 2), (int)enemyToDraw.position().getY() - (this.enemy.getHeight() / 2),null);
    }
    private void drawTower(Tower towerToDraw, Graphics g){
        g.drawImage(this.tower, towerToDraw.getPosition().x - this.tower.getWidth() / 2, towerToDraw.getPosition().y - this.tower.getHeight() / 2,null );
    }

    /**
     * The method required by extending JFrame to draw the graphics on the screen. Not called directly.
     * @param g is provided by java when calling repaint().
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateLabels();
        super.paintComponent(g);
        g.drawImage(map, 0, 0, null);
        if(model.getTowers() != null) {
            for (Tower eachTower : model.getTowers()) {
                drawTower(eachTower, g);
            }
        }
        if(model.getEnemies() != null) {
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
     * This method is the one that moves enemies using a given distance to travel and an angle of travel,
     * calculates hit detection using a range function between towers and enemies.
     */
    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while(animationState){
            long time = System.currentTimeMillis();
            if(model.getEnemies().size() == 0){
                model.getEnemies().add(new Enemy(model.path().get(0).x,model.path().get(0).y));
            }
            model.update(time);
            long currDelay = System.currentTimeMillis() - time;
            if(currDelay < delay) {

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
        private Listener(){
            super();
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //Push the button, set the flag so that the next mouse click actually places a tower. Impossible to get
            //the program to wait for the mouse to be clicked, so the best course for now is to set a flag so the next
            //place clicked is the location of the tower.
            if(actionEvent.getSource()  == placeTower){
                isPlaceTower = true;
            }
            //Currently just places one enemy at the start of the path and displays it.
            else if(actionEvent.getSource() == startRound){
                startRound();
                repaint();

            }
            /*
            My attempt to watch the 'animation' in a step by step process.
             */
            else if(actionEvent.getSource() == oneStep){

                model.getEnemies().add(new Enemy(new Point(400, 400)));
                repaint();
                Enemy mine = model.getEnemies().get(model.getEnemies().size() - 1);
                mine.takeDamage(mine.health() / 2);

                getGraphics().drawImage(mine.healthBar(), (int) mine.position().getX(), (int) mine.position().getY(), null);
            }
            else if(actionEvent.getSource() == startStop){
                if(!animationState && !thread1.isAlive()){
                    thread1.start();
                }
                if(thread1.isAlive() && animationState){
                    thread1.interrupt();
                }
                animationState = !animationState;
            }
        }
    }
    /*
    Class that listens to the mouse
     */
    private class MListener extends MouseAdapter{
        private MListener(){
            super();
        }
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            //When the mouse is clicked on the map after clicking place tower, a tower is placed centered around the mouse
            //and shown.
            if(isPlaceTower){
                Point mousePoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                Tower newTower = new Tower(mousePoint);
                if(!model.placeTower(newTower)){
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