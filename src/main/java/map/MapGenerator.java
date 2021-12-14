package map;

import java.util.Random;

import util.Jar;
import util.World;

public class MapGenerator {
    private int[][] map;
    private int height;
    private int width;
    private int BarrierRandomNum; // 1 Barrier in x Floor
    private int JarRandomNum;  //1 Jar in x Floor\

    long seed;
    Random r;

    public MapGenerator(long seed) {
        r=new Random(seed);
        height = World.HEIGHT;
        width = World.WIDTH;
        map = new int[height][width];
        BarrierRandomNum=7;
        JarRandomNum=20;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if (i == 0 || j == 0 || i == height - 1 || j == width - 1) // Wall
                    map[i][j] = 0;
                else // Floor
                    map[i][j] = 1;
            }

    }

    public void GenarateMap(int mapType) {
        switch (mapType) {
        case 0:
            break;
        case 1:
            BarrierRandomNum=10;
            JarRandomNum=25;
        case 2:
            BarrierRandomNum=12;
            JarRandomNum=35;
        default: 
            BarrierRandomNum=15;
            JarRandomNum=40;
            
            break;
        }
        for (int i=1;i<height-1;i++){
            for (int j=1;j<width-1;j++){
                if (r.nextInt(BarrierRandomNum)==1)
                    map[i][j]=2;
                if (r.nextInt(JarRandomNum)==1)
                    map[i][j]=3;
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
