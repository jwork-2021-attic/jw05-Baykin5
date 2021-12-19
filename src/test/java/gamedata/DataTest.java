package gamedata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import util.World;

public class DataTest {
    Data data;

    @Before
    public void setUP() throws IOException{
        data=new Data();
        data.save("testData.txt");
    }

    @Test
    public void testGetAttack() {
        assertEquals(0, data.getAttack());
    }

    @Test
    public void testGetCalabashType() {
        assertEquals(1, data.getCalabashType());
    }

    @Test
    public void testGetHP() {
        assertEquals(0, data.getHP());
    }

    @Test
    public void testGetLevel() {
        assertEquals(0, data.getLevel());
    }

    @Test
    public void testGetMaxHP() {
        assertEquals(0, data.getMaxHP());
    }

    @Test
    public void testGetraws() {
        assertNotNull(data.getraws());
    }
    @Test
    public void testSet() {
        int[][] raws=new int[World.HEIGHT][World.WIDTH];
        for (int i=0;i<World.HEIGHT;i++){
            for (int j=0;j<World.WIDTH;j++)
                raws[i][j]=0;
        }
        data.set(1,2,100,100,20,raws);
        assertEquals(1,data.getLevel());
        assertEquals(2, data.getCalabashType());
        assertEquals(100, data.getHP());
        assertEquals(100, data.getMaxHP());
        assertEquals(20, data.getAttack());
        for (int i=0;i<World.HEIGHT;i++){
            for (int j=0;j<World.WIDTH;j++)
                assertEquals(raws[i][j],0);
        }
    }

    @Test
    public void testLoad() throws IOException {
        data.load("testData.txt");
        assertEquals(0,data.getLevel());
        assertEquals(1, data.getCalabashType());
        assertEquals(0, data.getHP());
        assertEquals(0, data.getMaxHP());
        assertEquals(0, data.getAttack());
        assertNotNull(data.getraws());
    }

    @Test
    public void testSave() {

    }

}
