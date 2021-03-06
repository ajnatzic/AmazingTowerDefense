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
    public int distanceTraveled;
    private long frameOfLastMove;
    private long framesToMove;
    private BufferedImage graphic;

    private final int defaultHealth = 20;
    private final int defaultValue = 10;
    private final int defaultScore = 5;

    /**
     * Constructor for an enemy class that puts the enemy at the position indicated by the point passed.
     * @param position is where the enemy will be placed, both graphically and logically.
     */
    public Enemy(Point position, long frame) {
        this.position = position;
        initialize(frame);
    }

    /**
     * Constructor made to allow the enemy to be initialized using coordinates instead of a Point object.
     * @param x is the position relative to the x axis.
     * @param y is the position relative to the y axis.
     * @param frame - the frame the enenmy is constructed on
     */
    public Enemy(int x, int y, long frame) {
        this.position =  new Point(x, y);
        initialize(frame);
    }

    /**
     * Specialized constructor for classes that extend Enemy
     * @param x - x coordinate of the new enemy
     * @param y - y coordinate of the new enemy
     * @param health - the total health of the enemy
     * @param unitValue - the amount of money the enemy is worth
     * @param unitScore - the score of the enemy
     * @param speed - how fast the enemy moves
     * @param frame - the frame the enenmy is constructed on
     */
    public Enemy(int x, int y, int health, int unitValue, int unitScore, int speed, long frame){
        position = new Point(x, y);
        currHealth = health;
        totalHealth = health;
        this.value = unitValue;
        this.score = unitScore;
        travelDistance = speed;
        initializeGeneral(frame);
    }
    private void initializeGeneral(long frame){
        currentPathTarget = 1;
        setHealthBar();
        distanceTraveled = 0;
        framesToMove = 3;
        frameOfLastMove = frame;
    }
    private void initialize(long frame){
        travelDistance = 5;
        currHealth = defaultHealth;
        totalHealth = defaultHealth;
        currentPathTarget = 1; // spawns at 0, wants to go to one
        setHealthBar();
        value = defaultValue;
        score = defaultScore;
        distanceTraveled = 0;
        framesToMove = 3;
        frameOfLastMove = frame;
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
    /**
     * Getter for the image of the enemy, overridden by specific implementations
     * @return a BufferedImage representing the  enemy.
     */
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
     * the enemy is traveliing.
     */
    public void goToNextTarget(){
        currentPathTarget++;
    }

    /**
     * Method that tells the model class if the enemy has waited long enough to move again.
     * @return a boolean value with true being able to move and false not being able to move.
     */
    public boolean isAbleToMove(long currentFrame){
        boolean isAble = false;
        if(currentFrame - frameOfLastMove >= framesToMove){
            isAble = true;
        }
        return isAble;
    }

    /**
     * Setter to move the enemy to a new point, using coordinates.
     * @param x - the x coordinate of the enemy's new position.
     * @param y - the y coordinate of the enemy's new position.
     * @param currentFrame - the current frame that the enemy is moving on
     */
    public void setPosition(int x, int y, long currentFrame){
        this.position = new Point(x, y);
        frameOfLastMove = currentFrame;
    }

    /**
     * Method to change an enemy's health based on how much the tower doing the attacking can do. Also updates health bar
     * @param damageTaken - amount of damage that the enemy's health should be reduced by
     * @return the current amount of health after the damage has been taken.
     */
    public int takeDamage(int damageTaken){
        if(damageTaken <= 0){
            setHealthBar();
            return currHealth;
        }
        currHealth -= damageTaken;
        if(currHealth <= 0){
            currHealth = 0;

        }
        setHealthBar();
        return currHealth;
    }

    /**
     * Getter for the current amount of health the enemy has.
     * @return current amount of health.
     */
    public int health() {
        return currHealth;
    }
    /**
     * Setter to set the time of last move in Millis.
     * @param frameOfLastMove a long for timeOfLastMove in Millis.
     */
    public void setFrameOfLastMove(long frameOfLastMove) { this.frameOfLastMove = frameOfLastMove; }
    /**
     * Getter for the time of last move in Millis.
     * @return a long for time of last move in Millis.
     */
    public long getFrameOfLastMove() { return frameOfLastMove; }
    /**
     * Setter for the time to move in Millis.
     * @param framesToMove a long for the time to move in Millis.
     */
    public void setTimeToMove(long framesToMove) { this.framesToMove = framesToMove; }
    /**
     * Getter for the time to move in Millis.
     * @return timeToMove a long for the time to move in Millis.
     */
    public long getFramesToMove() { return framesToMove; }
    /**
     * Getter for the current target along the Enemies path.
     * @param pathTarget an int for the target along the Enemies path.
     */
    public void setCurrentPathTarget(int pathTarget) { this.currentPathTarget = pathTarget; }

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

    /**
     * Getter for how far the enemy can travel, used exclusively by the model class
     * @return the distance the enemy can travel
     */
    public int travelDistance(){
        return travelDistance;
    }
}