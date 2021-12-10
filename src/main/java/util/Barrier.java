package util;

import java.awt.Color;


public class Barrier extends Thing {
    boolean treasureSign=false;
    int treasureType=0;

    public Barrier(World world){
        super((char) 13, world);
    }
    
}
