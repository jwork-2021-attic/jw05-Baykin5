package creature;

import java.awt.Color;

import screen.WorldScreen;
import util.World;
import util.Floor;
import util.Wall;
import bullet.Bullet;
import bullet.MonsterBullet;


public abstract class Monster extends Creature {
    
    protected int attack;
    protected int HP;
    protected int maxHP;
    protected WorldScreen worldScreen;  //用于获取葫芦娃的位置等等

    public Monster(Color color,char glyph, World world,WorldScreen worldScreen) {
        super(color, glyph, world);
        this.worldScreen=worldScreen;

    }

    @Override
    public void beHit(int attack){
        HP-=attack;
    }


    public synchronized void goUp(){
        int x=this.getX();
        int y=this.getY();
        if (y>0 && (world.get(x, y-1) instanceof Floor)){
            this.moveTo(x,y-1);
        }
    }
    public synchronized void goDown(){
        int x=this.getX();
        int y=this.getY();
        if (y<World.HEIGHT-1 && (world.get(x, y+1) instanceof Floor)){
            this.moveTo(x,y+1);

        }
    }
    public synchronized void goLeft(){
        int x=this.getX();
        int y=this.getY();
        if (x>0 && (world.get(x-1, y) instanceof Floor)){
            this.moveTo(x-1,y);

        }
    }
    public synchronized void goRight(){
        int x=this.getX();
        int y=this.getY();
        if (x<World.WIDTH-1 && (world.get(x+1, y) instanceof Floor)){
            this.moveTo(x+1,y);

        }
    }
    public synchronized void attackUp(){
        int x=getX();
        int y=getY()-1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,0);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackDown(){
        int x=getX();
        int y=getY()+1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,1);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackLeft(){
        int x=getX()-1;
        int y=getY();
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,2);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackRight(){
        int x=getX()+1;
        int y=getY();
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,3);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
        
    }

    public synchronized void attackLeftUp(){
        int x=getX()-1;
        int y=getY()-1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,4);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackRightUp(){
        int x=getX()+1;
        int y=getY()-1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,5);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackRightDown(){
        int x=getX()+1;
        int y=getY()+1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,6);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
    }
    public synchronized void attackLeftDown(){
        int x=getX()-1;
        int y=getY()+1;
        if (!(world.get(x, y) instanceof Wall) ){
            Bullet b=new MonsterBullet(world, worldScreen, attack,7);
            world.put(b,x,y);
            worldScreen.addBullet(b);
        }
        
    }
    public abstract void run();
}
