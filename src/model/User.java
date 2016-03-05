package model;

import classes.DB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by root on 2/15/16.
 */
public class User {
	public String username;
	public String password;
	public String fullName;
	public User(String username, String password, String fullName) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
	}

	public void save() {
		DB db = DB.getInstance();
		int index = -1;
		for (int i = 0; i < db.alUser.size(); i++) {
			User u = db.alUser.get(i);
			if (u.username.equals(this.username)) {
				index = i;
			}
		}
		if (index == -1) {
			db.alUser.add(this);
			try {
				PrintWriter pw = null;
				File f = new File("users.csv");
				pw = new PrintWriter(new FileOutputStream(f, true));
				pw.write(System.getProperty("line.separator") + this.toString());
				pw.flush();
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Username already taken");
		}
	}
	public void showMyScore(){
		Highscore hs = DB.getInstance().hs;
		
		System.out.println("My Score");
		
		System.out.printf("----+-------------\n");
		System.out.printf("No. | Score\n");
		System.out.printf("----+-------------\n");
		for(int i=0;i<hs.scores.size() && i<10;i++){
			Score s= hs.scores.get(i);
			
			if(s.username.equals(this.username)){
				System.out.printf("%-3d | %-8d pts\n",i+1,s.score);
				System.out.printf("----+-------------\n");
			}
		}
	}
	public String toString() {
		return username + "," + password + "," + fullName;
	}
}
