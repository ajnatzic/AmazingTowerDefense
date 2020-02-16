import java.awt.*;
import java.util.ArrayList;


public class TDModel {
    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    private ArrayList<Point> path;

    public TDModel(){
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        initializePath(0);
    }
    private void initializePath(int whichPath){
        path = new ArrayList<>();
        switch (whichPath) {
            case 0:
                path.add(new Point(0, 195));
                path.add(new Point(456, 195));
                path.add(new Point(456, 516));
                path.add(new Point(799, 516));
                break;
            case 1:

                break;
        }
    }

    public ArrayList<Point> path() {
        return path;
    }

    public void setPath(ArrayList<Point> path) {
        this.path = path;
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
    public void spawnEnemy(){
        enemies.add(new Enemy(new Point(path.get(0))));
    }
    public void startRound(){
        spawnEnemy();


    }
}
