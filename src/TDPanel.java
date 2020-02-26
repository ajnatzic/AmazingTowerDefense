import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * TDPanel, or Tower Defense Panel, is the method that handles putting all of the graphics from the game up.
 * This includes, at the moment, buttons for placing towers, starting rounds, and stepping once with enemy movement.
 * It is also the method that is in charge of detecting player input, with mouse and button listeners.
 *
 *  **Will probably have to eventually implement Runnable to make smooth animations.
 */
public class TDPanel extends JPanel implements Runnable {
    /*

    BUTTON NAMES ARE NOT RIGHT I MESSED UP

     */

    private JButton placeTower;
    private JButton startRound;
    private TDModel model;
    private MListener m = new MListener();
    private Boolean isPlaceTower = false;
    private BufferedImage tower;
    private BufferedImage map;
    private BufferedImage enemy;
    private JButton oneStep;
    private JButton startStop;
    private boolean animationState = false;
    private Thread t1;
    private int deltaX;
    private int deltaY;
    private final int DELAY = 17;
    /*
    Constructor calls addMouseListener, addButtons, and addImages. AddMouseListener is a JPanel method, while addButtons
    and addImages are just private methods made to compartmentalize the code a little better.
     */
    /*TODO:
    -Decide on a layout model (gridbaglayout, gridlayout, etc.)
    -Possible make other panel for buttons, and this panel for the actual game like bloons
    -Make towers and enemies interact using a range between their two positions
    -Make enemy position the center of the graphic if it is not already
    -Make isPlaceTower flag able to be set to false without placing a tower
    -Add checking to placeTower to make sure its not on the path.
    -Draw up a general form for how rounds go in terms of number and pace of enemies, and implement that.
    -Make enemies follow path
    -Start round spawns enemies based on round number
     */

    /**
     * constructor
     */
    public TDPanel(){
        model = new TDModel();
        addMouseListener(m);
        Listener listener = new Listener();
        addButtons(listener);
        addImages();
        //thread for animations, adding the panel to have the thread run
        t1 = new Thread(this);
        deltaX = 0;
        deltaY = 0;

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
    /*
    Spawns an enemy then shows it.
     */
    private void startRound(){
        model.spawnEnemy();
        repaint();
    }
    //helper methods to put drawing enemies on the screen somewhere else for readability
    private void drawEnemy(Enemy e, Graphics g){
        g.drawImage(enemy, e.position().x, e.position().y, null);
        g.drawImage(e.healthBar(), (int)e.position().getX(), (int)e.position().getY(),null);
    }
    private void drawTower(Tower t, Graphics g){
        g.drawImage(tower, t.getPosition().x - tower.getWidth() / 2, t.getPosition().y - tower.getHeight() / 2,null );
    }
    /*
    This is the method that is eventually called by repaint().
     */
    @Override
    public void paintComponent(Graphics g){
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
    //This method is the method outlined by the Runnable interface. This makes it so that this method is run on a separate
    //thread from the game logic, allowing animations to happen simultaneously with the logic. Needs more comments about
    //why it works
    private double distanceBetween(Point p1, Point p2){
        return Math.pow(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2), .5);
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        int distanceToTravel = 15;
        double angle;
        long time = System.currentTimeMillis();
        while(animationState){

            if(model.getEnemies().size() == 0){
                model.getEnemies().add(new Enemy(model.path().get(0).x - enemy.getWidth() / 2,model.path().get(0).y - enemy.getHeight() / 2));
                repaint();
            }
            for(Enemy e : model.getEnemies()){
                Point target = model.path().get(e.currentPathTarget());
                if(distanceBetween(target, e.position()) < distanceToTravel){
                    e.goToNextTarget();
                    target = model.path().get(e.currentPathTarget());
                }
                angle = Math.atan(((double)(e.position().y - target.y) / (e.position().x - target.x)));
                deltaX = (int) (distanceToTravel * Math.cos(angle));
                deltaY = (int) (distanceToTravel * Math.sin(angle));
                int newX = e.position().x + deltaX, newY = e.position().y + deltaY;
                if(newX < 800 && newY < 700)
                    e.setPosition(newX, newY);
            }
            if(model.isEnemyInRange()){
                for(int i = 0; i < model.getEnemies().size(); i++){
                    Enemy e = model.getEnemies().get(i);
                    if(e.health() == 0){
                        model.killEnemy(e);
                    }
                }
                repaint();
            }

            /*long currDelay = System.currentTimeMillis() - time;
            if(currDelay < DELAY) {
                try {
                    Thread.sleep(DELAY - currDelay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
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
                startRound();
                repaint();

            }
            /*
            My attempt to watch the 'animation' in a step by step process.
             */
            else if(e.getSource() == oneStep){

                model.getEnemies().add(new Enemy(new Point(400, 400)));
                repaint();
                Enemy mine = model.getEnemies().get(0);
                mine.takeDamage(mine.health() / 2);

                getGraphics().drawImage(mine.healthBar(), (int) mine.position().getX(), (int) mine.position().getY(), null);
                System.out.println("here");
            }
            else if(e.getSource() == startStop){
                if(!animationState){
                    t1.start();
                }
                if(t1.isAlive() && animationState){
                    t1.interrupt();
                }
                animationState = !animationState;

                System.out.println("animationState: " + animationState);
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
                model.placeTower(t);
                isPlaceTower = false;
                repaint();
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
