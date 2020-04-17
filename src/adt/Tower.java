package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Generic tower class to represent the player's main method of attacking the enemies.
 *
 * Each tower costs some amount of currency, has a range, an amount of damage, and a position.
 */
public class Tower{
    private int cost;
    private int range;
    private int damage;
    private Point position;
    private final int  DEFAULT_RANGE = 100;
    private final int DEFAULT_COST = 25;
    private final int  DEFAULT_DAMAGE = 3;
    private int coolDown;
    private long lastTimeShot;
    private final int cooldown = 200;
    private BufferedImage graphic;

    /**
     * Default constructor that initializes each variable to its default value defined by final integers.
     */
    public Tower() {
        initialize();
    }

    /**
     * Constructor that defaults all values except the position, which is passed as a point.
     * @param position - the location of the tower as a Point object.
     */
    public Tower(Point position) {
        this.position = position;
        initialize();
    }

    /**
     * Constructor for specializing towers
     * @param position - position of tower
     * @param range - range of tower
     * @param cost - cost of tower
     * @param damage - total damage of tower
     * @param cooldown - cooldown between shots of tower
     */
    public Tower(Point position,int range, int cost, int damage,int cooldown) {
        this.position = position;
        this.range = range;
        this.cost = cost;
        this.damage = damage;
        this.coolDown = cooldown;
    }

    private void initialize(){
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
        this.coolDown = cooldown;
        try {
            graphic = ImageIO.read(new File(getClass().getResource("resources/tower.png").toURI()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if enough time has passed so that the tower can shoot again
     *
     * This method checks to see if the time since the tower last shot is greater than the cooldown specified by the
     * tower.
     * @param currTime - the current time given by System.currentTimeMillis()
     * @return a boolean value that says if the tower is able to shoot.
     */
    public boolean isAbleToShoot(long currTime){
        boolean isAble = false;
        if(currTime - lastTimeShot > coolDown){
            isAble = true;
        }
        return isAble;
    }

    /**
     * Determines which single enemy the tower will target based on how far the enemy has traveled, returns if
     * @param list - a sublist of the total enemy list that is in range of the tower
     * @return the enemy targeted by the tower
     */
    public Enemy targetEnemy(ArrayList<Enemy> list){
        Enemy closest = list.get(0);
        if(isAbleToShoot(System.currentTimeMillis())) {
            double maxDist = -1;

            for (Enemy enemy : list) {
                if (enemy.distanceTraveled > maxDist) {
                    closest = enemy;
                    maxDist = enemy.distanceTraveled;
                }
            }
            closest.takeDamage(damage);
            lastTimeShot = System.currentTimeMillis();
        }
        return closest;
    }
    /**
     * Getter for the range of the tower.
     * @return the range of this tower as an integer
     */
    public int range() {
        return range;
    }

    /**
     * Getter for the amount of damage the tower is able to do in one shot.
     * @return the amount of damage possible.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter for the cost of the tower.
     * @return the cost of a tower as an integer.
     */
    public int cost(){
        return cost;
    }
    /**
     * Getter for the cool down.
     * @return the cool down as an integer.
     */
    public int coolDown() {return coolDown; }
    /**
     * Getter for the current position of the tower, used for drawing the tower.
     * @return the position of this tower as a Point object.
     */
    public Point position() {
        return position;
    }

    /**
     * Getter for the image of the tower
     * @return the tower's image
     */
    public BufferedImage graphic(){
        return graphic;
    }
}