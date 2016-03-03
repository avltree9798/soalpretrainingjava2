package gameobject;

import model.Item;
import model.Player;

/**
 * Created by root on 3/1/16.
 */
public class Charmender extends Player{
    public Charmender(){
        setName("Charmender");
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
