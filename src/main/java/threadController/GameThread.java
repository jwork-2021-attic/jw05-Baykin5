package threadController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;

import creature.Calabash;
import creature.Monster;
import level.LevelController;

import screen.WorldScreen;


public class GameThread extends Thread{
    
    private Calabash calabash;
    private Calabash calabash2;
    private Calabash calabash3;
    private LevelController levelController;
    private ArrayList<Monster> monsters;
    private ExecutorService exec;

    public GameThread(WorldScreen worldScreen){
        levelController=worldScreen.getLevelController();
        calabash=worldScreen.getCalabash();
        calabash2=worldScreen.getCalabash2();
        calabash3=worldScreen.getCalabash3();
        monsters=worldScreen.getMonsters();
        exec = Executors.newCachedThreadPool();

    }

    public void start(){
        exec.execute(calabash);
        if (calabash2!=null)
            exec.execute(calabash2);
        if (calabash3!=null)
            exec.execute(calabash3);
        exec.execute(levelController);
        for (Monster m:monsters)
            exec.execute(m);
    }

    public void addMonsterThread(Monster m){
        exec.execute(m);
    }

    public void shutdown(){
        exec.shutdownNow();
    }
}
