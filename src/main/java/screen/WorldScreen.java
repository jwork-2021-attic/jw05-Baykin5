package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import network.NIOServer;
import network.NIOClient;
import threadController.GameThread;
import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    static final int MapTypeNum = 4;
    static final int TotalLevelNum = 3;

    private World world;
    int[][] raws;
    int calabashType;
    Calabash calabash; // 1p
    Calabash calabash2; // 2p
    Calabash calabash3; // 3p

    public ArrayList<Monster> monsters;
    ArrayList<Bullet> calabashBullets;
    ArrayList<Bullet> monsterBullets;
    ArrayList<Bullet> deleteCalabashBullets;
    ArrayList<Bullet> deleteMonsterBullets;
    LevelController levelController;
    public GameThread gameThread;

    boolean GameRunning = false;
    int level;
    boolean winSign = false;
    boolean loseSign = false;

    private int MapHeight;
    private int MapWidth;

    private boolean BossSign = false;
    private boolean defeatBossSign = false;

    /* MultiGame */
    boolean MultiGameSign = false;
    boolean ServerSign = false;
    boolean ClientSign = false;
    NIOServer nioServer;
    NIOClient nioClient;
    int playerID;

    long seed;
    Random r;

    int id=0;

    public WorldScreen(int calabashType) throws IOException { // 单人模式 新游戏
        playerID = 1;
        seed = new Random().nextInt(55555);
        r = new Random(seed);
        world = new World();
        level = 0;
        this.calabashType = calabashType;
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

        MapGenerator mapGenerator = new MapGenerator(seed);
        int t = r.nextInt(MapTypeNum);
        mapGenerator.GenarateMap(t);
        raws = mapGenerator.getMap();
        CreateMap(raws);

        world.put(calabash, 1, 20);
        levelController = new LevelController(this, BossSign, seed);
        gameThread = new GameThread(this);
        gameThread.start();

        Data data = new Data();
        data.set(level, calabashType, calabash.getHP(), calabash.getMaxHP(), calabash.getAttack(), raws);
        data.save("SaveData.txt");

    }

    public WorldScreen(Data data) { // 单人模式 继续上次游戏
        playerID = 1;
        r = new Random(seed);
        data.print();
        world = new World();
        this.level = data.getLevel();
        if (level == 2)
            BossSign = true;
        calabashType = data.getCalabashType();
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
        raws = data.getraws();
        CreateMap(raws);

        monsters = new ArrayList<Monster>();
        calabashBullets = new ArrayList<Bullet>();
        monsterBullets = new ArrayList<Bullet>();
        deleteCalabashBullets = new ArrayList<Bullet>();
        deleteMonsterBullets = new ArrayList<Bullet>();

        calabash.setWorld(world, this);
        world.put(this.calabash, 1, 20);
        levelController = new LevelController(this, BossSign, seed);
        gameThread = new GameThread(this);
        gameThread.start();
    }

    class TempRunnable implements Runnable {
        NIOClient client;

        public TempRunnable(NIOClient client) {
            this.client = client;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(33);
                    client.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public WorldScreen(String s) throws IOException, InterruptedException { // 多人模式
        MultiGameSign = true;

        if (s.equals("Client1"))
            playerID = 1;
        else if (s.equals("Client2"))
            playerID = 2;
        else if (s.equals("Client3"))
            playerID = 3;
        ClientSign = true;
        nioClient = new NIOClient(this, playerID);
        nioClient.startClient();
        TempRunnable tempRunnable = new TempRunnable(nioClient); // 新开一个线程 时刻监听server发送的消息并刷新worldscreen信息
        new Thread(tempRunnable).start();
        seed = nioClient.getSeed();
        r = new Random(seed);
        world = new World();
        level = 0;
        calabash = new Dawa(world, this);
        calabash2 = new Erwa(world, this);
        calabash3 = new Sanwa(world, this);

        monsters = new ArrayList<Monster>();
        calabashBullets = new ArrayList<Bullet>();
        monsterBullets = new ArrayList<Bullet>();
        deleteCalabashBullets = new ArrayList<Bullet>();
        deleteMonsterBullets = new ArrayList<Bullet>();

        MapGenerator mapGenerator = new MapGenerator(seed);
        int t = r.nextInt(MapTypeNum);
        mapGenerator.GenarateMap(t);
        raws = mapGenerator.getMap();
        CreateMap(raws);

        world.put(calabash, 1, 20);
        world.put(calabash2, 33, 20);
        world.put(calabash3, 15, 23);
        levelController = new LevelController(this, BossSign, seed);
        gameThread = new GameThread(this);
        // gameThread.start();
    }

    void SingleGameWin() throws IOException {
        seed = new Random().nextInt(55555); // 下一关，生成一个新的种子
        level++;
        winSign = false;
        int maptype;
        if (level == 2) {
            BossSign = true;
            maptype = 3;
        } else
            maptype = r.nextInt(MapTypeNum);

        
        MapGenerator mapGenerator = new MapGenerator(seed);
        mapGenerator.GenarateMap(maptype);
        raws = mapGenerator.getMap();
        CreateMap(raws);
        clearItems();
        calabash.setWorld(world, this);
        world.put(this.calabash, 1, 20);

        levelController.reset();
        gameThread.shutdown();
        gameThread = new GameThread(this);
        gameThread.start();

        Data data = new Data();
        data.set(level, calabashType, calabash.getHP(), calabash.getMaxHP(), calabash.getAttack(), raws);
        data.save("SaveData.txt");
    }

    void MultiGameWin() throws IOException, InterruptedException {
        long last = seed;
        level++;
        winSign = false;
        int maptype;
        if (calabash.isAlive()) {
            if (playerID == 1)
                nioClient.AskForSeed();
        } else if (calabash2.isAlive()) {
            if (playerID == 2)
                nioClient.AskForSeed();
        } else if (calabash3.isAlive()) {
            if (playerID == 3)
                nioClient.AskForSeed();
        }
        while (last == seed) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        if (level == 2) {
            BossSign = true;
            maptype = 3;
        } else
            maptype = r.nextInt(MapTypeNum);

        MapGenerator mapGenerator = new MapGenerator(seed);
        mapGenerator.GenarateMap(maptype);
        raws = mapGenerator.getMap();
        CreateMap(raws);
        clearItems();

        if (calabash.isAlive()) {
            calabash.setWorld(world, this);
            world.put(calabash, 1, 20);
        }
        if (calabash2.isAlive()) {
            calabash2.setWorld(world, this);
            world.put(calabash2, 33, 20);
        }
        if (calabash3.isAlive()) {
            calabash3.setWorld(world, this);
            world.put(calabash3, 15, 23);
        }

        levelController.reset();
        gameThread.shutdown();
        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void CreateMap(int raws[][]) {
        MapHeight = World.HEIGHT;
        MapWidth = World.WIDTH;
        for (int i = 0; i < MapHeight; i++)
            for (int j = 0; j < MapWidth; j++) {
                switch (raws[i][j]) {
                    case 1: // Floor
                        world.put(new Floor(world), j, i);
                        break;
                    case 2:// Barrier
                        world.put(new Barrier(world), j, i);
                        break;
                    case 3:// Jar
                        world.put(new Jar(world, r.nextInt(55555)), j, i);
                        break;
                    default:
                        world.put(new Wall(world), j, i);
                        break;
                }
            }
    }

    public void clearItems(){
        for (int i=0;i<World.HEIGHT;i++){
            for (int j=0;j<World.WIDTH;j++){
                world.deleteItem(j, i);
            }
        }
    }

    public boolean getBossSign() {
        return BossSign;
    }

    public long getSeed() {
        return seed;
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

    public Calabash getCalabash2() {
        return calabash2;
    }

    public Calabash getCalabash3() {
        return calabash3;
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
            x = r.nextInt(World.WIDTH);
            y = r.nextInt(World.HEIGHT);
        } while (!(world.get(x, y) instanceof Floor));
        VictorSign s = new VictorSign(world);
        world.put(s, x, y);
    }

    public void addHeart(int x, int y) {
        Heart h = new Heart(world);
        if (!(world.get(x, y) instanceof Creature) && !(world.get(x, y) instanceof Wall)) {
            world.put(h, x, y);
        }

    }

    public boolean addMonster(int type, int x, int y) {
        Monster m;
        switch (type) {
            case 0: {
                m = new Frog(world, this, seed);
                break;
            }
            case 1: {
                m = new Bat(world, this, seed);
                break;
            }
            default:
                m = new Frog(world, this, seed);
        }
        if (world.get(x, y) instanceof Floor) {
            world.put(m, x, y);
            monsters.add(m);
            gameThread.addMonsterThread(m);
            id++;
            return true;
        } else
            return false;
    }

    public boolean addBoss(int type, int x, int y) {
        Monster boss;
        switch (type) {
            case 0: {
                boss = new Snake(world, this, seed);
                break;
            }
            default:
                boss = new Snake(world, this, seed);
        }
        if (!(world.get(x, y) instanceof Creature) && !(world.get(x, y) instanceof Wall)) {
            world.put(boss, x, y);
            monsters.add(boss);
            gameThread.addMonsterThread(boss);
            id++;
            return true;
        } else
            return false;
    }

    public void deleteCalabash(Calabash c) {
        world.deleteCalabash(c, c.getX(), c.getY());
        switch (playerID) {
            case 1:
                if (calabash == c)
                    Lose();
                break;
            case 2:
                if (calabash2 == c)
                    Lose();
                break;
            case 3:
                if (calabash3 == c)
                    Lose();
                break;
        }
    }

    public void deleteMonster(Monster m) {
        gameThread.deleteMonster(m);
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

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void PassLevel() {
        winSign = true;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        if (ServerSign) {
            terminal.write("Server Running...", 0, 0);
        } else {
            for (int x = 0; x < World.WIDTH; x++) {
                for (int y = 0; y < World.HEIGHT; y++) {

                    terminal.write(world.get(x, y).getGlyph(), x, y);

                }
            }
            UIupdate(terminal);
        }
    }

    void UIupdate(AsciiPanel terminal) {
        terminal.write("1P", 35, 0);
        terminal.write("HP: " + calabash.getHP() + "/" + calabash.getMaxHP(), 36, 1);
        terminal.write("Attack: " + calabash.getAttack(), 36, 2);
        terminal.write("Skill: " + calabash.getSkillCD() + "%", 36, 3);

        if (MultiGameSign) {
            terminal.write("2P", 35, 5);
            terminal.write("HP: " + calabash2.getHP() + "/" + calabash2.getMaxHP(), 36, 6);
            terminal.write("Attack: " + calabash2.getAttack(), 36, 7);
            terminal.write("Skill: " + calabash2.getSkillCD() + "%", 36, 8);
            terminal.write("3P", 35, 10);
            terminal.write("HP: " + calabash3.getHP() + "/" + calabash3.getMaxHP(), 36, 11);
            terminal.write("Attack: " + calabash3.getAttack(), 36, 12);
            terminal.write("Skill: " + calabash3.getSkillCD() + "%", 36, 13);
        }

        terminal.write("LEVEL: " + level, 35, 24);

        if (BossSign && !monsters.isEmpty()) { // display BOSS HP
            terminal.write("BOSS:", 0, 25);
            Monster m = monsters.get(0);
            for (int i = 0; i < m.getHP() * 35 / m.getMaxHP(); i++)
                terminal.write((char) 177, 8 + i, 25);
        }

    }

    public void playerAction(int id, int keyCode) {
        Calabash temp;
        switch (id) {
            case 1:
                temp = calabash;
                break;
            case 2:
                temp = calabash2;
                break;
            default:
                temp = calabash3;
                break;
        }

        switch (keyCode) {
            case 87: {// up W
                temp.goUp();
                break;
            }
            case 83: {// down S
                temp.goDown();
                break;
            }
            case 65: {// left A
                temp.goLeft();
                break;
            }
            case 68: {// right D
                temp.goRight();
                break;
            }
            case 38: {// Attack UP ↑
                temp.attackUp();
                break;
            }
            case 40: {// Attack Down ↓
                temp.attackDown();
                break;
            }
            case 37: {// Attack Left ←
                temp.attackLeft();
                break;
            }
            case 39: {// Attack Right →
                temp.attackRight();
                break;
            }
            case 32: {// Skill SPACE
                temp.skill();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        if (!MultiGameSign) {
            if (winSign) {
                if (level == TotalLevelNum - 1) {
                    winSign = false;
                    Data data= new Data();
                    data.clear("SaveData.txt");
                    return new WinScreen();
                } else {
                    SingleGameWin();
                    return this;
                }
            }
            if (loseSign) {
                return new LoseScreen();
            }
            int code = key.getKeyCode();
            playerAction(1, code);
            return this;
        } else {
            if (winSign) {
                if (level == TotalLevelNum - 1) {
                    winSign = false;
                    return new WinScreen();
                } else {
                    try {
                        MultiGameWin();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return this;
                }
            }
            if (loseSign) {
                return new LoseScreen();
            }

            int code = key.getKeyCode();
            nioClient.Action(code);
            return this;
        }
    }

    public void StartGame() {
        if (GameRunning == false) {
            gameThread.start();
            GameRunning = true;
        }
    }

    public void DefeatBoss() {
        defeatBossSign = true;
        for (int i = 1; i < monsters.size(); i++) {
            monsters.get(i).beHit(monsters.get(i).getHP());
        }
        deleteMonster(monsters.get(0));
    }

    public boolean hasDefeatBoss() {
        return defeatBossSign;
    }

    public void Lose() {
        loseSign = true;
    }

    public void Refresh() {
        for (int i = 0; i < monsterBullets.size(); i++) {
            monsterBullets.get(i).Refresh();
        }
        for (int i = 0; i < calabashBullets.size(); i++) {
            calabashBullets.get(i).Refresh();
        }
        for (Bullet b : deleteCalabashBullets) {
            calabashBullets.remove(b);
        }
        deleteCalabashBullets.clear();
        for (Bullet b : deleteMonsterBullets) {
            monsterBullets.remove(b);
        }
        deleteMonsterBullets.clear();
        if (winSign && level != 2) {
            if (MultiGameSign)
                try {
                    MultiGameWin();
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            else
                try {
                    SingleGameWin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
