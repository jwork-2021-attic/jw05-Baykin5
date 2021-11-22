package bullet;

import screen.WorldScreen;

import util.World;
import java.awt.Color;


import creature.Calabash;
import util.Wall;

public class MonsterBullet extends Bullet {

    protected int refreshFreq=5;
    protected int refreshCnt=0;


    public MonsterBullet(World world, WorldScreen worldScreen, int attack, int sign) {
        super(new Color(255, 0, 0), (char) 7, world, worldScreen); // red
        this.attack = attack;
        this.moveSign = sign;
    }

    public MonsterBullet(World world, WorldScreen worldScreen, int attack, int sign,int speed) {
        super(new Color(255, 0, 0), (char) 7, world, worldScreen); // red
        this.attack = attack;
        this.moveSign = sign;
        refreshFreq=speed;
    }

    public void Refresh(){
        refreshCnt++;
        UpdateState();
        if (refreshCnt==refreshFreq){
            Move();
            refreshCnt=0;
        }
        UpdateState();
    }

    public void UpdateState() {
        if (!hasHit) {
            int x = getX();
            int y = getY();
            if (world.get(x, y) instanceof Calabash) {
                world.get(x, y).beHit(attack);
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
            }
        }

    }

    public void Move() {
        if (!hasHit) {
            int x = getX() + direction[moveSign][0];
            int y = getY() + direction[moveSign][1];
            if (!(world.get(x, y) instanceof Wall)) {
                world.deleteBullet(this, getX(), getY());
                this.moveTo(x, y);
            } else {
                hasHit = true;
                worldScreen.deleteBullet(this);
                world.deleteBullet(this, getX(), getY());
            }

        }
    }
}
