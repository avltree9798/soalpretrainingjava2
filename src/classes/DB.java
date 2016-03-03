package classes;

import gameobject.*;
import model.Player;
import model.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by root on 2/15/16.
 */
public class DB {
    private static DB ourInstance = new DB();

    public static DB getInstance() {
        return ourInstance;
    }
    public ArrayList<User> alUser = new ArrayList<User>();
    public String map[][] = new String[19][58];
    private DB() {
        try{
            FileInputStream fstream = new FileInputStream("users.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;
            while((line = br.readLine())!=null){
                String tokens[] = line.split("\\,");
                String username = tokens[0];
                String password = tokens[1];
                String fullName = tokens[2];
                alUser.add(new User(username,password,fullName));
            }
            FileInputStream mapstream = new FileInputStream("map.csv");
            BufferedReader mapreader = new BufferedReader(new InputStreamReader(mapstream));
            line = null;
            int i = 0;
            while((line = mapreader.readLine())!=null){
                map[i++] = line.split(",");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Player fetchEnemy(){
        Random rand = new Random();
        switch(rand.nextInt(20)){
            case 3:
                return new Bulbasaur();
            case 5:
                return new Charmender();
            case 6:
                return new Squirtle();
            case 9:
                return null;
        }
        return null;
    }
    public Player getPlayer(int hero){
        switch(hero){
            case Helper.PIKACHU:
                return new Pikachu();
            case Helper.KABUTO:
                return new Kabuto();
        }
        return null;
    }
}
