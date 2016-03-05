package model;

import gameobject.Mantra;
import gameobject.Potion;

import java.util.ArrayList;

/**
 * Created by root on 2/15/16.
 */
public abstract  class Player {
    private int hp;
    private int level;
    private int str;
    private int agi;
    private String name;
    private int maxHP;
    private int xp;
    private int maxXp;
    private int money;
    private int sleep=0;
    private ArrayList<Potion> potions = new ArrayList<Potion>();
    private ArrayList<Mantra> mantras = new ArrayList<Mantra>();
    public ArrayList<Potion> getPotions() {
		return potions;
	}

	public void setPotions(ArrayList<Potion> potions) {
		this.potions = potions;
	}

	public ArrayList<Mantra> getMantras() {
		return mantras;
	}

	public void setMantras(ArrayList<Mantra> mantras) {
		this.mantras = mantras;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getXp() {
		return xp;
	}

	public boolean setXp(int xp) {
		this.xp = xp;
		if(this.maxXp==0)maxXp = getLevel()*100;
		if(this.xp>=this.maxXp){
			setLevel(getLevel()+1);
			xp = 0;
			setMaxXp(getLevel()*100);
			setMaxHP(getMaxHP()+(getLevel()*20));
			setHp(getMaxHP());
			setStr(getStr()+getLevel()*2);
			return true;
		}
		return false;
	}

	public int getMaxXp() {
		return maxXp;
	}

	public void setMaxXp(int maxXp) {
		this.maxXp = maxXp;
	}

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
