package amazingtowerdefense;

import java.awt.*;
import java.util.List;
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
    private final int DEFAULT_COST = 10;
    private final int DEFAULT_DAMAGE = 2;
    private int coolDown;
    private long lastTimeShot;
    private final int cooldown = 10;

    /**
     * Default constructor that initializes each variable to its default value defined by final integers.
     */
    public Tower() {
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
        this.coolDown = cooldown;
    }

    /**
     * Constructor that defaults all values except the position, which is passed as a point.
     * @param position - the location of the tower as a Point object.
     */
    public Tower(Point position) {
        this.position = position;
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
    }

    public boolean isAbleToShoot(long currTime){
        boolean isAble = false;
        if(currTime - lastTimeShot > coolDown){
            isAble = true;
        }
        return isAble;
    }

    public void targetEnemy(List<Enemy> list){
        if(list.isEmpty()){
            return;
        }
        double maxDist = -1;
        Enemy closest = list.get(0);
        for(Enemy enemy : list){
            if(enemy.distanceTraveled > maxDist){
                closest = enemy;
                maxDist = enemy.distanceTraveled;
            }
        }
        closest.takeDamage(damage);
        lastTimeShot = System.currentTimeMillis();
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
    public Point getPosition() {
        return position;
    }
}