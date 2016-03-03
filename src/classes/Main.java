package classes;

import model.Player;
import model.User;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by root on 2/15/16.
 */
public class Main {
    private Player p;
    private DB database;
    private Scanner scan = new Scanner(System.in);
    private PrintStream o = System.out;
    private String map[][];
    int playerX=17;
    int playerY=54;
    final int FINISH_X = 1;
    final int FINISH_Y = 2;
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
                o.print(">>");
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
        o.println("5. Exit");
    }
    public void playGame(){
        int choose;
        do{
            try{
                cls();
                o.println("Choose your pokemon");
                o.println("1. Pikachu");
                o.println("2. Kabuto");
                choose = scan.nextInt();scan.nextLine();
            }catch(Exception e){
                choose = -1;
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
        o.println("\tMp  : "+p.getMp());
        o.println("\tStr : "+p.getStr());
        o.println("\tAgi : "+p.getAgi()+"\n");
        o.println("Let the game begin");
        o.println("Press ENTER key to continue");
        scan.nextLine();
        game();

    }
    private void printMap(){
        cls();
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
        playerX=17;
        playerY=54;
        do{
            printMap();
            o.print("Press [a] to move left, [d] to move right, \n[w] to move up, [s] to move down, \n[e] to exit, [b] to buy item\n>> ");
            key = scan.nextLine().charAt(0);
            move(key);
        }while(key!='e');
    }
    private void battle(Player en){
        o.println(en.getName()+" appeared!!!");
        scan.nextLine();
        cls();
        int input;
        do{
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
                o.println("2. Skills");
                o.println("3. Item");
                o.println("4. Run");
                o.print(">> ");
                input = scan.nextInt(); scan.nextLine();
            }catch(Exception e){
                scan.nextLine();
                input = -1;
            }
        }while(input!=4);
    }
    private void move(char key) {
        switch(key){
            case 'a':
                while(true){
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
                while(true){
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
                while(true){
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
                while(true){
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

                break;
            case 'b':

                break;
            default:
                o.println("Input invalid");
                scan.nextLine();
                break;
        }
    }

    private void printMap(int sleep) {
        cls();
        printMap();
        try{Thread.sleep(sleep);}catch(Exception e){}
    }

    public void scoreboard(){

    }
    public void myProfile(){

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
        }while(menu!=5);
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
