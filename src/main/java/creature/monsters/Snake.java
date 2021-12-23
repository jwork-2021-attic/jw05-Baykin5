package creature.monsters;

import util.World;
import creature.Calabash;
import creature.Monster;
import screen.WorldScreen;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Snake extends Monster {

    private static int shootFreq = 2;
    private int shootCnt = 0;
    private static int actionFreq = 5;
    private int actionCnt = 0;
    static private int bulletFreq = 5;

    long seed;
    Random r;

    public Snake(World world, WorldScreen worldScreen, long seed) {
        super((char) 224, world, worldScreen);
        this.seed = seed;
        r = new Random(seed);
        //this.id=id;

        attack = 20;
        maxHP = 2000;
        HP = maxHP;
    }

    public void run() {
        try {
            while (HP > 0) {
                TimeUnit.MILLISECONDS.sleep(refreshFreq);
                //System.out.println("Snake"+id+" Action");
                UpdateState();
                if (HP > 0 && actionCnt == actionFreq) {
                    actionCnt = 0;
                    RandomMove();
                    RandomAttack();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Monster thread ERROR");
        }
    }

    public void UpdateState() {
        actionCnt++;
        if (HP <= 0) {
            worldScreen.DefeatBoss();
        }

    }

    public void RandomMove() {
        Calabash temp=getNearestCalabash();
        int Cx = temp.getX();
        int Cy = temp.getY();
        int x = getX();
        int y = getY();
        int t = r.nextInt(2);
        switch (t) {
            case 0: {
                if (x < Cx) { // Calabash is on the right
                    goRight();
                } else if (x > Cx) {
                    goLeft();
                }
                break;
            }
            case 1: {
                if (y < Cy) {// Calabash is on the down
                    goDown();

                } else if (y > Cy) {
                    goUp();
                }
                break;
            }
        }
    }

    public void RandomAttack() {
        if (shootCnt == shootFreq) { // shoot per 2 moving
            Calabash temp=getNearestCalabash();
            int Cx = temp.getX();
            int Cy = temp.getY();
            int x = getX();
            int y = getY();
            if (Math.abs((Cx - x)) > Math.abs(Cy - y)) {
                if (x < Cx) { // Calabash is on the right
                    attackRightUp(bulletFreq);
                    attackRight(bulletFreq);
                    attackRightDown(bulletFreq);
                } else if (x > Cx) {
                    attackLeftUp(bulletFreq);
                    attackLeft(bulletFreq);
                    attackLeftDown(bulletFreq);
                }

            } else {
                if (y < Cy) {// Calabash is on the down
                    attackLeftDown(bulletFreq);
                    attackDown(bulletFreq);
                    attackRightDown(bulletFreq);

                } else if (y > Cy) {
                    attackLeftUp(bulletFreq);
                    attackUp(bulletFreq);
                    attackRightUp(bulletFreq);
                }
            }
            shootCnt = 0;
        } else
            shootCnt++;
    }
}
