package bullet;

import screen.WorldScreen;

import util.World;
import java.awt.Color;

import util.Thing;
import creature.Monster;
import util.Barrier;
import util.Jar;
import util.Wall;

public class CalabashBullet extends Bullet {

    public CalabashBullet(World world, WorldScreen worldScreen, int attack, int sign) {
        super((char) 6, world, worldScreen); // green
        this.attack = attack;
        this.moveSign = sign;

    }

    public void Refresh(){
        UpdateState();
        Move();
        UpdateState();
    }
    public void UpdateState() {
        if (!hasHit) {
            int x = getX();
            int y = getY();
            Thing t=world.get(x, y);
            if (t instanceof Monster) {
                t.beHit(attack);
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
            }
            else if (t instanceof Barrier) {
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

            Thing t=world.get(x, y);
            if (t instanceof Wall || t instanceof Barrier){
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
            }
            else if (t instanceof Jar){
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
                t.beDeleted();
            }
            else{
                world.deleteBullet(this, getX(), getY());
                this.moveTo(x, y);
            }
        }
    }
}
