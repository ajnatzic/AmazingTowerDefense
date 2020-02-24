import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Enemy class to represent the basic enemies in the game. Attack, cost, health, physArmor, and elementalArmor are all
 * integers that represent exactly what they say. Position is a point, hopefully directly in the middle of the object,
 * that tells the game where the enemy is.
 *
 */
public class Enemy{

    private int attack;
    private int cost;
    private int currHealth;
    private int totalHealth;
    private int physArmor;
    private int elementalArmor;
    private Point position;
    private BufferedImage healthBar;

    public Enemy(Point position) {
        this.position = position;

    }

    public Enemy(int attack, int health, int physArmor, int elementalArmor, int cost) {
        this.attack = attack;
        this.currHealth = health;
        this.totalHealth = health;
        this.physArmor = physArmor;
        this.elementalArmor = elementalArmor;
        this.cost = cost;
    }
    public BufferedImage healthBar(){
        return healthBar;
    }
// done using the individual pixel function for a BufferedImage, should be done with a range of pixels function
    private void initializeHealthBar(){
        int width = 20, height = 5;
        double healthRatio = currHealth / (double) totalHealth * width;
        healthBar = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(i < healthRatio){
                    healthBar.setRGB(i, j, 0);
                }
            }
        }
    }

    public Point position() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    public void setPosition(int x, int y){
        this.position = new Point(x, y);
    }

    public int cost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int attack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int health() {
        return currHealth;
    }

    public void setHealth(int health) {
        this.currHealth = health;
    }

    public int phsyArmor() {
        return physArmor;
    }

    public void setPhysArmor(int physArmor) {
        this.physArmor = physArmor;
    }

    public int elementalArmor() {
        return elementalArmor;
    }

    public void setElementalArmor(int elementalArmor) {
        this.elementalArmor = elementalArmor;
    }
}