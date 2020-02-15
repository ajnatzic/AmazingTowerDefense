public class Enemy{
    private int damage;
    private int health;
    private int physArmor;
    private int elementalArmor;

    public Enemy(int damage, int health, int physArmor, int elementalArmor) {
        this.damage = damage;
        this.health = health;
        this.physArmor = physArmor;
        this.elementalArmor = elementalArmor;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPhysArmor() {
        return physArmor;
    }

    public void setPhysArmor(int physArmor) {
        this.physArmor = physArmor;
    }

    public int getElementalArmor() {
        return elementalArmor;
    }

    public void setElementalArmor(int elementalArmor) {
        this.elementalArmor = elementalArmor;
    }
}