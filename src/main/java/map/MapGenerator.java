package map;

import java.util.Random;

import util.World;

public class MapGenerator {
    private int[][] map;
    private int height;
    private int width;

    public MapGenerator() {
        height = World.HEIGHT;
        width = World.WIDTH;
        map = new int[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if (i == 0 || j == 0 || i == height - 1 || j == width - 1) // Wall
                    map[i][j] = 0;
                else // Floor
                    map[i][j] = 1;
            }

    }

    public void GenarateMap(int levelType) {
        switch (levelType) {
        case 0: case 1: case 2:{
            //int barrierNum=t.Random(10)+20;
            for (int i=1;i<height-1;i++){
                for (int j=1;j<width-1;j++){
                    if (new Random().nextInt(7)==1)
                        map[i][j]=2;
                    if (new Random().nextInt(20)==1)
                        map[i][j]=3;
                }
            }
            break;
        }
        /*
        case 1: {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    if ((i >= 3 && i <= 9 && ((j >= 3 && j < 10) || (j >= 50 && j < 57)))
                            || ((i >= 30 && i <= 36) && ((j >= 3 && j < 10) || (j >= 50 && j < 57))))
                        map[i][j] = 2;
            break;
        }
        case 2: {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) {
                    if ((i == 5 || i == 35) && (j > 12 && j < 20))
                        map[i][j] = 0;
                    if ((i == 4 || i == 36) && (j > 25 && j < 35))
                        map[i][j] = 0;
                    if ((i == 5 || i == 35) && (j > 40 && j < 48))
                        map[i][j] = 0;
                }
            break;
        }
        */
        default: {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) {
                    if (((j==5 || j==30) && ((i>=3 && i<=5) || (i>=20 && i<=23))) ||
                        ((i==5 || i==20) && ((j>=3 && j<=5) || j>=27 && j<=30)))
                        map[i][j]=2;
                }

            break;
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
