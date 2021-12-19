package creature;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import creature.monsters.Bat;
import creature.monsters.Frog;
import creature.monsters.Snake;
import gamedata.Data;
import screen.WorldScreen;
import util.Floor;
import util.World;

public class MonsterTest {
    Monster m1,m2,m3;
    WorldScreen worldScreen;

    @Before
    public void setUP() throws IOException{

        Data data = new Data();
        int[][] raws = new int[World.HEIGHT][World.WIDTH];
        for (int i = 0; i < World.HEIGHT; i++) {
            for (int j = 0; j < World.WIDTH; j++)
                if (i == 0 || j == 0 || i == World.HEIGHT - 1 || j == World.WIDTH - 1) // Wall
                    raws[i][j] = 0;
                else // Floor
                    raws[i][j] = 1;
        }
        data.set(0, 1, 200, 200, 50, raws);
        worldScreen = new WorldScreen(data);
        //worldScreen.gameThread.shutdown();
        
        m1=new Frog(worldScreen.getWorld(),worldScreen,1234);
        m2=new Bat(worldScreen.getWorld(), worldScreen, 1234);
        m3=new Snake(worldScreen.getWorld(), worldScreen, 1234);
        worldScreen.getWorld().put(m1,10,10);
        worldScreen.getMonsters().add(m1);
        worldScreen.getWorld().put(m2,15,23);
        worldScreen.getMonsters().add(m1);
        worldScreen.getWorld().put(m3,34,15);
        worldScreen.getMonsters().add(m1);
    }

    @Test
    public void testMove(){
        m1.goUp();
        assertEquals(m1.getY(), 9);
        m1.goDown();
        assertEquals(m1.getY(), 10);
        m1.goLeft();
        assertEquals(m1.getX(), 9);
        m1.goRight();
        assertEquals(m1.getX(), 10);

        m2.goUp();
        assertEquals(m2.getY(), 22);
        m2.goDown();
        assertEquals(m2.getY(), 23);
        m2.goLeft();
        assertEquals(m2.getX(), 14);
        m2.goRight();
        assertEquals(m2.getX(), 15);

        m3.goUp();
        assertEquals(m3.getY(), 15);
        m3.goDown();
        assertEquals(m3.getY(), 15);
        m3.goLeft();
        assertEquals(m3.getX(), 33);
        m3.goRight();
        assertEquals(m3.getX(), 34);


        m1.RandomMove();
        m1.RandomMove();
        m1.RandomMove();
        m1.RandomMove();
        m1.RandomMove();
        m2.RandomMove();
        m2.RandomMove();
        m2.RandomMove();
        m2.RandomMove();
        m2.RandomMove();
        m3.RandomMove();
        m3.RandomMove();
        m3.RandomMove();
        m3.RandomMove();
        m3.RandomMove();
    }

    @Test
    public void testAttack(){
        m1.RandomAttack();
        m1.RandomAttack();
        worldScreen.Refresh();
        m1.RandomAttack();
        m1.RandomAttack();
        worldScreen.Refresh();
        m1.RandomAttack();
        m2.RandomAttack();
        worldScreen.Refresh();
        m2.RandomAttack();
        m2.RandomAttack();
        worldScreen.Refresh();
        m2.RandomAttack();
        m2.RandomAttack();
        worldScreen.Refresh();
        m3.RandomAttack();
        m3.RandomAttack();
        worldScreen.Refresh();
        m3.RandomAttack();
        m3.RandomAttack();
        m3.RandomAttack();
        worldScreen.Refresh();

    }

}
