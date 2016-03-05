package gameobject;

import java.util.Random;

import model.Item;
import model.Player;

/**
 * Created by root on 3/1/16.
 */
public class Pikachu extends Player
{	
    public Pikachu(){
        setName("Pikachu");
        setLevel(1);
        setHp(87);
        setMaxHP(87);
        setStr(40);
        setAgi(27);
    }
    @Override
    public void attack(Player enemy) {
    	if(getSleep()>0){
    		setSleep(getSleep()-1);
    		return;
    	}else{
    		Random rand = new Random();
        	int attck = rand.nextInt(getLevel()*2);
        	enemy.setHp(enemy.getHp()-(getStr()+attck));
        	System.out.println(getName()+" attacks!!!");
        	System.out.println("Do "+(getStr()+attck)+" dmg to "+enemy.getName());
    	}
    	
    }

    @Override
    public void useItem(Item item) {

    }
}
