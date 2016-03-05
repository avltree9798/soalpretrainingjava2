package classes;

import gameobject.Mantra;
import gameobject.Potion;

import java.io.PrintStream;
import java.util.Scanner;

import model.Player;
import model.Score;
import model.User;

/**
 * Created by root on 2/15/16.
 */
public class Main {
    private Player p;
    private DB database;
    private Scanner scan = new Scanner(System.in);
    private PrintStream o = System.out;
    private String map[][];
    int playerX=2;
    int playerY=2;
    final int FINISH_X = 1;
    final int FINISH_Y = 2;
    int score = 0;
    boolean masihMain;
    public void cls(){
        for(int i=0;i<25;i++)o.println();
    }
    public int mainMenu(){
        cls();
        String menus[] = {"Login","Register","Exit"};
        int menu = 0;
        do{
            try{
                for(int i=0;i<menus.length;i++){
                    o.println((i+1)+". "+menus[i]);
                }
                o.print(">> ");
                menu = scan.nextInt();scan.nextLine();
            }catch(Exception e){scan.nextLine();}
        }while(menu<1||menu>3);
        return menu;
    }
    private void login(){
        while(true){
            o.print("Username : ");
            String username = scan.nextLine();
            o.print("Password : ");
            String password = scan.nextLine();
            int index = -1;
            for(int i=0;i<database.alUser.size();i++){
                User u = database.alUser.get(i);
                if(u.username.equals(username) && u.password.equals(password)){
                    index = i;
                    Helper.u = u;
                    break;
                }
            }
            if(index!=-1){
                o.println("Welcome "+Helper.u.fullName+" !");
                scan.nextLine();
                afterLogin();
                break;
            }else{
                String choice;
                do{
                    o.print("Want to try again [ Y | N ] ? ");
                    choice = scan.nextLine();
                }while(!choice.equals("Y") && !choice.equals("N"));
                if(choice.equals("N")){
                    break;
                }
            }
        }
    }
    private void register(){
        o.print("Username : ");
        String username = scan.nextLine();
        o.print("Password : ");
        String password = scan.nextLine();
        o.print("Fullname : ");
        String fullName = scan.nextLine();
        User u = new User(username,password, fullName);
        int count = database.alUser.size();
        u.save();
        if(count!=database.alUser.size()){
            o.print("Regiser Success");
        }else{
            o.print("Register Failed");
        }
        scan.nextLine();
    }
    public void menu(){
        cls();
        o.println("1. Play game");
        o.println("2. Scoreboard");
        o.println("3. My Profile");
        o.println("4. Logout");
    }
    public void playGame(){
        int choose;
        score = 0;
        do{
            try{
                cls();
                o.println("Choose your pokemon");
                o.println("1. Pikachu");
                o.print("2. Kabuto\n>> ");
                choose = scan.nextInt();scan.nextLine();
            }catch(Exception e){
                choose = -1;
                scan.nextLine();
            }
        }while(choose<1||choose>2);
        p = database.getPlayer(choose);
        cls();
        o.println("====================");
        o.println("This is your Pokemon");
        o.println("====================\n");
        o.println("\t"+p.getName());
        o.println("\tLvl : "+p.getLevel());
        o.println("\tHp  : "+p.getHp());
        o.println("\tStr : "+p.getStr());
        o.println("\tAgi : "+p.getAgi()+"\n");
        o.println("Let the game begin");
        o.println("Press ENTER key to continue");
        scan.nextLine();
        game();

    }
    private void printMap(){
        cls();
        System.out.println("Money : "+p.getMoney()+"G | XP : "+p.getXp()+"/"+(p.getLevel()*100)+" | HP : "+p.getHp()+" | Lvl : "+p.getLevel());
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(i==playerX && j ==playerY){
                    o.print("A");
                }else if(i==FINISH_X && j == FINISH_Y){
                    o.print("X");
                }else if(map[i][j].equals("1")){
                    o.print("#");
                }else if(map[i][j].equals("0")){
                    o.print(" ");
                }
            }
            o.println();
        }
    }
    private void game() {
        map = database.map;
        char key;
        playerX=map.length-3;
        playerY=map[0].length-3;
        do{
            printMap();
            String tutors[] = {"[a] to move left","[d] to move right","[w] to move up","[s] to move down","[e] to exit","[o] to open pocket"};
            for(int i=0;i<tutors.length;i++){
            	o.printf("=%-20s ",tutors[i]);
            	if(i%2!=0)o.println("=");
            }
            key = scan.next().charAt(0);
            scan.nextLine();
            move(key);
            if(isWin() || p.isDead()){
            	break;
            }
        }while((key!='e' || !p.isDead()) || !isWin());
        if(isWin()){
        	o.println("You got "+p.getMoney()+" points");
        }else if(p.isDead()){
        	o.println("You Lose");
        }
    }
    private void battle(Player en){
        o.println(en.getName()+" appeared!!!");
        scan.nextLine();
        int input;
        masihMain = true;
        do{
            cls();
            try{
                o.println("ENEMY");
                o.println("Name : "+en.getName());
                o.println("Hp   : "+en.getHp()+"/"+en.getMaxHP()+"\n");
                o.println("YOU");
                o.println("Name : "+p.getName());
                o.println("Hp   : "+p.getHp()+"/"+p.getMaxHP()+"\n\n");
                o.println("======");
                o.println("BATTLE");
                o.println("======");
                o.println("1. Fight");
                o.println("2. Item");
                o.println("3. Run");
                o.print(">> ");
                input = scan.nextInt(); scan.nextLine();
                switch(input){
                case 1:
                	p.attack(en);
                	en.attack(p);
                	scan.nextLine();
                	if(p.isDead()){
                		o.println("You lose");
                		scan.nextLine();
                		masihMain = false;
                	}else if(en.isDead()){
                		o.println("Win!!!");
                		p.setMoney(p.getMoney()+50);
                		o.println("Getting 50G to yout pocket");
                		if(p.setXp(p.getXp()+50)){
                			o.println("Level up to "+p.getLevel());
                			o.println("Hp  : "+p.getHp());
                			o.println("Str : "+p.getStr());
                			p.setMoney(p.getMoney()+100);
                		}
                		masihMain = false;
                		scan.nextLine();
                	} 
                	break;
                case 2:
                	cls();
                	int choose;
                	do {
                		try{
                			o.println("=========================");
                        	o.printf("=%16s %7s\n","My Pocket","=");
                        	o.println("=========================");
                        	System.out.printf("= 1. Potion : %4dpcs   =\n",p.getPotions().size());
                        	System.out.printf("= 2. Mantra : %4dpcs   =\n",p.getMantras().size());
                        	o.println("=========================");
                        	System.out.print(">> ");
                        	choose = scan.nextInt(); scan.nextLine();
                		}catch(Exception e){scan.nextLine(); choose = -1;}
					} while (choose<1 || choose>2);
                	if(choose == 1){
                		if(p.getPotions().size()==0){
                			o.println("Your Potion is 0");
                		}else{
                			p.setHp(p.getMaxHP());
                			o.println("Your HP restored to "+p.getHp());
                			p.getPotions().remove(0);
                		}
                	}else{
                		if(p.getMantras().size()==0){
                			o.println("Your Mantra is 0");
                		}else{
                			p.getMantras().get(0).affectPlayer(en);
                			o.println("The enemy will stunt for 3 rounds");
                			p.getMantras().remove(0);
                		}
                	}
                	en.attack(p);
                	scan.nextLine();
                	break;
                case 3:
                	Score s = new Score(Helper.u.username, (p.getMoney()*p.getLevel()));
                	s.save();
                	database.hs.scores.add(s);
                	masihMain = false;
                	break;
                }
            }catch(Exception e){
                scan.nextLine();
                input = -1;
            }
        }while(input!=4 && masihMain);
    }
    private void move(char key) {
        switch(key){
            case 'a':
                while(!p.isDead()){
                    int oldX=playerX;
                    int oldY=playerY-1;
                    if(map[oldX][oldY].equals("1")){
                        break;
                    }
                    oldY++;
                    playerY--;
                    map[playerX][playerY]=map[oldX][oldY];
                    map[oldX][oldY]="0";
                    printMap(300);
                    if(isWin()){
                    	Score s = new Score(Helper.u.username, p.getMoney()*p.getLevel());
                    	s.save();
                    	database.hs.scores.add(s);
                    	o.println("You won");
                    	scan.nextLine();
                    	masihMain = false;
                        break;
                    }
                    Player enemy = database.fetchEnemy();
                    if(enemy!=null){
                        battle(enemy);
                        break;
                    }
                }
                break;
            case 'd':
                while(!p.isDead()){
                    int oldX=playerX;
                    int oldY=playerY+1;
                    if(map[oldX][oldY].equals("1")){
                        break;
                    }
                    oldY--;
                    playerY++;
                    map[playerX][playerY]=map[oldX][oldY];
                    map[oldX][oldY]="0";
                    printMap(300);
                    if(isWin()){
                    	Score s = new Score(Helper.u.username, p.getMoney()*p.getLevel());
                    	s.save();
                    	database.hs.scores.add(s);
                    	o.println("You won");
                    	scan.nextLine();
                    	masihMain = false;
                        break;
                    }
                    Player enemy = database.fetchEnemy();
                    if(enemy!=null){
                        battle(enemy);
                        break;
                    }
                }
                break;
            case 'w':
                while(!p.isDead()){
                    int oldX=playerX-1;
                    int oldY=playerY;
                    if(map[oldX][oldY].equals("1")){
                        break;
                    }
                    oldX++;
                    playerX--;
                    map[playerX][playerY]=map[oldX][oldY];
                    map[oldX][oldY]="0";
                    printMap(300);
                    if(isWin()){
                    	Score s = new Score(Helper.u.username, p.getMoney()*p.getLevel());
                    	s.save();
                    	database.hs.scores.add(s);
                    	o.println("You won");
                    	scan.nextLine();
                    	masihMain = false;
                        break;
                    }
                    Player enemy = database.fetchEnemy();
                    if(enemy!=null){
                        battle(enemy);
                        break;
                    }
                }
                break;
            case 's':
                while(!p.isDead()){
                    int oldX=playerX+1;
                    int oldY=playerY;
                    if(map[oldX][oldY].equals("1")){
                        break;
                    }
                    oldX--;
                    playerX++;
                    map[playerX][playerY]=map[oldX][oldY];
                    map[oldX][oldY]="0";
                    printMap(300);
                    if(isWin()){
                    	Score s = new Score(Helper.u.username, p.getMoney()*p.getLevel());
                    	s.save();
                    	database.hs.scores.add(s);
                    	o.println("You won");
                    	scan.nextLine();
                    	masihMain = false;
                        break;
                    }
                    Player enemy = database.fetchEnemy();
                    if(enemy!=null){
                        battle(enemy);
                        break;
                    }
                }
                break;
            case 'e':
            	Score s = new Score(Helper.u.username, p.getMoney()*p.getLevel());
            	s.save();
            	database.hs.scores.add(s);
            	masihMain = false;
                break;
            case 'o':
            	backpack();
                break;
            default:
                o.println("Input invalid");
                scan.nextLine();
                break;
        }
    }

    private void backpack() {
		// TODO Auto-generated method stub
		int menu = -1;
    	do {
			try {
				cls();
				o.println("=========================");
            	o.printf("=%16s %7s\n","My Pocket","=");
            	o.println("=========================");
            	System.out.printf("= 1. Potion : %4dpcs   =\n",p.getPotions().size());
            	System.out.printf("= 2. Mantra : %4dpcs   =\n",p.getMantras().size());
            	o.println("=========================");
				o.println("1. Buy new item");
				o.println("2. Back to game");
				o.print(">>");
				menu = scan.nextInt(); scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				menu = -1;
				scan.nextLine();
			}
			if(menu==1){
	    		int choice = -1;
	    		do{
	    			try {
	    				o.println("Which item you want to buy?");
	            		o.println("1. Potion : "+Potion.PRICE+"G");
	            		o.println("2. Mantra : "+Mantra.PRICE+"G");
	            		o.print(">> ");
	            		choice = scan.nextInt(); scan.nextLine();
					} catch (Exception e) {
						// TODO: handle exception
						scan.nextLine();
						choice = -1;
					}
	    		}while(choice<1||choice>2);
	    		if(choice==1){
	    			if(p.getMoney()<Potion.PRICE){
	    				o.println("You dont have enough money");
	    			}else{
	    				p.getPotions().add(new Potion());
	    			}
	    		}else{
	    			if(p.getMoney()<Mantra.PRICE){
	    				o.println("You dont have enough money");
	    			}else{
	    				p.getMantras().add(new Mantra());
	    			}
	    		}
	    	}
		} while (menu!=2);
    	
	}
	private void printMap(int sleep) {
        cls();
        printMap();
        try{Thread.sleep(sleep);}catch(Exception e){}
    }
	
	public void batas(){
		o.printf("---+-");
		for(int j=0;j<25;j++)o.printf("-");
		o.printf("+-");
		for(int j=0;j<10;j++)o.printf("-");
		o.printf("\n");
	}

    public void scoreboard(){
    	cls();
    	database.sortHS();
    	batas();
    	o.printf("%-3s| %-25s| %-5s \n","No.","Username","Score");
    	batas();
    	for(int i=0;i<database.hs.scores.size() && i<10;i++){
    		o.printf("%-3d| %-25s| %-5d pts \n",(i+1),database.hs.scores.get(i).username,database.hs.scores.get(i).score);
    		batas();
    	}
    	scan.nextLine();
    }
    public void myProfile(){
    	cls();
    	o.println("Name     : "+Helper.u.fullName);
    	o.println("Username : "+Helper.u.username);
    	Helper.u.showMyScore();
    	scan.nextLine();
    }
    public void afterLogin(){
        int menu;
        do {
            try {
                menu();
                o.print(">> ");
                menu = scan.nextInt(); scan.nextLine();
            }catch(Exception e){
                menu = -1;
                scan.nextLine();
            }
            switch(menu){
                case 1:
                    playGame();
                    break;
                case 2:
                    scoreboard();
                    break;
                case 3:
                    myProfile();
                    break;
                case 4:

                    break;
            }
        }while(menu!=4);
    }
    public Main(){
        database = DB.getInstance();
        while(true){
            switch(mainMenu()){
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }
    public static void main(String [] args){
        new Main();
    }

    public boolean isWin() {
        boolean status = false;
        if((playerX==FINISH_X)&&(playerY==FINISH_Y)){
            status = true;
        }
        return status;
    }
}
