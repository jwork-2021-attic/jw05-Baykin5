package bullet;

import screen.WorldScreen;

import util.World;

import util.Jar;
import util.Thing;
import util.Barrier;
import creature.Calabash;
import util.Wall;

public class MonsterBullet extends Bullet {

    protected int refreshFreq = 5;
    protected int refreshCnt = 0;

    public MonsterBullet(World world, WorldScreen worldScreen, int attack, int sign) {
        super((char) 7, world, worldScreen); // red
        this.attack = attack;
        this.moveSign = sign;
    }

    public MonsterBullet(World world, WorldScreen worldScreen, int attack, int sign, int speed) {
        super((char) 7, world, worldScreen); // red
        this.attack = attack;
        this.moveSign = sign;
        refreshFreq = speed;
    }

    public void Refresh() {
        refreshCnt++;
        UpdateState();
        if (refreshCnt == refreshFreq) {
            Move();
            refreshCnt = 0;
        }
        UpdateState();
    }

    public void UpdateState() {
        if (!hasHit) {
            int x = getX();
            int y = getY();
            Thing t = world.get(x, y);
            if (t instanceof Calabash) {
                t.beHit(attack);
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
            } else if (t instanceof Barrier) {
                hasHit = true;
                worldScreen.deleteBullet(this);
                t.beDeleted();
                world.deleteBullet(this, getX(), getY());
            }
        }

    }

    public void Move() {
        if (!hasHit) {
            int x = getX() + direction[moveSign][0];
            int y = getY() + direction[moveSign][1];
            Thing t = world.get(x, y);
            if (t instanceof Wall || t instanceof Barrier) {
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
            } else if (t instanceof Jar) {
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
                t.beDeleted();
            } else {
                world.deleteBullet(this, getX(), getY());
                this.moveTo(x, y);
            }

        }
    }
}
