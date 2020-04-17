import adt.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;
import java.util.Random;

import javax.swing.text.Position;
import java.awt.*;

public class TowerDefenseTest {

    /*
    MODEL TESTING
     */
    @Test
    public void modelInstantiationTest(){
        TDModel model = new TDModel();
        Assert.assertEquals(model.money(), 100);
        Assert.assertEquals(model.lives(), 10);
        Assert.assertEquals(model.score(), 0);

    }

    @Test
    public void basicTest(){
        TDModel model = new TDModel();
        model.spawnEnemy("base");
        Assert.assertFalse(model.getEnemies().isEmpty());
    }
    @Test
    public void modelisEnemyInRangeFalse(){
        TDModel model = new TDModel();

        Assert.assertFalse(model.isEnemyInRange());
    }
    @Test
    public void modelSpawnEnemiesManually(){
        TDModel m = new TDModel();
        Enemy e1 = new Enemy(0, 0);
        Enemy e2 = new Enemy(new Point(0, 1));
        m.getEnemies().add(e1);
        m.getEnemies().add(e2);
        Assert.assertEquals(m.getEnemies().size(), 2);
    }
    @Test
    public void modelSpawnEnemiesAutomatically() {
        TDModel m = new TDModel();
        m.spawnEnemy("base");
        m.spawnEnemy("grunt");
        m.spawnEnemy("bruiser");
        Assert.assertEquals(m.getEnemies().size(), 3);
        for(Enemy e : m.getEnemies()){
            Assert.assertEquals(e.position().x, m.path().get(0).x );
            Assert.assertEquals(e.position().y, m.path().get(0).y );
        }
    }

    @Test
    public void modelAddTowers(){
        TDModel m = new TDModel();
        Tower t = new Tower(new Point(0, 99));
        MagicTower mt = new MagicTower(new Point(149, 0));
        m.placeTower(t);
        m.placeTower(mt);
        Assert.assertEquals(m.getTowers().size(), 2);
    }

    @Test
    public void testTowerRangesNotInRange(){
        TDModel m1 = new TDModel();
        Tower tower = new Tower(new Point(0, 101));
        Grunt g = new Grunt(0, 0);
        m1.placeTower(tower);
        m1.getEnemies().add(g);
        Assert.assertFalse(m1.isEnemyInRange());
    }
    @Test
    public void testTowerRangesInRange() {
        TDModel m = new TDModel();
        Enemy e1 = new Enemy(0, 0);
        Enemy e2 = new Enemy(new Point(0, 1));
        m.getEnemies().add(e1);
        m.getEnemies().add(e2);
        Tower t = new Tower(new Point(0, 99));
        MagicTower mt = new MagicTower(new Point(149, 0));
        m.placeTower(t);
        m.placeTower(mt);
        Assert.assertTrue(m.isEnemyInRange());
    }
    @Test
    public void testRound(){
        TDModel m = new TDModel();
        m.beginRound(0);
        for(int i = 0; i < 500; i++){
            m.round(i);
        }
        Assert.assertEquals(m.getEnemies().size(), 6);
    }
    @Test
    public void testKillingEnemies(){
        TDModel m = new TDModel();
        Enemy e1 = new Enemy(0, 0);
        Grunt g = new Grunt(0, 0);
        Bruiser b = new Bruiser(0, 0);
        m.getEnemies().add(e1);
        m.getEnemies().add(g);
        m.getEnemies().add(b);
        Assert.assertEquals(m.getEnemies().size(), 3);
        int size = m.getEnemies().size();
        for(int i = 0; i < m.getEnemies().size(); i++) {
            size -=1;
            Enemy e = m.getEnemies().get(i);
            int currScore = m.score();
            m.killEnemy(e);
            Assert.assertEquals(m.getEnemies().size(), size);
            Assert.assertNotEquals(currScore, m.score());
        }
    }
    @Test
    public void testLosingLife(){
        TDModel m = new TDModel();
        int before = m.lives();
        m.loseLife();
        Assert.assertEquals(before, m.lives() + 1);
    }
//    @Test
//    public void testUpdate(){
//        TDModel m = new TDModel();
//        m.beginRound(0);
//        for(int i = 0; i < 480; i++){
//            m.update(i);
//
//        }
//        Assert.assertEquals(m.getEnemies().size(), 6);
//        for(int i = 600; i < 10000; i++){
//            m.update(i);
//        }
//        Assert.assertEquals(m.getEnemies().size(), 0);
//    }

