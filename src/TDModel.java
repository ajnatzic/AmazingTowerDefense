import java.util.List;

public class TDModel {
    private List<Enemy> enemies;
    private List<Tower> towers;
    public TDModel(){

    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public void placeTower(Tower t){
        towers.add(t);

    }
}
