package level;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import screen.WorldScreen;
import util.World;


public class LevelController implements Runnable { // 控制关卡运行

    WorldScreen worldScreen;

    static final int[]TotalMonsterNums={5,10,10000};
    static final int[]NormalMonsterTypeNums={1,1,1};
    static final int BossTypeNum=1;

    int TotalMonsterNum;
    int HasCreateNum;
    int MaxMonsterNum; // the max num of monster at the same time
    int NormalMonsterTypeNum;
    boolean bossSign=false;

    public LevelController(WorldScreen worldScreen,boolean bossSign) {
        this.worldScreen = worldScreen;
        int level=worldScreen.getLevel();
        TotalMonsterNum = TotalMonsterNums[level];
        NormalMonsterTypeNum = NormalMonsterTypeNums[level];
        HasCreateNum = 0;
        MaxMonsterNum = 4;
        this.bossSign=bossSign;
    }

    public void run() {
        System.out.println("LevelController thread running!");
        try {
            if (bossSign){
                int t=new Random().nextInt(BossTypeNum);
                worldScreen.addBoss(t,World.WIDTH/2,World.HEIGHT/2);
            }
            int nowMonsternum=0;
            while (HasCreateNum < TotalMonsterNum && !(worldScreen.hasDefeatBoss())) {
                TimeUnit.MILLISECONDS.sleep(2000);
                nowMonsternum = worldScreen.getMonsters().size();
                if (nowMonsternum < MaxMonsterNum && !(worldScreen.hasDefeatBoss())) {
                    int x;
                    int y;
                    int Cx = worldScreen.getCalabashX();
                    int Cy = worldScreen.getCalabashY();
                    do {
                        x = new Random().nextInt(World.WIDTH);
                        y = new Random().nextInt(World.HEIGHT);

                    } while ((x - Cx > 5 || Cx - x > 5) && (y - Cy > 5 || Cy - y > 5));
                    int t=new Random().nextInt(NormalMonsterTypeNum);
                    if (worldScreen.addMonster(t, x, y)) {
                        HasCreateNum++;
                    }
                }
            }
            do {
                TimeUnit.MILLISECONDS.sleep(2000);
                nowMonsternum=worldScreen.getMonsters().size();
            } while (nowMonsternum>0);

        } catch (InterruptedException e) {
            System.out.println("Level Thread ERROR");
        } finally {
            System.out.println("WIN!");
            worldScreen.addVictorSign();
        }

    }

}
