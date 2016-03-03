package model;

import classes.DB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by root on 2/15/16.
 */
public class User {
    public String username;
    public String password;
    public String fullName;
    public User(String username, String password, String fullName){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
    public void save(){
        DB db = DB.getInstance();
        db.alUser.add(this);
        try{
            PrintWriter pw = null;
            File f = new File("users.csv");
            pw = new PrintWriter(new FileOutputStream(f,true));
            pw.write(System.getProperty("line.separator")+this.toString());
            pw.flush();
            pw.close();
        }catch(Exception e){e.printStackTrace();}
    }
    public String toString(){
        return username+","+password+","+fullName;
    }
}
