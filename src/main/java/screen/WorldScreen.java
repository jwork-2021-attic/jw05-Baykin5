package screen;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import creature.Creature;
import creature.Calabash;
import util.World;
import creature.calabashes.*;
import creature.Monster;
import creature.monsters.*;
import level.LevelController;
import util.Wall;
import util.Floor;
import bullet.Bullet;
import util.VictorSign;


import map.MapGenerator;
import threadController.GameThread;
import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    static final int MapTypeNum=2;

    private World world;
    int [][]raws;
    Calabash calabash;
    ArrayList<Monster> monsters;
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> deleteBullets;
    LevelController levelController;
    GameThread gameThread;

    static final int TotalLevelNum=3;
    int level;
    boolean winSign;


    private int MapHeight;
    private int MapWidth;
    

    public WorldScreen(int calabashType) {
        world = new World();
        level=0;
        switch(calabashType){
            case 1:{
                calabash=new Dawa(world,this);
                break;
            }
            /*
            case 2:{
                calabash=new Erwa(world,this);
                break;
            }
            */

            default:
            calabash=new Dawa(world,this);
        }

        calabash = new Dawa(world,this);

        monsters=new ArrayList<Monster>();
        bullets=new ArrayList<Bullet>();
        deleteBullets=new ArrayList<Bullet>();

        MapGenerator mapGenerator=new MapGenerator();
        int t=new Random().nextInt(MapTypeNum);
        mapGenerator.GenarateMap(t);
        CreateMap(mapGenerator);

        world.put(calabash, 1, 20);
        levelController=new LevelController(this);
        gameThread=new GameThread(this);
        gameThread.start();

    }
    public WorldScreen(int level,Calabash calabash) {
        world = new World();
        this.level=level;
        this.calabash=calabash;
        monsters=new ArrayList<Monster>();
        bullets=new ArrayList<Bullet>();
        deleteBullets=new ArrayList<Bullet>();

        MapGenerator mapGenerator=new MapGenerator();
        int t=new Random().nextInt(MapTypeNum);
        mapGenerator.GenarateMap(t);
        CreateMap(mapGenerator);

        calabash.setWorld(world, this);
        world.put(this.calabash, 1, 20);
        levelController=new LevelController(this);
        gameThread=new GameThread(this);
        gameThread.start();

    }

    public void CreateMap(MapGenerator mapGenerator){
        MapHeight=mapGenerator.getHeight();
        MapWidth=mapGenerator.getWidth();
        raws=mapGenerator.getMap();
        for (int i=0;i<MapHeight;i++)
            for (int j=0;j<MapWidth;j++){
                if (raws[i][j]==1){
                    world.put(new Floor(world),j,i);
                }
                else{
                    world.put(new Wall(world),j,i);
                }
            }
    }
    public World getWorld(){
        return world;
    }
    public int getLevel(){
        return level;
    }
    public int getCalabashX(){
        return calabash.getX();
    }
    public int getCalabashY(){
        return calabash.getY();
    }
    public Calabash getCalabash(){
        return calabash;
    }
    public LevelController getLevelController(){
        return levelController;
    }

    public ArrayList<Monster> getMonsters(){
        return monsters;
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public void addVictorSign(){
        int x;
        int y;
        do{
            x = new Random().nextInt(World.WIDTH);
            y = new Random().nextInt(World.HEIGHT);
        }while(!(world.get(x, y) instanceof Floor));
        VictorSign s=new VictorSign(world);
        world.put(s, x, y);
    }
    public boolean addMonster(int type,int x,int y){
        Monster m;
        switch(type){
            case 0:{
                m=new Frog(world, this);
                break;
            }
            default:
            m=new Frog(world,this);
        }
        if (!(world.get(x, y) instanceof Creature) && !(world.get(x, y) instanceof Wall) ){
            world.put(m, x, y);
            monsters.add(m);
            gameThread.addMonsterThread(m);
            return true;
        }
        else
            return false;
    }
    public void deleteMonster(Monster m){
        monsters.remove(m);
        world.deleteMonster(m, m.getX(), m.getY());
    }


    public void addBullet(Bullet b){
        bullets.add(b);
    }

    public void deleteBullet(Bullet b){
        deleteBullets.add(b);
    }

    public void PassLevel(){
        winSign=true;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
        UIupdate(terminal);
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (winSign){
            if (level==TotalLevelNum-1){
                winSign=false;
                return new WinScreen();
            }
            else{
                level++;
                winSign=false;
                return new WorldScreen(level,calabash);
            }
        }
        int x=key.getKeyCode();
            switch(x){
            case 87:{//up W
                calabash.goUp(); break; }
            case 83:{//down S
                calabash.goDown(); break;}
            case 65:{//left A
                calabash.goLeft(); break;}
            case 68:{//right D
                calabash.goRight();break;}
            case 38:{//Attack UP ↑
                calabash.attackUp();break; }
            case 40:{//Attack Down ↓
                calabash.attackDown();break;}
            case 37:{//Attack Left ←
                calabash.attackLeft();break;}
            case 39:{//Attack Right →
                calabash.attackRight();break;}
            case 32:{//Skill SPACE
                calabash.skill();break;}
            default: break;  
            }
        return this;
    }

    void UIupdate(AsciiPanel terminal){
        terminal.write("HP: "+calabash.getHP()+"/"+calabash.getMaxHP(),0,World.HEIGHT+1);
        terminal.write("Attack: "+calabash.getAttack(),0,World.HEIGHT+2);
        terminal.write("Skill: "+calabash.getSkillCD()+"%",0,World.HEIGHT+3);
    }

    public void Refresh(){
        for (Bullet b:bullets){
            b.UpdateState();
            b.Move();
            b.UpdateState();
        }
        for (Bullet b:deleteBullets){
            bullets.remove(b);
        }
        deleteBullets.clear();
    }

}