    @Test
    public void EnemyConfirmPoint(){
        Random rand = new Random();
        int x = rand.nextInt(400);
        int y = rand.nextInt(400);
        Point p = new Point(x,y);
        Enemy e = new Enemy(new Point(p));
        Assert.assertEquals(e.position(),p);

    }
    @Test
    public void EnemyisAbleToMoveTrue(){
        Enemy e = new Enemy(new Point(0,0));
        e.setTimeOfLastMove(e.getTimeToMove());
        e.setTimeToMove(e.getTimeOfLastMove());
        Assert.assertTrue(e.isAbleToMove());
    }
    @Test
    public void EnemyConstructorPosition(){
        Enemy e = new Enemy(new Point(0,0));
        Assert.assertEquals(e.position(),new Point(0,0));
    }
    @Test
    public void EnemyConstructorPositionNotEquals(){
        Enemy e = new Enemy(new Point(0,0));
        for(int i = 1; i < 150; i++) {
            Assert.assertNotEquals(e.position(), new Point(i, i));
        }
    }
    @Test
    public void EnemyisAbleToMoveFalse(){
        Enemy e = new Enemy(new Point(0,0));
        e.setTimeOfLastMove(e.getTimeOfLastMove());
        e.setTimeToMove(e.getTimeOfLastMove() + e.getTimeToMove());
        Assert.assertFalse(e.isAbleToMove());
    }
    @Test
    public void EnemyGoToNextTarget(){
        Enemy e = new Enemy(new Point(0,0));
        e.goToNextTarget();
        for(int i = 0; i < 150; i++){
            e.setCurrentPathTarget(i);
            Assert.assertEquals(e.currentPathTarget(), i);
        }
    }
    @Test
    public void EnemyGoToNextTargetNotEquals(){
        Enemy e = new Enemy(new Point(0,0));
        e.goToNextTarget();
        for(int i = 0; i < 150; i++){
            e.setCurrentPathTarget(i);
            Assert.assertNotEquals(e.currentPathTarget(), i += 2);
        }
    }
    @Test
    public void EnemyTakeDamage(){
        Enemy e = new Enemy(new Point(0,0));
        Assert.assertEquals(e.takeDamage(e.health()), 0);
    }
    @Test
    public void EnemyTakeDamageDead(){
        Enemy e = new Enemy(new Point(0,0));
        for(int i = 1; i < e.health(); i++){
            Assert.assertEquals(e.takeDamage(i),e.health());
        }
    }
    @Test
    public void EnemyTakeDamageNotEquals(){
        Enemy e = new Enemy(new Point(0,0));
        for(int i = 1; i < e.health(); i++){
            Assert.assertNotEquals(e.takeDamage(e.health()), i);
        }
    }
    @Test
    public void EnemyTakeDamageNoDamage(){
        Enemy e = new Enemy(new Point(0,0));
        Assert.assertEquals(e.takeDamage(0),e.health());
    }
    @Test
    public void EnemyTakeDamageNoDamageNotEquals(){
        Enemy e = new Enemy(new Point(0,0));
        for(int i = e.health(); i > 0; i--) {
            Assert.assertNotEquals(e.takeDamage(0), e.health() - i);
        }
    }
    @Test
    public void towerTestisAbleToShootTrue(){
        Tower tower = new Tower();
        Assert.assertTrue(tower.isAbleToShoot(1000));
    }
    @Test
    public void towerTestisAbleToShootFalse(){
        Tower tower = new Tower();
        Assert.assertFalse(tower.isAbleToShoot(tower.coolDown()));
    }
}
