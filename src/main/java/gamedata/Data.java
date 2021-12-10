package gamedata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import creature.Calabash;
import util.World;

public class Data {
    int level;
    int raws[][];
    int HP;
    int maxHP;
    int attack;
    int calabashType;

    public Data() {
        level = 0;
        raws = new int[World.HEIGHT][World.WIDTH];
        HP = 0;
        maxHP = 0;
        attack = 0;
        calabashType=1;
    }

    public boolean load(String file) throws IOException {
        File tempFile = new File(file);
        if (tempFile.exists()) {
            FileInputStream in = null;
            int ch;
            try {
                in = new FileInputStream(file);
                ch = in.read();
                if (ch == '1') // 有存档
                {
                    HP = 0;
                    maxHP = 0;
                    attack = 0;
                    ch = in.read();
                    ch = in.read();
                    level = ch - '0';
                    ch = in.read();
                    for (int i = 0; i < World.HEIGHT; i++) {
                        for (int j = 0; j < World.WIDTH; j++) {
                            ch = in.read();
                            raws[i][j] = ch - '0';
                            ch = in.read();
                        }
                        ch = in.read();
                    }
                    
                    ch=in.read();
                    calabashType=ch-'0';
                    ch=in.read();
                    while ((ch = in.read()) != ' ') {
                        HP *= 10;
                        HP += (ch - '0');
                    }
                    while ((ch = in.read()) != ' ') {
                        maxHP *= 10;
                        maxHP += (ch - '0');
                    }
                    while ((ch = in.read()) != ' ' && ch != -1) {
                        attack *= 10;
                        attack += (ch - '0');
                    }
                    return true;
                } else
                    return false;

            } finally {
                if (in != null)
                    in.close();
            }
        } 
        else
            return false;

    }

    public void save(String file) throws IOException {
        FileOutputStream out = null;
        File tempFile=new File(file);
        if (!tempFile.exists()){
            tempFile.createNewFile();
        }
        
        out= new FileOutputStream(file);
        try {
            out.write('1');
            out.write('\n');
            out.write(level + '0');
            out.write('\n');
            for (int i = 0; i < World.HEIGHT; i++) {
                for (int j = 0; j < World.WIDTH; j++) {
                    out.write(raws[i][j] + '0');
                    out.write(' ');
                }
                out.write('\n');
            }
            out.write(calabashType+'0');
            out.write(' ');
            String s;
            s = Integer.toString(HP);
            for (int i = 0; i < s.length(); i++)
                out.write((int) (s.charAt(i)));
            out.write(' ');
            s = Integer.toString(maxHP);
            for (int i = 0; i < s.length(); i++)
                out.write((int) (s.charAt(i)));
            out.write(' ');
            s = Integer.toString(attack);
            for (int i = 0; i < s.length(); i++)
                out.write((int) (s.charAt(i)));
        } finally {
            if (out != null)
                out.close();
        }

    }

    public int getLevel() {
        return level;
    }

    public int[][] getraws() {
        return raws;
    }

    public int getCalabashType(){
        return calabashType;
    }
    public int getHP() {
        return HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void set(int level,int calabashType,int HP,int maxHP,int attack,int raws[][]){
        this.level=level;
        this.calabashType=calabashType;
        this.HP=HP;
        this.maxHP=maxHP;
        this.attack=attack;
        for(int i=0;i<World.HEIGHT;i++){
            for(int j=0;j<World.WIDTH;j++)
                this.raws[i][j]=raws[i][j];
        }
    }

    public void print(){
        System.out.println("level="+level);
        System.out.println("type="+calabashType);
        System.out.println("HP="+HP);
        System.out.println("maxHP="+maxHP);
        System.out.println("attack="+attack);
    }
}
