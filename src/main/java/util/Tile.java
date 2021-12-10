package util;
import java.util.ArrayList;
import bullet.Bullet;
import creature.Creature;

public class Tile<T extends Thing> {

    private T thing;
    private T item;
    private ArrayList<T>bullets;
    private int xPos;
    private int yPos;

    public T getThing() {   //Creature>Bullet>util
        if (!(thing instanceof Floor))
            return thing;
        else if (item!=null){
            return item;
        }
        else if (!bullets.isEmpty())
            return bullets.get(0);
        else
            return thing;
    }

    public T getItem(){
        return item;
    }

    public void deleteItem(){
        item=null;
    }

    public ArrayList<T> getBullets(){
        return bullets;
    }
    public void setThing(T thing) {
        if (thing instanceof Bullet){
            bullets.add(thing);
            thing.setTile(this);
        }
        else if (thing instanceof Item){
            this.item=thing;
            this.item.setTile(this);
        }
        else{
            this.thing = thing;
            this.thing.setTile(this);
        }
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Tile() {
        this.xPos = -1;
        this.yPos = -1;
        bullets=new ArrayList<T>();
    }

    public Tile(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        bullets=new ArrayList<T>();
    }

}
