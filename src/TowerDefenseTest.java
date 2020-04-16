import adt.TDModel;
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
}
