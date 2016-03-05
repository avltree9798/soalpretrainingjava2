package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import classes.DB;
import classes.Helper;

public class Score {
	public String username;
	public int score;
	public Score(String username, int score){
		this.username = username;
		this.score = score;
	}
	public void save() {
		DB db = DB.getInstance();
		db.hs.scores.add(this);
		try {
			PrintWriter pw = null;
			File f = new File("highscore.csv");
			pw = new PrintWriter(new FileOutputStream(f, true));
			pw.write(System.getProperty("line.separator") + this.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return username + "," + score;
	}
}
