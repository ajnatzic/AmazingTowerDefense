import java.awt.*;

public class Tower{
    private int cost;
    private int range;
    private int damage;
    private Point position;
    private int  DEFAULT_RANGE = 10, DEFAULT_COST = 10, DEFAULT_DAMAGE = 10;
    private int coolDown;
    private long lastTimeShot;
    private final int COOLDOWN = 10;

    public Tower() {
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
        this.coolDown = COOLDOWN;
    }

    public Tower(int cost, int range, int damage, Point position) {
        this.cost = cost;
        this.range = range;
        this.damage = damage;
        this.position = position;
        this.coolDown = COOLDOWN;
    }

    public Tower(Point position) {
        this.position = position;
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
    }

    public int range() {
        return range;
    }

    public int coolDown(){
        return coolDown;
    }

    public void setLastShot(long time){
        lastTimeShot = time;
    }

    public int getDamage() {
        return damage;
    }

    public int cost(){
        return cost;
    }

    public Point getPosition() {
        return position;
    }
}