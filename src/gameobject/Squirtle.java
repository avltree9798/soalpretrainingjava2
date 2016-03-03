package gameobject;

import model.Item;
import model.Player;

/**
 * Created by root on 3/1/16.
 */
public class Squirtle extends Player {
    public Squirtle(){
        setName("Squirtle");
        setHp(50);
        setMaxHP(50);
    }
    @Override
    public void attack(Player enemy) {

    }

    @Override
    public void useItem(Item item) {

    }
}
