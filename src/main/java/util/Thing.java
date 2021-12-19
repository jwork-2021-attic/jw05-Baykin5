package util;


public class Thing {

    protected World world;

    public Tile<? extends Thing> tile;

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    public Thing( char glyph, World world) {
        this.glyph = glyph;
        this.world = world;
    }



    private final char glyph;

    public char getGlyph() {
        return this.glyph;
    }


    public void beHit(int attack){

    }

    public void beDeleted(){

    }

}
