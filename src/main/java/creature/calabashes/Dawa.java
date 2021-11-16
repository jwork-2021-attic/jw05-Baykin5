package creature.calabashes;

import util.World;
import java.awt.Color;
import creature.Calabash;
import screen.WorldScreen;

public class Dawa extends Calabash{

    private boolean buffed;
    private int buffedTime=5*1000;
    private int buffedCount=0;

    public Dawa(World world,WorldScreen worldScreen){
        super(new Color(0, 255, 0),world,worldScreen);
        attack=20;
        maxHP=100;
        HP=100;
        skillCD=20*1000;
        skillCDCount=20*1000;
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
        nowTime=System.currentTimeMillis();
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
