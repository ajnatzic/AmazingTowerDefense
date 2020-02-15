public class Tower{
    private int cost;
    private int range;
    private int damage;
    private int[][] position;

    public Tower() {
    }

    public Tower(int range, int damage, int[][] position) {
        this.range = range;
        this.damage = damage;
        this.position = position;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }
}