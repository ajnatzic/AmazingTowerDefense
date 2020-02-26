import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Enemy class to represent the basic enemies in the game. Attack, cost, health, physArmor, and elementalArmor are all
 * integers that represent exactly what they say. Position is a point, hopefully directly in the middle of the object,
 * that tells the game where the enemy is.
 *
 */
public class Enemy{
    /**
     * javadoc style comments for variables go here
     */
    private int currHealth;
    private int totalHealth;
    private Point position;
    private BufferedImage healthBar;
    private int currentPathTarget;
    private int value;

    private final int defaultHealth = 20;
    //constructor for enemy with only point
    public Enemy(Point position) {
        this.position = position;
        currHealth = defaultHealth;
        totalHealth = defaultHealth;
    }
    //identical to point constructor, just passes x and y to allow changing the point
    public Enemy(int x, int y) {
        this.position =  new Point(x, y);
        currHealth = defaultHealth;
        totalHealth = defaultHealth;
        currentPathTarget = 1; // spawns at 0, wants to go to one
    }

    //method to return the health bar image
    public BufferedImage healthBar(){
        return healthBar;
    }
    //setHealthBar is a method that makes the BufferedImage for the health bar and fills it in with a 1 pixel black border.
    //then it fills the rest with green based on how much health is left with red as the background, in theory. In reality
    //its just the pixels.
// done using the individual pixel function for a BufferedImage, should be done with a range of pixels function
    private void setHealthBar(){
        int width = 20, height = 5;
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

    public Point position() {
        return position;
    }

    public int currentPathTarget(){
        return currentPathTarget;
    }
    public void goToNextTarget(){
        currentPathTarget++;
    }
    public void setPosition(int x, int y){
        this.position = new Point(x, y);
    }

    //simple function to reduce health by damage taken, make sure health isn't negative, and update the health bar

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

    public int health() {
        return currHealth;
    }
    public int value(){
        return value;
    }
}