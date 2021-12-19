package bullet;

import screen.WorldScreen;
import util.World;
import util.Thing;

public abstract class Bullet extends Thing {
    int attack;
    int moveSign; // indicate the direction
    boolean hasHit;
    WorldScreen worldScreen;

    static final int direction[][] = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }, { -1, -1 }, { 1, -1 }, { 1, 1 }, { -1, 1 } };

    public Bullet(char glyph, World world, WorldScreen worldscreen) {
        super(glyph, world);
        this.worldScreen = worldscreen;
        hasHit = false;
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    public abstract void UpdateState();

    public abstract void Move();

    public abstract void Refresh();

}
