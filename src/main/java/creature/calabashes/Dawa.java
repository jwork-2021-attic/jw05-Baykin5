package creature.calabashes;

import util.Heart;
import util.Sword;
import util.Thing;
import util.World;
import java.awt.Color;
import creature.Calabash;
import screen.WorldScreen;

public class Dawa extends Calabash{

    private boolean buffed;
    private int buffedTime=5*1000;
    private int buffedCount=0;

    public Dawa(World world,WorldScreen worldScreen){
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
            attack+=20;
            buffed=true;
            skillCDCount=0;
        }
    }

    @Override
    public void updateStates(){

        if (buffed){
            buffedCount+=nowTime-lastTime;
            if (buffedCount>=buffedTime){
                buffed=false;
                buffedCount=0;
                attack-=20;
            }
        }
        if (skillCDCount<skillCD){
            skillCDCount+=(nowTime-lastTime);
        }
        lastTime=nowTime;
    }
}
