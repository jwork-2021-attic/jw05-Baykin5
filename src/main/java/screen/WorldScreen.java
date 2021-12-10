package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import gamedata.Data;
import creature.Creature;
import creature.Calabash;
import util.World;
import creature.calabashes.*;
import creature.Monster;
import creature.monsters.*;
import level.LevelController;
import util.Wall;
import util.Barrier;
import util.Floor;
import util.Heart;
import util.Jar;
import bullet.Bullet;
import bullet.CalabashBullet;
import util.VictorSign;

import map.MapGenerator;
import threadController.GameThread;
import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    static final int MapTypeNum = 3;
    static final int TotalLevelNum = 3;

    private World world;
    int[][] raws;
    int calabashType;
    Calabash calabash;
    ArrayList<Monster> monsters;
    ArrayList<Bullet> calabashBullets;
    ArrayList<Bullet> monsterBullets;
    ArrayList<Bullet> deleteCalabashBullets;
    ArrayList<Bullet> deleteMonsterBullets;
    LevelController levelController;
    GameThread gameThread;

    int level;
    boolean winSign = false;
    boolean loseSign = false;

    private int MapHeight;
    private int MapWidth;


    private boolean BossSign = false;
    private boolean defeatBossSign=false;

    public WorldScreen(int calabashType) throws IOException {
        world = new World();
        level = 0;
        this.calabashType=calabashType;
        switch (calabashType) {
        case 1: {
            calabash = new Dawa(world, this);
            break;
        }

        case 2: {
            calabash = new Erwa(world, this);
            break;
        }

        case 3: {
            calabash = new Sanwa(world, this);
            break;
        }

        default:
            calabash = new Dawa(world, this);
        }

        monsters = new ArrayList<Monster>();
        calabashBullets = new ArrayList<Bullet>();
        monsterBullets = new ArrayList<Bullet>();
        deleteCalabashBullets = new ArrayList<Bullet>();
        deleteMonsterBullets = new ArrayList<Bullet>();

        MapGenerator mapGenerator = new MapGenerator();
        int t = new Random().nextInt(MapTypeNum);
        mapGenerator.GenarateMap(t);
        raws=mapGenerator.getMap();
        CreateMap(raws);

        world.put(calabash, 1, 20);
        levelController = new LevelController(this,BossSign);
        gameThread = new GameThread(this);
        gameThread.start();

        Data data=new Data();
        data.set(level,calabashType, calabash.getHP(), calabash.getMaxHP(), calabash.getAttack(),raws);
        data.save("SaveData.txt");

    }

    public WorldScreen(int level, Calabash calabash) throws IOException {
        world = new World();
        this.level = level;
        this.calabash = calabash;
        monsters = new ArrayList<Monster>();
        calabashBullets = new ArrayList<Bullet>();
        monsterBullets = new ArrayList<Bullet>();
        deleteCalabashBullets = new ArrayList<Bullet>();
        deleteMonsterBullets = new ArrayList<Bullet>();
        int maptype;
        if (level == 2) {
            BossSign = true;
            maptype=4;
        }
        else
            maptype = new Random().nextInt(MapTypeNum);

        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.GenarateMap(maptype);
        raws=mapGenerator.getMap();
        CreateMap(raws);

        calabash.setWorld(world, this);
        world.put(this.calabash, 1, 20);
        levelController = new LevelController(this,BossSign);
        gameThread = new GameThread(this);
        gameThread.start();


        Data data=new Data();
        data.set(level,calabashType, calabash.getHP(), calabash.getMaxHP(), calabash.getAttack(),raws);
        data.save("SaveData.txt");

    }

    public WorldScreen(Data data){
        data.print();
        world=new World();
        this.level=data.getLevel();
        if (level==2)
            BossSign=true;
        calabashType=data.getCalabashType();
        switch (calabashType) {
            case 1: {
                calabash = new Dawa(world, this);
                break;
            }
            case 2: {
                calabash = new Erwa(world, this);
                break;
            }
            case 3: {
                calabash = new Sanwa(world, this);
                break;
            }
            default:
                calabash = new Dawa(world, this);
            }
        calabash.setHP(data.getHP());
        calabash.setMaxHP(data.getMaxHP());
        calabash.setAttack(data.getAttack());
        raws=data.getraws();
        CreateMap(raws);

        monsters = new ArrayList<Monster>();
        calabashBullets = new ArrayList<Bullet>();
        monsterBullets = new ArrayList<Bullet>();
        deleteCalabashBullets = new ArrayList<Bullet>();
        deleteMonsterBullets = new ArrayList<Bullet>();

        calabash.setWorld(world, this);
        world.put(this.calabash, 1, 20);
        levelController = new LevelController(this,BossSign);
        gameThread = new GameThread(this);
        gameThread.start();


    }




    public void CreateMap(int raws[][]) {
        MapHeight = World.HEIGHT;
        MapWidth = World.WIDTH;
        for (int i = 0; i < MapHeight; i++)
            for (int j = 0; j < MapWidth; j++) {
                switch(raws[i][j]){
                    case 1: //Floor
                        world.put(new Floor(world),j,i);
                    break;
                    case 2://Barrier
                        world.put(new Barrier(world),j,i);
                    break;
                    case 3://Jar
                        world.put(new Jar(world),j,i);
                    break;
                    default:
                        world.put(new Wall(world), j, i);
                    break;
                }
            }
    }

    public World getWorld() {
        return world;
    }

    public int getLevel() {
        return level;
    }

    public int getCalabashX() {
        return calabash.getX();
    }

    public int getCalabashY() {
        return calabash.getY();
    }

    public Calabash getCalabash() {
        return calabash;
    }

    public LevelController getLevelController() {
        return levelController;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void addVictorSign() {
        int x;
        int y;
        do {
            x = new Random().nextInt(World.WIDTH);
            y = new Random().nextInt(World.HEIGHT);
        } while (!(world.get(x, y) instanceof Floor));
        VictorSign s = new VictorSign(world);
        world.put(s, x, y);
    }

    public void addHeart(int x,int y){
        Heart h=new Heart(world);
        if (!(world.get(x, y) instanceof Creature) && !(world.get(x, y) instanceof Wall)) {
            world.put(h, x, y);
        }

    }
    public boolean addMonster(int type, int x, int y) {
        Monster m;
        switch (type) {
        case 0: {
            m = new Frog(world, this);
            break;
        }
        case 1:{
            m=new Bat(world, this);
            break;
        }
        default:
            m = new Frog(world, this);
        }
        if (!(world.get(x, y) instanceof Creature) && 
            !(world.get(x, y) instanceof Wall) && 
            !(world.get(x, y) instanceof Heart)){
            world.put(m, x, y);
            monsters.add(m);
            gameThread.addMonsterThread(m);
            return true;
        } else
            return false;
    }

    public boolean addBoss(int type, int x, int y) {
        Monster boss;
        switch (type) {
        case 0: {            
            boss = new Snake(world, this);
            break;
        }
        default:
            boss = new Snake(world, this);
        }
        if (!(world.get(x, y) instanceof Creature) && !(world.get(x, y) instanceof Wall)) {
            world.put(boss, x, y);
            monsters.add(boss);
            gameThread.addMonsterThread(boss);
            return true;
        } else
            return false;
    }

    public void deleteMonster(Monster m) {
        monsters.remove(m);
        world.deleteMonster(m, m.getX(), m.getY());
    }

    public void addBullet(Bullet b) {
        if (b instanceof CalabashBullet)
            calabashBullets.add(b);
        else
            monsterBullets.add(b);
    }

    public void deleteBullet(Bullet b) {
        if (b instanceof CalabashBullet)
            deleteCalabashBullets.add(b);
        else
            deleteMonsterBullets.add(b);

    }


    public void PassLevel() {
        winSign = true;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y);

            }
        }
        UIupdate(terminal);
    }

    void UIupdate(AsciiPanel terminal) {
        terminal.write("HP: " + calabash.getHP() + "/" + calabash.getMaxHP(), 36, 0);
        terminal.write("Attack: " + calabash.getAttack(), 36, 1);
        terminal.write("Skill: " + calabash.getSkillCD() + "%", 36, 2);


        terminal.write("LEVEL: "+level,36,25);

        if (BossSign && !monsters.isEmpty()){   //display BOSS HP
            terminal.write("BOSS:",0,27);
            Monster m=monsters.get(0);
            for (int i=0;i<m.getHP()*35/m.getMaxHP();i++)
                terminal.write((char)177,8+i,27,AsciiPanel.white);
        }

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        if (winSign) {
            if (level == TotalLevelNum - 1) {
                winSign = false;
                return new WinScreen();
            } else {
                level++;
                winSign = false;
                return new WorldScreen(level, calabash);
            }
        }
        if (loseSign) {
            return new LoseScreen();
        }
        int x = key.getKeyCode();
        switch (x) {
        case 87: {// up W
            calabash.goUp();
            break;
        }
        case 83: {// down S
            calabash.goDown();
            break;
        }
        case 65: {// left A
            calabash.goLeft();
            break;
        }
        case 68: {// right D
            calabash.goRight();
            break;
        }
        case 38: {// Attack UP ↑
            calabash.attackUp();
            break;
        }
        case 40: {// Attack Down ↓
            calabash.attackDown();
            break;
        }
        case 37: {// Attack Left ←
            calabash.attackLeft();
            break;
        }
        case 39: {// Attack Right →
            calabash.attackRight();
            break;
        }
        case 32: {// Skill SPACE
            calabash.skill();
            break;
        }
        default:
            break;
        }
        return this;
    }


    public void DefeatBoss() {
        defeatBossSign=true;
        for (int i=1;i<monsters.size();i++){
            monsters.get(i).beHit(monsters.get(i).getHP());
        }
        deleteMonster(monsters.get(0));
    }

    public boolean hasDefeatBoss(){
        return defeatBossSign;
    }

    public void Lose() {
        loseSign = true;
    }

    public void Refresh() {
        for (Bullet b : monsterBullets) {
            b.Refresh();
        }
        for (Bullet b : calabashBullets) {
            b.Refresh();
        }
        for (Bullet b : deleteCalabashBullets) {
            calabashBullets.remove(b);
        }
        deleteCalabashBullets.clear();
        for (Bullet b : deleteMonsterBullets) {
            monsterBullets.remove(b);
        }
        deleteMonsterBullets.clear();
    }
}
