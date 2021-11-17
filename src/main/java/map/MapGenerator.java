package map;

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
        case 0: {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    if ((i >= 11 && i < 17 && j >= 27 && j < 33) || (i >= 17 && i < 23 && j >= 21 && j < 39)
                            || (i >= 23 && i < 29 && j >= 27 && j < 33))
                        map[i][j] = 0;
            break;
        }
        case 1: {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    if ((i >= 3 && i <= 9 && ((j >= 3 && j < 10) || (j >= 50 && j < 57)))
                            || ((i >= 30 && i <= 36) && ((j >= 3 && j < 10) || (j >= 50 && j < 57))))
                        map[i][j] = 0;
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
        default: {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) {
                    if (((j==5 || j==55)&& ((i>=3 && i<=8) || (i>=32 && i<=37))) ||
                        ((i==5 || i==35)&& ((j>=3 && j<=10) || j>=50 && j<=57)))
                        map[i][j]=0;
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
