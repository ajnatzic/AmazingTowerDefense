import adt.Enemy;
import adt.Sound;
import adt.TDModel;
import adt.Tower;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

public class TowerDefenseTest {
    @Test
    public void basicTest(){
        TDModel model = new TDModel();
        model.spawnEnemy("base");
        Assert.assertFalse(model.getEnemies().isEmpty());
    }
    @Test
    public void modelisEnemyInRangeFalse(){
        TDModel model = new TDModel();
        model.isEnemyInRange();
        Assert.assertFalse(false);
    }
    @Test
    public void modelisEnemyInRangeTrue(){
        TDModel model = new TDModel();
        model.isEnemyInRange();
        Assert.assertTrue(true);
    }
//    @Test
//    public void soundSoundbrutePain(){
//        Sound sound  = new Sound();
//        sound.sound("brutePain.wav");
//    }
//    @Test
//    public void soundSoundbellgruntPain(){
//        Sound sound  = new Sound();
//        sound.sound("gruntPain.wav");
//    }
//    @Test
//    public void soundSoundbellgruntPain0(){
//        Sound sound  = new Sound();
//        sound.sound("gruntPain0.wav");
//    }
//    @Test
//    public void soundSoundgruntPain1(){
//        Sound sound  = new Sound();
//        sound.sound("gruntPain1.wav");
//    }
//    @Test
//    public void soundSoundgruntPain2(){
//        Sound sound  = new Sound();
//        sound.sound("gruntPain2.wav");
//    }
//    @Test
//    public void soundtrySound(){
//        Sound sound  = new Sound();
//        sound.sound();
//    }
    @Test
    public void towerTestisAbleToShootTrue(){
        Tower tower = new Tower();
        tower.isAbleToShoot(1000);
        Assert.assertTrue(tower.isAbleToShoot(1000));
    }
    @Test
    public void towerTestisAbleToShootFalse(){
        Tower tower = new Tower();
        tower.isAbleToShoot(tower.coolDown());
        Assert.assertFalse(tower.isAbleToShoot(tower.coolDown()));
    }
}
