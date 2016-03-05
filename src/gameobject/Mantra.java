package gameobject;

import model.Item;
import model.Player;
import classes.Helper;

public class Mantra extends Item{

	public static final int PRICE = 50;
	public Mantra() {
		super(Helper.MANTRA, 3);
		// TODO Auto-generated constructor stub
	}
	
	public void affectPlayer(Player p){
		p.setSleep(getEffect());
	}

}
