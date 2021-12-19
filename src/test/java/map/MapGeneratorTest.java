package map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import util.World;

public class MapGeneratorTest {
    MapGenerator mapGenerator;

    @Before
    public void setUP(){
        mapGenerator=new MapGenerator(1234);
    }
    @Test
    public void testGenarateMap() {
        mapGenerator.GenarateMap(0);
    }

    @Test
    public void testGetHeight() {
        assertEquals(World.HEIGHT,mapGenerator.getHeight());
    }

    @Test
    public void testGetWidth() {
        assertEquals(World.WIDTH, mapGenerator.getWidth());
    }
    
    @Test
    public void testGetMap() {
        assertNotNull(mapGenerator.getMap());
    }

}
