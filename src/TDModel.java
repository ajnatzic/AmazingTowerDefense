import java.util.ArrayList;


public class TDModel {
    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    public TDModel(){
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void setTowers(ArrayList<Tower> towers) {
        this.towers = towers;
    }

    public void placeTower(Tower t){
        towers.add(t);
        System.out.println("Should be here: " + t.getPosition().getX() + ", " + t.getPosition().getY());
    }
}
