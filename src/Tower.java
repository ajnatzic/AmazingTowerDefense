import java.awt.*;

public class Tower{
    private int cost;
    private int range;
    private int damage;
    private Point position;
    private int  DEFAULT_RANGE = 10, DEFAULT_COST = 10, DEFAULT_DAMAGE = 10;
    private Point DEFAULT_POS = new Point(10, 10);

    public Tower() {
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
    }

    public Tower(int cost, int range, int damage, Point position) {
        this.cost = cost;
        this.range = range;
        this.damage = damage;
        this.position = position;
    }

    public Tower(Point position) {
        this.position = position;
        this.range = DEFAULT_RANGE;
        this.cost = DEFAULT_COST;
        this.damage = DEFAULT_DAMAGE;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}