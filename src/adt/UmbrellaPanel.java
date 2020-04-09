package adt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UmbrellaPanel extends JPanel implements Runnable {

    private GamePanel gamePanel;
    private MainMenuPanel mainMenuPanel;
    private ButtonPanel buttonPanel;
    private TDModel model;
    private MListener mouse;
    private boolean isPlaceTower;
    private Thread graphicsThread;
    private long frameCount = 0;
    private final int delay = 17;
    private int state = 0;

    public UmbrellaPanel(){
        model = new TDModel();
        initializePanels();
        mouse = new MListener();
        addMouseListener(mouse);
        isPlaceTower = false;
        graphicsThread = new Thread(this);
        graphicsThread.start();
    }

    private void initializePanels(){
        gamePanel = new GamePanel(model);
        mainMenuPanel = new MainMenuPanel();
        buttonPanel = new ButtonPanel(model, this);
        add(gamePanel);
        add(mainMenuPanel);
        add(buttonPanel);
    }

    public void placeTower(){
        isPlaceTower = true;
        System.out.println("Place tower clicked");
    }

    public void startRound(){
        model.beginRound(frameCount);

        repaint();
    }

    private class MListener extends MouseAdapter {

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
            if(model.lives() <= 0){
                JOptionPane.showMessageDialog(null, "Game Over");
                //set state to menu
            }
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
}
