package creature.monsters;

import java.awt.Color;
import util.World;
import creature.Monster;
import screen.WorldScreen;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Frog extends Monster {

    private int shootCnt;

    public Frog(World world, WorldScreen worldScreen) {
        super(new Color(255, 0, 0), (char) 1, world, worldScreen);
        attack = 20;
        HP = 200;
        maxHP = 200;
        shootCnt = 0;
    }

    public void run() {
        try {
            while (HP > 0) {
                TimeUnit.MILLISECONDS.sleep(1000);
                UpdateState();
                if (HP > 0) {
                    RandomAttack();
                    RandomMove();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Monster thread ERROR");
        }
    }

    public void UpdateState() {
        if ((float) (HP) / maxHP < 0.7) {
            this.color = new Color(255, 165, 0);
        }
        if ((float) HP / maxHP < 0.3) {
            this.color = new Color(255, 255, 0);
        }
        if (HP <= 0)
            worldScreen.deleteMonster(this);

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
        if (shootCnt == 2) { // shoot per 2 moving
            int Cx = worldScreen.getCalabashX();
            int Cy = worldScreen.getCalabashY();
            int x = getX();
            int y = getY();
            if (Cx - x > Cy - y) {
                if (x < Cx) { // Calabash is on the right
                    attackRight();
                    attackRightUp();
                    attackRightDown();
                } else if (x > Cx) {
                    attackLeft();
                    attackLeftUp();
                    attackLeftDown();
                }

            } else {
                if (y < Cy) {// Calabash is on the down
                    attackDown();
                    attackLeftDown();
                    attackRightDown();

                } else if (y > Cy) {
                    attackUp();
                    attackRightUp();
                    attackLeftUp();
                }
            }

            shootCnt = 0;
        } else
            shootCnt++;
    }
}
