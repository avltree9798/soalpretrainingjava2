package classes;

import gameobject.*;
import model.Highscore;
import model.Player;
import model.Score;
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
    public String map[][] = new String[17][56];
    public Highscore hs = new Highscore();
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
                map[i] = line.split(",");
                i++;
            }
            FileInputStream hstream = new FileInputStream("highscore.csv");
            BufferedReader hreader = new BufferedReader(new InputStreamReader(hstream));
            line = null;
            i = 0;
            while((line = hreader.readLine())!=null){
                String tokens[] = line.split(",");
                Score s = new Score(tokens[0], Integer.parseInt(tokens[1]));
                hs.scores.add(s);
            }
            sortHS();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sortHS() {
		// TODO Auto-generated method stub
    	for(int i=0;i<hs.scores.size();i++){
        	for(int j=hs.scores.size()-1;j>0;j--){
        		Score temp;
        		if(hs.scores.get(j).score>hs.scores.get(j-1).score){
        			temp = hs.scores.get(j);
        			hs.scores.set(j, hs.scores.get(j-1));
        			hs.scores.set(j-1, temp);
        		}
        	}
        }
	}
	public Player fetchEnemy(){
        Random rand = new Random();
        switch(rand.nextInt(31)){
            case 30:
                return new Bulbasaur();
            case 5:
                return new Charmender();
            case 26:
                return new Squirtle();
            case 19:
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
