package adt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
* adt.TDModel, or adt.Tower Defense Model, is the brains of the game. With a list of the enemies, towers, and a list representing
* the path, this class is made to communicate with the graphics of the game to make it playable.
*/
public class TDModel {
  private int roundNum;
  private long roundStartFrame;
  private List<Enemy> enemies;
  private List<Tower> towers;
  /*
  The path array list is a list containing the points on a line that travels down the middle of the graphical
  path, with the points in the list being where it changes direction. In the initializePath method, there is a
  variable that should eventually be an enum that determines what the path is based on the current level. With only one
  level, this is always 0, and the switch statement only has one case with things in it.
  */
  private List<Point> path;
  private int money;
  private int lives;
  private int score;
  private final int mapX = 800;
  private final int mapY = 700;

  /**
  * Constructor for the adt.Tower Defense model. Includes instantiating array lists for enemies and towers.
  * As well as initializing the path and money/lives values.
  */
  public TDModel(){
    enemies = new ArrayList<>();
    towers = new ArrayList<>();
    initializePath(0);
    money = 100;
    lives = 10;
    score = 0;
    roundNum = 0;
  }
  /**
  * This method places dots on the grid and connects them to create a path. Points
  * that are created are specified in this method using x and y coordinates.
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
        break ;
      case 1:

        break;
      default:
        path.add(new Point(0, 0));
        path.add(new Point(mapX - 1, mapY - 1));
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
  * See equation: (adt.Tower x position - adt.Enemy x position) + (adt.Tower y position - adt.Enemy y position)
  * @param tower The tower we are checking
  * @param enemy The enemy we are checking
  * @return Returns the distance between the tower and enemy specified in the parameters
  */
  private double distanceBetween(Tower tower, Enemy enemy){
    return Math.pow(Math.pow(tower.position().x - enemy.position().x, 2) + Math.pow(tower.position().y - enemy.position().y, 2), 0.5);

  }

  private double distanceBetween(Point point1, Point point2){
    return Math.pow(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2), 0.5);
  }

  /**
  * Method to remove an enemy, will be called when refreshing the game per frame when an enemy has health 0
  * @param enemyToKill Which enemy we want to kill
  */
  public void killEnemy(Enemy enemyToKill){
    enemies.remove(enemyToKill);
    money += enemyToKill.value();
    score += enemyToKill.score();
  }

  /**
  * An Array list of points that specify a created path
  * @return The array list of points
  */
  public List<Point> path() {
    return path;
  }

  /**
  * An Array list of enemies created
  * @return The array list of enemies
  */
  public List<Enemy> getEnemies() {
    return enemies;
  }

  /**
  * An Array list of towers created
  * @return The array list of towers
  */
  public List<Tower> getTowers() {
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
  * @param towerToPlace adt.Tower that we want to place
  * @return True if successful tower placement, false if not
  */
  public boolean placeTower(Tower towerToPlace){
    boolean success = false;
    if(money > towerToPlace.cost()) {
      towers.add(towerToPlace);
      money -= towerToPlace.cost();
      success = true;
    }
    return success;
  }
  /**
  * spawnEnemy adds a new enemy to the start of the path, with the
  * first point of the path array being the beginning.
  */
  public void spawnEnemy(String type){
      if(type.equals("base")){
          enemies.add(new Enemy(new Point(path.get(0))));
      }else if(type.equals("grunt")){
          enemies.add(new Grunt(new Point(path.get(0))));
      }
      else if(type.equals("bruiser")){
          enemies.add(new Bruiser(new Point(path.get(0))));
      }

  }
  /**
  * startRound is currently not used but will eventually be the method called when the start round button is pushed.
   * @param frame
   */
    public void round(long frame){

        long framesSinceStart = frame - roundStartFrame;
        switch(roundNum){
            case 1:
                for(int i = 0; i < 5; i++){
                    if(framesSinceStart == 60 * i) {
                        spawnEnemy("grunt");
                    }
                    if(framesSinceStart == 450){
                        spawnEnemy("bruiser");
                    }
                }
        }
    }

    /**
     * Method that updates enemy positions and towers' targets with time angle calculation for the enemies and
     * a sublist of enemies passed to the towers
     *
     * @param frame - the current frame of the game
     */
  public void update(long frame){
      int deltaX;
      int deltaY;
      double angle;
      round(frame);
      for(int i = 0; i < getEnemies().size(); i++){
        Enemy currentEnemy = getEnemies().get(i);
        double distanceToTravel = currentEnemy.travelDistance();
        double actualDistance = distanceToTravel;
        if(currentEnemy.isAbleToMove()) {
          boolean isEnd = false;
          Point target = path().get(currentEnemy.currentPathTarget());
          double distToNextPoint = distanceBetween(target, currentEnemy.position());

          if (currentEnemy.currentPathTarget() < path().size() - 1 && distToNextPoint <= distanceToTravel) {
            actualDistance = distanceToTravel - distToNextPoint;
            currentEnemy.setPosition(path().get(currentEnemy.currentPathTarget()).x, path().get(currentEnemy.currentPathTarget()).y);
            currentEnemy.goToNextTarget();
            target = path().get(currentEnemy.currentPathTarget());
          }
          currentEnemy.distanceTraveled += distanceToTravel;
          if(target == path.get(path.size() - 1 ) && Math.abs(currentEnemy.position().x - target.x) < 0.01){
            loseLife();
            killEnemy(currentEnemy);
            isEnd = true;
            angle = 0; // needed to appease compiler
          }else if (Math.abs(currentEnemy.position().x - target.x) < 0.01) {
            angle = -Math.atan(((double) (currentEnemy.position().y - target.y) / (currentEnemy.position().x - target.x)));
          }
          else {
            angle = Math.atan(((double) (currentEnemy.position().y - target.y) / (currentEnemy.position().x - target.x)));
          }
          if (!isEnd) {
            deltaX = (int) (actualDistance * Math.cos(angle));
            deltaY = (int) (actualDistance * Math.sin(angle));
            int newX = currentEnemy.position().x + deltaX ;
            int newY = currentEnemy.position().y + deltaY;
            if (newX < mapX && newY < mapY) {
              currentEnemy.setPosition(newX, newY);
            } else {
              loseLife();
              killEnemy(currentEnemy);
            }
          }
        }

        }
      for(Tower tower : towers){
          ArrayList<Enemy> sublist = new ArrayList<>();
          for (Enemy enemy : enemies) {
            if (distanceBetween(tower, enemy) < tower.range()) {
              sublist.add(enemy);
            }
          }
          if(!sublist.isEmpty())
            tower.targetEnemy(sublist);


      }
      for(int i = 0; i < enemies.size(); i++){
        Enemy enemy = enemies.get(i);
        if(enemy.health() == 0){
          killEnemy(enemy);
        }
      }
  }

  public void beginRound(long frame){
      roundStartFrame = frame;
      roundNum++;
  }
}
