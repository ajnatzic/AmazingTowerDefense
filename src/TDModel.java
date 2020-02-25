import java.awt.*;
import java.util.ArrayList;

/**
 * TDModel, or Tower Defense Model, is the brains of the game. With a list of the enemies, towers, and a list representing
 * the path, this class is made to communicate with the graphics of the game to make it playable.
 */
public class TDModel {
    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    /*
    The path array list is a list containing the points on a line that travels down the middle of the graphical
    path, with the points in the list being where it changes direction. In the initializePath method, there is a
    variable that should eventually be an enum that determines what the path is based on the current level. With only one
    level, this is always 0, and the switch statement only has one case with things in it.
     */
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
    //method to remove an enemy, will be called when refreshing the game per frame when an enemy has health 0
    public void killEnemy(Enemy e){
        enemies.remove(e);
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
    /*
    placeTower adds a tower to the model's list of towers. This is used to tell the graphics portion where to place the
    new tower.
     */
    public void placeTower(Tower t){
        towers.add(t);
        System.out.println("Should be here: " + t.getPosition().getX() + ", " + t.getPosition().getY());
    }
    /*
    spawnEnemy adds a new enemy to the start of the path, with the first point of the path array being the beginning.
     */
    public void spawnEnemy(){
        enemies.add(new Enemy(new Point(path.get(0))));
    }
    /*
    startRound is currently not used but will eventually be the method called when the start round button is pushed.
     */
    public void startRound(){
        spawnEnemy();


    }
}
