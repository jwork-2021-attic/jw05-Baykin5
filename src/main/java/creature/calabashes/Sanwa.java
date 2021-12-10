package creature.calabashes;

import util.Thing;
import util.Heart;
import util.Sword;
import util.World;
import java.awt.Color;
import creature.Calabash;
import screen.WorldScreen;

public class Sanwa extends Calabash {
    
    private boolean buffed;
    private int buffedTime=5*1000;
    private int buffedCount=0;

    public Sanwa(World world,WorldScreen worldScreen){
        super(world,worldScreen);
        attack=20;
        maxHP=100;
        HP=maxHP;
        skillCD=20*1000;
        skillCDCount=skillCD;
        buffed=false;
    }

    public void skill(){
        if (skillCDCount>=skillCD){
            buffed=true;
            skillCDCount=0;
            if (HP<maxHP*0.3){
                heal((maxHP-HP)/2);
            }
        }
    }


    @Override
    public void beHit(int attack){
        if (buffed){
            HP-=attack/2;
        }
        else
            HP-=attack;
    }

    @Override
    public void updateStates(){
        if (world.getItem(getX(), getY())!=null){
            Thing t= world.getItem(getX(), getY());
            if (t instanceof Heart){
                heal(20);
            }
            else if (t instanceof Sword){
                attack+=10;
            }
            world.deleteItem(getX(),getY());
        }

        if (buffed){
            buffedCount+=nowTime-lastTime;
            if (buffedCount>=buffedTime){
                buffed=false;
                buffedCount=0;
            }
        }
        if (skillCDCount<skillCD){
            skillCDCount+=(nowTime-lastTime);
        }
        lastTime=nowTime;

    }
}
