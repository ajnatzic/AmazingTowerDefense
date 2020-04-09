package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * adt.Enemy class to represent the basic enemies in the game.
 *
 * Attack, cost, health, physArmor, and elementalArmor are all integers that represent exactly what they say.
 * Position is a point, hopefully directly in the middle of the object, that tells the game where the enemy is.
 * This class will be used as a template for specialized enemies.
 */
public class Enemy{
    private int travelDistance;
    private int currHealth;
    private int totalHealth;
    private Point position;
    private BufferedImage healthBar;
    private int currentPathTarget;
    private int value;
    private int score;
    int distanceTraveled;
    private long timeOfLastMove;
    private long timeToMove;
    private BufferedImage graphic;

    /**
     * Constructor for an enemy class that puts the enemy at the position indicated by the point passed.
     * @param position is where the enemy will be placed, both graphically and logically.
     */
    public Enemy(Point position) {
        this.position = position;
        initialize();
    }

    /**
     * Constructor made to allow the enemy to be initialized using coordinates instead of a Point object.
     * @param x is the position relative to the x axis.
     * @param y is the position relative to the y axis.
     */
    public Enemy(int x, int y) {
        this.position =  new Point(x, y);
        initialize();
    }

    public Enemy(int x, int y, int health, int unitValue, int unitScore, int speed){
        position = new Point(x, y);
        currHealth = health;
        totalHealth = health;
        this.value = unitValue;
        this.score = unitScore;
        travelDistance = speed;
        initializeGeneral();
    }
    private void initializeGeneral(){
        currentPathTarget = 1;
        setHealthBar();
        distanceTraveled = 0;
        timeToMove = 50;
        timeOfLastMove = System.currentTimeMillis() - timeToMove;
    }
    private void initialize(){
        travelDistance = 5;
        currHealth = 20;
        totalHealth = 20;
        currentPathTarget = 1; // spawns at 0, wants to go to one
        setHealthBar();
        value = 5;
        score = 5;
        distanceTraveled = 0;
        timeToMove = 50;
        timeOfLastMove = System.currentTimeMillis() - timeToMove;
        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/enemy.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Getter for the image of the health bar.
     * @return a BufferedImage representing the health bar of the enemy.
     */
    public BufferedImage healthBar(){
        return healthBar;
    }
    public BufferedImage graphic(){
        return graphic;
    }

    //setHealthBar is a method that makes the BufferedImage for the health bar and fills it in with a 1 pixel black border.
    //then it fills the rest with green based on how much health is left with red as the background, in theory. In reality
    //its just the pixels.
    // done using the individual pixel function for a BufferedImage, should be done with a range of pixels function
    private void setHealthBar(){
        int width = 20;
        int height = 5;
        double healthRatio = currHealth / (double) totalHealth * width;
        // the plus 2 here is to compensate for the black outline
        healthBar = new BufferedImage(width + 2, height + 2, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                //if the current pixel is on the edge of the BufferedImage, set it black. otherwise, do the ratio of
                //current health to total health
                if(i == 0 || j  == 0 || i == width - 1 || j == height - 1){
                    healthBar.setRGB(i, j, new Color(0, 0, 0).getRGB());

                }else {
                    if (i < healthRatio) {
                        healthBar.setRGB(i, j, new Color(0, 255, 0).getRGB());
                    } else {
                        healthBar.setRGB(i, j, new Color(255, 0, 0).getRGB());
                    }
                }
            }
        }
    }

    /**
     * Getter to return the point that represents this enemy's location.
     * @return the position of the enemy as a Point.
     */
    public Point position() {
        return position;
    }

    /**
     * Getter to return which point on the path this enemy target.
     * @return an integer that represents what step of the path this enemy is on.
     */
    public int currentPathTarget(){
        return currentPathTarget;
    }

    /**
     * Method that is called once the enemy has reached the next point in the path. Essentially changes the direction
     * the enemy is travelling.
     */
    public void goToNextTarget(){
        currentPathTarget++;
    }

    /**
     * Method that tells the model class if the enemy has waited long enough to move again.
     * @return a boolean value with true being able to move and false not being able to move.
     */
    public boolean isAbleToMove(){
        boolean isAble = false;
        if(System.currentTimeMillis() - timeOfLastMove >= timeToMove){
            isAble = true;
        }
        return isAble;
    }

    /**
     * Setter to move the enemy to a new point, using coordinates.
     * @param x - the x coordinate of the enemy's new position.
     * @param y - the y coordinate of the enemy's new position.
     */
    public void setPosition(int x, int y){
        this.position = new Point(x, y);
        timeOfLastMove = System.currentTimeMillis();
    }

    /**
     * Method to change an enemy's health based on how much the tower doing the attacking can do. Also updates health bar
     * @param damageTaken - amount of damage that the enemy's health should be reduced by
     */
    public void takeDamage(int damageTaken){
        if(damageTaken <= 0){
            setHealthBar();
        }
        currHealth -= damageTaken;
        if(currHealth <= 0){
            currHealth = 0;

        }
        setHealthBar();

    }

    /**
     * Getter for the current amount of health the enemy has.
     * @return current amount of health.
     */
    public int health() {
        return currHealth;
    }

    /**
     * Getter for the amount of money killing an enemy is worth.
     * @return the value of the enemy.
     */
    public int value(){
        return value;
    }

    /**
     * Getter for the amount of score killing an enemy is worth.
     * @return the score enemy gives on death.
     */
    public int score(){
        return score;
    }

    public int travelDistance(){
        return travelDistance;
    }
}