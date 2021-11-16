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
    private LevelController levelController;
    private ArrayList<Monster> monsters;
    private ExecutorService exec;

    public GameThread(WorldScreen worldScreen){
        levelController=worldScreen.getLevelController();
        calabash=worldScreen.getCalabash();
        monsters=worldScreen.getMonsters();
        exec = Executors.newCachedThreadPool();

    }

    public void start(){
        exec.execute(calabash);
        exec.execute(levelController);
        for (Monster m:monsters)
            exec.execute(m);
    }

    public void addMonsterThread(Monster m){
        exec.execute(m);
    }



}
