package creature.calabashes;

import java.util.ArrayList;
import util.Thing;
import util.Heart;
import util.Sword;
import util.World;
import creature.Calabash;
import screen.WorldScreen;
import creature.Monster;

public class Erwa extends Calabash {

    private int skillAttack = 50;

    public Erwa(World world, WorldScreen worldScreen) {
        super((char)17, world, worldScreen);
        attack = 20;
        maxHP = 100;
        HP = maxHP;
        skillCD = 3 * 1000;
        skillCDCount = skillCD;
    }

    public void skill() {
        if (skillCDCount >= skillCD) {
            skillCDCount = 0;
            ArrayList<Monster> monsters = worldScreen.getMonsters();
            int min = 0;
            int minDis = 60 ^ 2 + 40 ^ 2;
            if (!monsters.isEmpty())
                for (int i = 0; i < monsters.size(); i++) {
                    if (((monsters.get(i).getX() - getX()) ^ 2 + (monsters.get(i).getY() - getY()) ^ 2) < minDis) {
                        min = i;
                    }
                }
            monsters.get(min).beHit(skillAttack);
        }
    }

    @Override
    public void updateStates() {

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

        if (skillCDCount < skillCD) {
            skillCDCount += (nowTime - lastTime);
        }
        lastTime = nowTime;

    }

}
