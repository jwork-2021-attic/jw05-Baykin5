package util;

import java.awt.Color;
import java.util.Random;


public class Jar extends Thing {
    boolean treasureSign=false;
    int treasureType=0;

    long seed;
    Random r;

    public Jar(World world,long seed){
        super( (char) 11, world);
        this.seed=seed;
        r=new Random(seed);
        int s=r.nextInt(1);
        if (s==0){
            treasureSign=true;
            int t=r.nextInt(20);
            if (t<2 && t>=0){
                treasureType=1;  //sword
            }
            else if (t<=4 && t>=2){
                treasureType=2;  //heart cystal
            }

            else{
                treasureType=0;  //heart
            }
        }
    }

    public void beDeleted(){
        world.put(new Floor(world),getX(),getY());
        if (treasureSign){
            switch(treasureType){
                case 1:
                    world.put(new Sword(world),getX(),getY());
                    break;
                case 2:
                    world.put(new HeartCrystal(world),getX(),getY());
                    break;
                default:
                    world.put(new Heart(world),getX(),getY());
                    break;
            }
        }
    }
    
}
