package gameobject;

import model.Item;
import model.Player;

/**
 * Created by root on 3/1/16.
 */
public class Squirtle extends Player {
	public Squirtle() {
		setName("Squirtle");
		setHp(50);
		setMaxHP(50);
		setStr(30);
	}

	@Override
	public void attack(Player enemy) {
		if (getSleep() > 0) {
			setSleep(getSleep() - 1);
			return;
		} else {
			enemy.setHp(enemy.getHp() - getStr());
			System.out.println(getName() + " attack you by " + getStr()
					+ " dmg");
		}
	}

	@Override
	public void useItem(Item item) {

	}
}
