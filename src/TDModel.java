
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
  private int money;
  private int lives;
  private int score;
  private final int mapX = 800, mapY = 700;

  /**
  * Constructor for the Tower Defense model. Includes instantiating array lists for enemies and towers.
  * As well as intializing the path and money/lives values.
  */
  public TDModel(){
    enemies = new ArrayList<>();
    towers = new ArrayList<>();
    initializePath(0);
    money = 100;
    lives = 10;
    score = 0;
  }
  /**
  * This method places dots on the grid and connects them to create a path. Points
  * that are created are specified in this method using x and y cooridinates.
  * @param whichPath Specifies which path to change (if there are multiple paths)
  */
  public void initializePath(int whichPath){
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

  /**
  * Boolean variable that keeps track of whether an enemy is in range of a tower placed
  * by the player.
  * @return true if one of the enemies is in range of a tower, or false if it is not
  */


  public boolean isEnemyInRange(){
    boolean isInRange = false;
    for(Tower t : towers){
      for(Enemy e : enemies){
        if(distanceBetween(t, e) < t.range()){
          isInRange = true;
        }
      }
    }
    return isInRange;
  }

  /**
  * Calculates the distance between a tower and enemy by finding the difference between their x values and y values and summing them.
  * See equation: (Tower x position - Enemy x position) + (Tower y position - Enemy y position)
  * @param t The tower we are checking
  * @param e The enemy we are checking
  * @return Returns the distance between the tower and enemy specified in the parameters
  */
  private double distanceBetween(Tower t, Enemy e){
    return Math.pow(Math.pow(t.getPosition().x - e.position().x, 2) + Math.pow(t.getPosition().y - e.position().y, 2), 0.5);

  }

  private double distanceBetween(Point p1, Point p2){
    return Math.pow(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2), 0.5);
  }

  /**
  * Method to remove an enemy, will be called when refreshing the game per frame when an enemy has health 0
  * @param e Which enemy we want to kill
  */
  public void killEnemy(Enemy e){
    enemies.remove(e);
    money += e.value();
    score += e.score();
  }

  /**
  * An Array list of points that specify a created path
  * @return The array list of points
  */
  public ArrayList<Point> path() {
    return path;
  }

  /**
  * An Array list of enemies created
  * @return The array list of enemies
  */
  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }

  /**
  * An Array list of towers created
  * @return The array list of towers
  */
  public ArrayList<Tower> getTowers() {
    return towers;
  }

  /**
  * Public integer that keeps track of money the player has earned
  * @return Amount of money the user has earned
  */
  public int money(){
    return money;
  }

  /**
   * Public integer that keeps track of score the player has earned
   * @return Total score the user has reached
   */
  public int score(){
    return score;
  }

  /**
  * Public integer that keeps track of how many lives the user has
  * @return Number of lives
  */
  public int lives(){
    return lives;
  }

  /**
  * Removes a life from the player when an enemy crosses the end of a path
  */
  public void loseLife(){
    lives -= 1;
  }

  /**
  * placeTower adds a tower to the model's list of towers. This is used to tell
  * the graphics portion where to place the new tower.
  * @param t Tower that we want to place
  * @return True if successful tower placement, false if not
  */
  public boolean placeTower(Tower t){
    boolean success = false;
    if(money > t.cost()) {
      towers.add(t);
      money -= t.cost();
      success = true;
    }
    return success;
  }
  /**
  * spawnEnemy adds a new enemy to the start of the path, with the
  * first point of the path array being the beginning.
  */
  public void spawnEnemy(){
    enemies.add(new Enemy(new Point(path.get(0))));
  }
  /**
  * startRound is currently not used but will eventually be the method called when the start round button is pushed.
  * @param roundNum Used to specify which round to start on
  */
  public void startRound(int roundNum){
    switch(roundNum){

    }
    spawnEnemy();

  }

    /**
     * Method that updates enemy positions and towers' targets with time angle calculation for the enemies and
     * a sublist of enemies passed to the towers
     *
     * @param time - the current time of the system
     */
  public void update(long time){
      int deltaX, deltaY;
      double distanceToTravel = 5;
      double actualDistance = distanceToTravel;
      double angle;
      for(int i = 0; i < getEnemies().size(); i++){
        Enemy e = getEnemies().get(i);
        if(e.isAbleToMove()) {
          boolean isEnd = false;
          Point target = path().get(e.currentPathTarget());
          double distToNextPoint = distanceBetween(target, e.position());

          if (e.currentPathTarget() < path().size() - 1 && distToNextPoint <= distanceToTravel) {
            actualDistance = distanceToTravel - distToNextPoint;
            e.setPosition(path().get(e.currentPathTarget()).x, path().get(e.currentPathTarget()).y);
            e.goToNextTarget();
            target = path().get(e.currentPathTarget());
          }
          e.distanceTraveled += distanceToTravel;
          if(target == path.get(path.size() - 1 ) && Math.abs(e.position().x - target.x) < 0.01){
            loseLife();
            killEnemy(e);
            isEnd = true;
            angle = 0; // needed to appease compiler
          }else if (Math.abs(e.position().x - target.x) < 0.01) {
            angle = -Math.atan(((double) (e.position().y - target.y) / (e.position().x - target.x)));
          }
          else {
            angle = Math.atan(((double) (e.position().y - target.y) / (e.position().x - target.x)));
          }
          if (!isEnd) {
            deltaX = (int) (actualDistance * Math.cos(angle));
            deltaY = (int) (actualDistance * Math.sin(angle));
            int newX = e.position().x + deltaX, newY = e.position().y + deltaY;
            if (newX < mapX && newY < mapY) {
              e.setPosition(newX, newY);
            } else {
              loseLife();
              killEnemy(e);
            }
          }
        }

        }
      for(Tower t : towers){
          ArrayList<Enemy> sublist = new ArrayList<>();
          for (Enemy e : enemies) {
            if (distanceBetween(t, e) < t.range()) {
              sublist.add(e);
            }
          }
          t.targetEnemy(sublist);


      }
      for(int i = 0; i < enemies.size(); i++){
        Enemy e = enemies.get(i);
        if(e.health() == 0){
          killEnemy(e);
        }
      }
  }
}
