package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
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
    private int  DEFAULT_RANGE = 100;
    private int DEFAULT_COST = 10;
    private int  DEFAULT_DAMAGE = 2;
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
     * Checks to see if enough time has passed so that the tower can shoot agian
     *
     * This method checks to see if the time since the tower last shot is greater than the cooldown specified by the
     * tower.
     * @param currTime - the current time given by System.currentTimeMillis()
     * @return a boolean value that says if the tower is able to shoot.
     */
    private boolean isAbleToShoot(long currTime){
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
     * Getter for the current position of the tower, used for drawing the tower.
     * @return the position of this tower as a Point object.
     */
    public Point position() {
        return position;
    }

    public BufferedImage graphic(){
        return graphic;
    }
}