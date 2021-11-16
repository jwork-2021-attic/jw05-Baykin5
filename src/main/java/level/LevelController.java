package level;


import java.util.Random;
import java.util.concurrent.TimeUnit;


import screen.WorldScreen;
import util.World;


public class LevelController implements Runnable { // 控制关卡运行

    WorldScreen worldScreen;

    static final int[]TotalMonsterNums={5,10,1};
    static final int[]MonsterTypeNums={1,1,1};

    int TotalMonsterNum;
    int HasCreateNum;
    int MaxMonsterNum; // the max num of monster at the same time
    int MonsterTypeNum;

    public LevelController(WorldScreen worldScreen) {
        this.worldScreen = worldScreen;
        int level=worldScreen.getLevel();
        TotalMonsterNum = TotalMonsterNums[level];
        MonsterTypeNum = MonsterTypeNums[level];
        HasCreateNum = 0;
        MaxMonsterNum = 5;

    }

    public void run() {
        System.out.println("LevelController thread running!");
        try {
            int nowMonsternum=0;
            while (HasCreateNum < TotalMonsterNum ) {
                TimeUnit.MILLISECONDS.sleep(2000);
                nowMonsternum = worldScreen.getMonsters().size();
                if (nowMonsternum < MaxMonsterNum) {
                    int x;
                    int y;
                    int Cx = worldScreen.getCalabashX();
                    int Cy = worldScreen.getCalabashY();
                    do {
                        x = new Random().nextInt(World.WIDTH);
                        y = new Random().nextInt(World.HEIGHT);

                    } while ((x - Cx > 5 || Cx - x > 5) && (y - Cy > 5 || Cy - y > 5));
                    int t=new Random().nextInt(MonsterTypeNum);
                    if (worldScreen.addMonster(t, x, y)) {
                        HasCreateNum++;
                    }
                }
            }
            while(nowMonsternum>0){
                TimeUnit.MILLISECONDS.sleep(2000);
                nowMonsternum=worldScreen.getMonsters().size();
            }

        } catch (InterruptedException e) {
            System.out.println("Level Thread ERROR");
        } finally {
            System.out.println("WIN!");
            worldScreen.addVictorSign();
        }

    }

}
