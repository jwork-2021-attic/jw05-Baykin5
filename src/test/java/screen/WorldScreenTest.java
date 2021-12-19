package screen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import bullet.CalabashBullet;
import creature.Monster;
import gamedata.Data;
import map.MapGenerator;
import util.Floor;
import util.Heart;
import util.VictorSign;
import util.Wall;
import util.World;

public class WorldScreenTest {

    WorldScreen worldScreen;

    @Before
    public void setUP() throws IOException {
        Data data = new Data();
        int[][] raws = new int[World.HEIGHT][World.WIDTH];
        for (int i = 0; i < World.HEIGHT; i++) {
            for (int j = 0; j < World.WIDTH; j++)
                if (i == 0 || j == 0 || i == World.HEIGHT - 1 || j == World.WIDTH - 1) // Wall
                    raws[i][j] = 0;
                else // Floor
                    raws[i][j] = 1;
        }
        data.set(1, 2, 200, 200, 50, raws);
        worldScreen = new WorldScreen(data);
    }

    @Test
    public void testConstructor() throws IOException{
        WorldScreen tempScreen=new WorldScreen(1);
    }
    @Test
    public void testLoadData(){
        assertEquals(worldScreen.getLevel(), 1);
        assertEquals(worldScreen.getCalabash().getHP(), 200);
        assertEquals(worldScreen.getCalabash().getMaxHP(), 200);
        assertEquals(worldScreen.getCalabash().getAttack(), 50);
    }

    @Test
    public void testCreateMap() {
        MapGenerator mapGenerator = new MapGenerator(1234);
        mapGenerator.GenarateMap(4);
        int raws[][] = new int[World.HEIGHT][World.WIDTH];
        for (int i = 0; i < World.HEIGHT; i++)
            for (int j = 0; j < World.WIDTH; j++) {
                if (i == 0 || j == 0 || i == World.HEIGHT - 1 || j == World.WIDTH - 1) // Wall
                    raws[i][j] = 0;
                else // Floor
                    raws[i][j] = 1;
            }
        worldScreen.CreateMap(raws);
        for (int i = 0; i < World.HEIGHT; i++)
            for (int j = 0; j < World.WIDTH; j++) {
                if (i == 0 || j == 0 || i == World.HEIGHT - 1 || j == World.WIDTH - 1) // Wall
                    assertTrue(worldScreen.getWorld().get(j, i) instanceof Wall);
                else // Floor
                    assertTrue(worldScreen.getWorld().get(j, i) instanceof Floor);
            }
    }

    @Test
    public void testLose() {
        worldScreen.Lose();
        assertTrue(worldScreen.loseSign);
    }

    @Test
    public void testPassLevel() {
        worldScreen.PassLevel();
        assertTrue(worldScreen.winSign);
    }

    @Test
    public void testSingleGameWin() throws IOException {
        long last = worldScreen.seed;
        worldScreen.SingleGameWin();
        assertEquals(2, worldScreen.level);
        assertNotEquals(last, worldScreen.seed);
    }

    @Test
    public void testAddBullet() {
        worldScreen.addBullet(
                new CalabashBullet(worldScreen.getWorld(), worldScreen, worldScreen.calabash.getAttack(), 2));

    }

    @Test
    public void testAddHeart() {
        worldScreen.addHeart(2, 2);
        assertTrue(worldScreen.getWorld().get(2, 2) instanceof Heart);
    }

    @Test
    public void testAddVictorSign() {
        worldScreen.addVictorSign();
        boolean sign = false;
        for (int i = 0; i < World.HEIGHT; i++)
            for (int j = 0; j < World.WIDTH; j++) {
                if (worldScreen.getWorld().get(j, i) instanceof VictorSign)
                    sign = true;
            }
        assertTrue(sign);
    }

    @Test
    public void testDisplayOutput() {
        AsciiPanel terminal = new AsciiPanel(50, 28, AsciiFont.Baykin_30_30);
        worldScreen.displayOutput(terminal);
    }

    @Test
    public void testGetCalabashX() {
        assertEquals(1, worldScreen.getCalabashX());
    }

    @Test
    public void testGetCalabashY() {
        assertEquals(20, worldScreen.getCalabashY());
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, worldScreen.getLevel());
    }

    @Test
    public void testGetSeed() {
        assertEquals(worldScreen.getSeed(), worldScreen.seed);
    }

    @Test
    public void testGetWorld() {
        assertNotNull(worldScreen.getWorld());
    }

    @Test
    public void testPlayerAction() {
        worldScreen.playerAction(1, 87);//
    }

    @Test
    public void testSetSeed() {
        worldScreen.setSeed(1234);
        assertEquals(worldScreen.seed, 1234);
    }
}
