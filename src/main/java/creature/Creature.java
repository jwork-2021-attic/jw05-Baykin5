package creature;

import java.awt.Color;


import util.Thing;
import util.World;
import util.Floor;

public abstract class Creature extends Thing implements Runnable{

    public Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        int x=getX();
        int y=getY();
        this.world.put(this, xPos, yPos);
        world.put(new Floor(world),x,y);

    }



    public abstract void run();

    public synchronized void goUp(){

    }
    public synchronized void goDown(){

    }
    public synchronized void goLeft(){

    }
    public synchronized void goRight(){
        
    }
}
