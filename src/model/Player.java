package model;

/**
 * Created by root on 2/15/16.
 */
public abstract  class Player {
    private int hp;
    private int level;
    private int mp;
    private int str;
    private int agi;
    private String name;
    private int maxHP;
    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getAgi() {
        return agi;
    }

    public void setAgi(int agi) {
        this.agi = agi;
    }

    public abstract void attack(Player enemy);
    public abstract void useItem(Item item);
    public void setHp(int hp){
        this.hp = hp;
    }
    public int getHP() {
        return this.hp;
    }
    public boolean isDead(){
        return this.hp<=0;
    }
}
