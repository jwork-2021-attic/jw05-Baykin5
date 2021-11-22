package creature;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import util.World;
import util.Wall;
import util.Floor;
import util.Heart;
import util.VictorSign;
import bullet.Bullet;
import bullet.CalabashBullet;
import screen.WorldScreen;

public abstract class Calabash extends Creature {

    protected int attack;
    protected int HP;
    protected int maxHP;
    protected WorldScreen worldScreen;

    protected long lastTime;
    protected long nowTime;
    protected int skillCD;
    protected int skillCDCount;

    protected int shootCnt;
    protected int shootFreq = (int) (0.25 * 1000); // can shoot 1 per 500ms

    public Calabash(Color color, World world, WorldScreen worldScreen) {
        super(color, (char) 2, world);
        this.worldScreen = worldScreen;
        lastTime = System.currentTimeMillis();
        shootCnt = shootFreq;
    }

    @Override
    public void beHit(int attack) {
        HP -= attack;
        if (HP < 0)
            HP = 0;
    }

    public int getHP() {
        return HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public synchronized void goUp() {
        int x = this.getX();
        int y = this.getY();
        if (world.get(x, y - 1) instanceof VictorSign) {
            worldScreen.PassLevel();
        }
        if (y > 0 && (world.get(x, y - 1) instanceof Heart)) {
            heal(20);
            this.moveTo(x, y - 1);
        } else if (y > 0 && (world.get(x, y - 1) instanceof Floor) || (world.get(x, y - 1) instanceof Bullet)) {
            this.moveTo(x, y - 1);
        }

    }

    public synchronized void goDown() {
        int x = this.getX();
        int y = this.getY();
        if (world.get(x, y + 1) instanceof VictorSign) {
            worldScreen.PassLevel();
        }
        if (y < World.HEIGHT - 1 && (world.get(x, y + 1) instanceof Heart)) {
            heal(20);
            this.moveTo(x, y + 1);
        } else if (y < World.HEIGHT - 1 && (world.get(x, y + 1) instanceof Floor)
                || (world.get(x, y + 1) instanceof Bullet)) {
            this.moveTo(x, y + 1);

        }
    }

    public synchronized void goLeft() {
        int x = this.getX();
        int y = this.getY();
        if (world.get(x - 1, y) instanceof VictorSign) {
            worldScreen.PassLevel();
        }
        if (x > 0 && (world.get(x - 1, y) instanceof Heart)) {
            heal(20);
            this.moveTo(x - 1, y);
        } else if (x > 0 && (world.get(x - 1, y) instanceof Floor) || (world.get(x - 1, y) instanceof Bullet)) {
            this.moveTo(x - 1, y);
        }
    }

    public synchronized void goRight() {
        int x = this.getX();
        int y = this.getY();
        if (world.get(x + 1, y) instanceof VictorSign) {
            worldScreen.PassLevel();
        }
        if (x < World.WIDTH - 1 && (world.get(x + 1, y) instanceof Heart)) {
            heal(20);
            this.moveTo(x + 1, y);
        }
        if (x < World.WIDTH - 1 && (world.get(x + 1, y) instanceof Floor) || (world.get(x + 1, y) instanceof Bullet)) {
            this.moveTo(x + 1, y);

        }
    }

    public synchronized void attackUp() {
        if (shootCnt >= shootFreq) {
            int x = getX();
            int y = getY() - 1;
            if (!(world.get(x, y) instanceof Wall)) {
                Bullet b = new CalabashBullet(world, worldScreen, attack, 0);
                world.put(b, x, y);
                worldScreen.addBullet(b);
            }
            shootCnt = 0;
        }

    }

    public synchronized void attackDown() {
        if (shootCnt >= shootFreq) {
            int x = getX();
            int y = getY() + 1;
            if (!(world.get(x, y) instanceof Wall)) {
                Bullet b = new CalabashBullet(world, worldScreen, attack, 1);
                world.put(b, x, y);
                worldScreen.addBullet(b);
            }
            shootCnt = 0;
        }
    }

    public synchronized void attackLeft() {
        if (shootCnt >= shootFreq) {
            int x = getX() - 1;
            int y = getY();
            if (!(world.get(x, y) instanceof Wall)) {
                Bullet b = new CalabashBullet(world, worldScreen, attack, 2);
                world.put(b, x, y);
                worldScreen.addBullet(b);
            }
            shootCnt = 0;
        }
    }

    public synchronized void attackRight() {
        if (shootCnt >= shootFreq) {
            int x = getX() + 1;
            int y = getY();
            if (!(world.get(x, y) instanceof Wall)) {
                Bullet b = new CalabashBullet(world, worldScreen, attack, 3);
                world.put(b, x, y);
                worldScreen.addBullet(b);
            }
            shootCnt = 0;
        }
    }

    public abstract void skill();

    public void run() {
        System.out.println("calabash thread running!");
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(5);
                updateGameState();
                updateShootState(); // 限制射击间隔
                updateStates();
            }
        } catch (InterruptedException e) {
            System.out.println("Calabash action error!");
        }
    }

    public void heal(int x) {
        if (HP + x > maxHP)
            HP = maxHP;
        else
            HP += x;
    }

    public void setWorld(World world, WorldScreen worldScreen) {
        this.world = world;
        this.worldScreen = worldScreen;
    }

    public void updateGameState() {
        if (HP == 0)
            worldScreen.Lose();
    }

    public void updateStates() {
    }

    public void updateShootState() {
        nowTime = System.currentTimeMillis();
        shootCnt += nowTime - lastTime;
    }

    public int getAttack() {
        return attack;
    }

    public int getSkillCD() {
        int res = skillCDCount * 100 / skillCD;
        if (res <= 100)
            return res;
        else
            return 100;
    }

}
