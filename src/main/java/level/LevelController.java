package level;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import screen.WorldScreen;
import util.World;


public class LevelController implements Runnable { // 控制关卡运行

    WorldScreen worldScreen;

    static final int[]TotalMonsterNums={1,1,10000};
    static final int[]NormalMonsterTypeNums={1,2,2};
    static final int BossTypeNum=1;
    static final int MaxMonsterNum=2; //Max Monster Num MeanWhile

    int TotalMonsterNum;
    int HasCreateNum;
    int NormalMonsterTypeNum;
    int CreateHeartFreq=3;
    int CreateHeartCnt=0;
    boolean bossSign=false;

    long seed;
    Random r;

    public LevelController(WorldScreen worldScreen,boolean bossSign,long seed) {
        this.worldScreen = worldScreen;
        int level=worldScreen.getLevel();
        TotalMonsterNum = TotalMonsterNums[level];
        NormalMonsterTypeNum = NormalMonsterTypeNums[level];
        HasCreateNum = 0;
        this.bossSign=bossSign;
        this.seed=seed;
        r= new Random(seed);
    }

    public void reset(){
        int level=worldScreen.getLevel();
        TotalMonsterNum = TotalMonsterNums[level];
        NormalMonsterTypeNum = NormalMonsterTypeNums[level];
        HasCreateNum = 0;

        this.bossSign=worldScreen.getBossSign();
        this.seed=worldScreen.getSeed();
        r= new Random(seed);
    }

    public void run() {
        System.out.println("LevelController thread running!");
        try {
            if (bossSign){
                int t=r.nextInt(BossTypeNum);
                worldScreen.addBoss(t,World.WIDTH/2,World.HEIGHT/2);
            }

            int nowMonsternum=0;
            while (HasCreateNum < TotalMonsterNum && !(worldScreen.hasDefeatBoss())) {
                TimeUnit.MILLISECONDS.sleep(3000);
                nowMonsternum = worldScreen.getMonsters().size();
                int x;
                int y;
                int Cx = worldScreen.getCalabashX();
                int Cy = worldScreen.getCalabashY();
                if (nowMonsternum < MaxMonsterNum && !(worldScreen.hasDefeatBoss())) {
                    do {
                        x = r.nextInt(World.WIDTH);
                        y = r.nextInt(World.HEIGHT);
                    } while ((x - Cx > 5 || Cx - x > 5) && (y - Cy > 5 || Cy - y > 5) );
                    int t=r.nextInt(NormalMonsterTypeNum);
                    if (worldScreen.addMonster(t, x, y)) {
                        HasCreateNum++;
                    }
                }
                CreateHeartCnt++;
                if (CreateHeartCnt==CreateHeartFreq){
                    do {
                        x = r.nextInt(World.WIDTH);
                        y = r.nextInt(World.HEIGHT);
                    } while ((x - Cx > 10 || Cx - x > 10) && (y - Cy > 10 || Cy - y > 10));
                    worldScreen.addHeart(x, y);
                    CreateHeartCnt=0;
                }
            }
            do {
                TimeUnit.MILLISECONDS.sleep(3000);
                CreateHeartCnt++;
                if (CreateHeartCnt==CreateHeartFreq){
                    int x;
                    int y;
                    int Cx = worldScreen.getCalabashX();
                    int Cy = worldScreen.getCalabashY();
                    do {
                        x = r.nextInt(World.WIDTH);
                        y = r.nextInt(World.HEIGHT);
                    } while ((x - Cx > 10 || Cx - x > 10) && (y - Cy > 10 || Cy - y > 10));
                    worldScreen.addHeart(x, y);
                    CreateHeartCnt=0;
                }
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
