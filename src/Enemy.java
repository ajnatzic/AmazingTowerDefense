import java.awt.*;

/**
 * Enemy class to represent the basic enemies in the game. Attack, cost, health, physArmor, and elementalArmor are all
 * integers that represent exactly what they say. Position is a point, hopefully directly in the middle of the object,
 * that tells the game where the enemy is.
 *
 */
public class Enemy{

    private int attack;
    private int cost;
    private int health;
    private int physArmor;
    private int elementalArmor;
    private Point position;

    public Enemy(Point position) {
        this.position = position;
    }

    public Enemy(int attack, int health, int physArmor, int elementalArmor, int cost) {
        this.attack = attack;
        this.health = health;
        this.physArmor = physArmor;
        this.elementalArmor = elementalArmor;
        this.cost = cost;
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
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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