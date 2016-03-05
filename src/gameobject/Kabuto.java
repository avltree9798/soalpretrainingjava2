package gameobject;

import java.util.Random;

import model.Item;
import model.Player;

/**
 * Created by root on 3/1/16.
 */
public class Kabuto extends Player {
	public Kabuto() {
		setName("Kabuto");
		setLevel(1);
		setHp(100);
		setMaxHP(100);
		setStr(30);
		setAgi(27);
		this.getMantras().add(new Mantra());
		this.setMoney(5000);
	}

	@Override
	public void attack(Player enemy) {
		if (getSleep() > 0) {
			setSleep(getSleep() - 1);
			return;
		} else {
			Random rand = new Random();
			int attck = rand.nextInt(getLevel());
			enemy.setHp(enemy.getHp() - (getStr() + attck));
			System.out.println(getName() + " attacks!!!");
			System.out.println("Do " + (getStr() + attck) + " dmg to "
					+ enemy.getName());
		}
	}

	@Override
	public void useItem(Item item) {

	}
}
