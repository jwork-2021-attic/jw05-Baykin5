package creature;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import creature.monsters.Frog;
import gamedata.Data;
import screen.WorldScreen;
import util.World;

public class CalabashTest {

    Calabash calabash;
    Calabash calabash2;
    Calabash calabash3;
    WorldScreen worldScreen;
    WorldScreen worldScreen2;
    WorldScreen worldScreen3;
    
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
        data.set(0, 1, 100, 100, 20, raws);
        worldScreen = new WorldScreen(data);
        calabash=worldScreen.getCalabash();

        data.set(0, 2, 100, 100, 20, raws);
        worldScreen2 = new WorldScreen(data);
        calabash2=worldScreen2.getCalabash();
        data.set(0, 3, 100, 100, 20, raws);
        worldScreen3 = new WorldScreen(data);
        calabash3=worldScreen3.getCalabash();
    }

    @Test
    public void testMove(){
        assertEquals(calabash.getX(), 1);
        assertEquals(calabash.getY(), 20);
        calabash.goDown();
        assertEquals(calabash.getY(), 21);
        calabash.goRight();
        assertEquals(calabash.getX(), 2);
        calabash.goUp();
        assertEquals(calabash.getY(), 20);
        calabash.goLeft();
        assertEquals(calabash.getX(), 1);

    }

    @Test
    public void testHit(){
        assertEquals(calabash.getHP(), 100);
        assertEquals(calabash.getMaxHP(), 100);
        calabash.beHit(10);
        assertEquals(calabash.getHP(), 90);
        assertEquals(calabash.getMaxHP(), 100);
        calabash.heal(20);
        assertEquals(calabash.getHP(), 100);
        assertEquals(calabash.getMaxHP(), 100);
    }

    @Test
    public void testAttack(){
        calabash.attackLeft();
        calabash.attackDown();
        calabash.attackRight();
        calabash.attackUp();
        worldScreen.Refresh();
        calabash.attackLeft();
        calabash.attackDown();
        calabash.attackRight();
        calabash.attackUp();
        worldScreen.Refresh();
        calabash.attackLeft();
        calabash.attackDown();
        calabash.attackRight();
        calabash.attackUp();
        worldScreen.Refresh();
        calabash.attackLeft();
        calabash.attackDown();
        calabash.attackRight();
        calabash.attackUp();
        worldScreen.Refresh();
    }

    @Test
    public void testSkill(){
        calabash.skill();
        assertEquals(calabash.getAttack(), 40);

        Frog m=new Frog(worldScreen2.getWorld(), worldScreen, 1234);
        assertEquals(m.getHP(), 200);
        worldScreen2.getWorld().put(m, 10, 10);
        worldScreen2.monsters.add(m);
        calabash2.skill();
        m.UpdateState();
        assertEquals(m.getHP(),150);

        calabash3.beHit(20);
        assertEquals(calabash3.getHP(), 80);
        calabash3.skill();
        calabash3.beHit(20);
        assertEquals(calabash3.getHP(), 70);
    }
}
