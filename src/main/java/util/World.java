package util;

public class World {

    public static final int WIDTH = 35;
    public static final int HEIGHT = 25;

    private Tile<Thing>[][] tiles;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this));
            }
        }
    }

    public Thing getItem(int x, int y) {
        return this.tiles[x][y].getItem();
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public void deleteCalabash(Thing calabash, int x, int y) {
        this.tiles[x][y].setThing(new Floor(this));
    }

    public void deleteBullet(Thing bullet, int x, int y) {
        this.tiles[x][y].getBullets().remove(bullet);
    }

    public void deleteMonster(Thing monster, int x, int y) {
        this.tiles[x][y].setThing(new Floor(this));
    }

    public void deleteItem(int x, int y) {
        this.tiles[x][y].deleteItem();
    }
}
