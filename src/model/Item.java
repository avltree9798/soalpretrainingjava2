package model;

/**
 * Created by root on 2/15/16.
 */
public abstract class Item {
    int kind;
    int effect;
    public Item(int kind, int effect){
        this.kind = kind;
        this.effect = effect;
    }
    public int getEffect(){
        return this.effect;
    }
}
