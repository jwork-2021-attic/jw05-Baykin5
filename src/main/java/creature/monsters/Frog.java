package creature.monsters;

import java.awt.Color;
import util.World;
import creature.Monster;
import screen.WorldScreen;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Frog extends Monster {

    private static int shootFreq=2;
    private int shootCnt=0;
    private static int actionFreq=5;
    private int actionCnt=0;

    static private int bulletFreq=5;

    

    public Frog(World world, WorldScreen worldScreen) {
        super((char) 233, world, worldScreen);
        attack = 20;
        maxHP=200;
        HP = maxHP;
        int t=new Random().nextInt(2);
        if (t==0)
            hasHeart=true;
    }

    public void run() {
        try {
            while (HP > 0) {
                TimeUnit.MILLISECONDS.sleep(refreshFreq);
                UpdateState();
                if (HP > 0 && actionCnt==actionFreq) {
                    actionCnt=0;
                    RandomAttack();
                    RandomMove();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Frog thread ERROR");
        } finally{
            worldScreen.deleteMonster(this);
            if (hasHeart){
                worldScreen.addHeart(getX(), getY());
            }
            
        }
    }

    public void UpdateState() {
        actionCnt++;
    }

    public void RandomMove() {
        int Cx = worldScreen.getCalabashX();
        int Cy = worldScreen.getCalabashY();
        int x = getX();
        int y = getY();
        int t = new Random().nextInt(2);
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
            int Cx = worldScreen.getCalabashX();
            int Cy = worldScreen.getCalabashY();
            int x = getX();
            int y = getY();
            if (Math.abs((Cx - x)) > Math.abs(Cy - y)) {
                if (x < Cx) { // Calabash is on the right
                    attackRight(bulletFreq);
                    //attackRightUp(bulletFreq);
                    //attackRightDown(bulletFreq);
                } else if (x > Cx) {
                    attackLeft(bulletFreq);
                    //attackLeftUp(bulletFreq);
                    //attackLeftDown(bulletFreq);
                }

            } else {
                if (y < Cy) {// Calabash is on the down
                    attackDown(bulletFreq);
                    //attackLeftDown(bulletFreq);
                    //attackRightDown(bulletFreq);

                } else if (y > Cy) {
                    attackUp(bulletFreq);
                    //attackRightUp(bulletFreq);
                    //attackLeftUp(bulletFreq);
                }
            }

            shootCnt = 0;
        } else
            shootCnt++;
    }
}
