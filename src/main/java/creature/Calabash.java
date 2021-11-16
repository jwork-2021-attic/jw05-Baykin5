package creature;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import util.World;
import util.Wall;
import util.Floor;
import util.VictorSign;
import bullet.Bullet;
import bullet.CalabashBullet;
import bullet.MonsterBullet;
import screen.WorldScreen;

public abstract class Calabash extends Creature {

    protected int attack;
    protected int HP;
    protected int maxHP;
    protected WorldScreen worldScreen;

    protected long lastTime;
    protected long nowTime;
    protected int skillCD;
    protected int skillCDCount;

    public Calabash(Color color, World world,WorldScreen worldScreen) {
        super(color, (char) 2, world);
        this.worldScreen=worldScreen;
        lastTime=System.currentTimeMillis();
    }


    @Override
    public void beHit(int attack){
        HP-=attack;
    }


    public synchronized void goUp(){
        int x=this.getX();
        int y=this.getY();
        if (world.get(x, y-1) instanceof VictorSign){
            worldScreen.PassLevel();
        }
        if (y>0 && (world.get(x, y-1) instanceof Floor) ||(world.get(x, y-1) instanceof MonsterBullet )){
            this.moveTo(x,y-1);
        }
        
    }

    public synchronized void goDown(){
        int x=this.getX();
        int y=this.getY();
        if (world.get(x, y+1) instanceof VictorSign){
            worldScreen.PassLevel();
        }
        if (y<World.HEIGHT-1 && (world.get(x, y+1) instanceof Floor) ||(world.get(x, y+1) instanceof MonsterBullet )){
            this.moveTo(x,y+1);

        }
    }
    public synchronized void goLeft(){
        int x=this.getX();
        int y=this.getY();
        if (world.get(x-1, y) instanceof VictorSign){
            worldScreen.PassLevel();
        }
        if (x>0 &&(world.get(x-1, y) instanceof Floor) ||(world.get(x-1, y) instanceof MonsterBullet )){
            this.moveTo(x-1,y);
        }
    }
    public synchronized void goRight(){
        int x=this.getX();
        int y=this.getY();
        if (world.get(x+1, y) instanceof VictorSign){
            worldScreen.PassLevel();
        }
        if (x<World.WIDTH-1 && (world.get(x+1, y) instanceof Floor) ||(world.get(x+1, y) instanceof MonsterBullet )){
            this.moveTo(x+1,y);

        }
    }

    public synchronized void attackUp(){
        int x=getX();
        int y=getY()-1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new CalabashBullet(world, worldScreen, attack,0);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackDown(){
        int x=getX();
        int y=getY()+1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new CalabashBullet(world, worldScreen, attack,1);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackLeft(){
        int x=getX()-1;
        int y=getY();
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new CalabashBullet(world, worldScreen, attack,2);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackRight(){
        int x=getX()+1;
        int y=getY();
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new CalabashBullet(world, worldScreen, attack,3);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
        
    }
    public abstract void skill();


    public void run(){
        System.out.println("calabash thread running!");
        try{
            while(true){
            TimeUnit.MILLISECONDS.sleep(5);
            updateStates();
            }
        }
        catch(InterruptedException e){
            System.out.println("Calabash action error!");
        }
        
   }

   public void setWorld(World world,WorldScreen worldScreen){
       this.world=world;
       this.worldScreen=worldScreen;
   }
   public void updateStates(){
   }

   public int getHP(){
       return HP;
   }
   public int getMaxHP(){
        return maxHP;
    }
    public int getAttack(){
        return attack;
    }
    public int getSkillCD(){
        int res=skillCDCount*100/skillCD;
        if (res<=100)
            return res;
        else
            return 100;
    }

}
