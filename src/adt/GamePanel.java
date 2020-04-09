package adt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
public class GamePanel extends JPanel  {




    private TDModel model;
    private BufferedImage map;
    private JLabel moneyLabel;
    private JLabel livesLabel;
    private JLabel scoreLabel;




    /**
     * Constructor that sets all variables to their default or generic state.
     *
     * Initializes all images and labels used by this panel, and initializes all listeners necessary, as well as setting
     * instance variables to their desired initial state for every game.
     */
    GamePanel(TDModel modelFromUmbrella){
        addLabels();
        addImages();
        model = modelFromUmbrella;
    }
    /*
    addButtons adds the button with defining text, adds it to the panel, and adds an actionListener to it.
     */


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
        g.drawImage(enemy.healthBar(), enemy.position().x  - (enemy.healthBar().getWidth() / 2), enemy.position().y - (enemy.graphic().getHeight() / 2) - 15,null);
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
            for(int i = 0; i < model.getEnemies().size(); i++){
                Enemy eachEnemy = model.getEnemies().get(i);
                drawEnemy(eachEnemy, g);
            }
        }
    }
    private void updateLabels(){
        moneyLabel.setText("Money: " + model.money());
        scoreLabel.setText("Score: " + model.score());
        livesLabel.setText("Lives: " + model.lives());
    }

}
