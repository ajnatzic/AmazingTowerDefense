import adt.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.awt.*;
import java.util.ArrayList;

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
    public void modelkillEnemy(){
        TDModel m = new TDModel();
        Enemy e = new Enemy(0,0);
        Bruiser b = new Bruiser(0,0);
        Grunt g = new Grunt(0,0);
        m.killEnemy(e);
        m.killEnemy(b);
        m.killEnemy(g);
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
    @Test
    public void testUpdate(){
        TDModel m = new TDModel();
        m.beginRound(0);
        for(int i = 0; i < 480; i++){
            m.update(i);
        }
        Assert.assertEquals(m.getEnemies().size(), 6);
        for(int i = 600; i < 10000; i++){
            m.update(i);
        }
        Assert.assertEquals(m.getEnemies().size(), 0 /*passes with 6*/);
    }

    @Test
    public void TowerisAbleToShootTrue(){
        long time = 0;
        for(int i = 0; i < 10000; i++){
            time += i;
        }
        Tower t = new Tower(new Point(0,0));
        Assert.assertTrue(t.isAbleToShoot(time));
    }

    @Test
    public void towerTestisAbleToShootFalse(){
        Tower tower = new Tower();
        Assert.assertFalse(tower.isAbleToShoot(tower.coolDown()));
    }
    @Test
    public void TowerTargetEnemy(){
        Tower t = new Tower(new Point(0,0));
        ArrayList<Enemy> list = new ArrayList<>();
        for(int i = 0; i < 300; i++){
            list.add(new Enemy(i,i));
        }
        Assert.assertEquals(t.targetEnemy(list), list.get(0));
    }
    @Test
    public void TowerTargetEnemyNotEquals(){
        Tower t = new Tower(new Point(0,0));
        ArrayList<Enemy> list = new ArrayList<>();
        for(int i = 0; i < 300; i++){
            list.add(new Enemy(i,i));
        }
        for(int i = 1; i < 300; i++) {
            Assert.assertNotEquals(t.targetEnemy(list), list.get(i));
        }
    }
}
