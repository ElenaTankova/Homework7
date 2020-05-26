package Homework7;


import java.util.Scanner;

public class Mines{

public static void main(String[] args){
        GameRunner.init();
        GameRunner.test();
        }

    public static class Game{
        public String[][] field = new String[8][8];
        public String[][] display = new String[8][8];
        public Boolean isDone = false;
        public Boolean isWin = false;

        private String unknown = " x ";
        private String mine = " mine ";
        private String empty = " v  ";


        public Game(){
            int row = 0;
            int column =0;

            for(int n = 0; n < field.length; n++){
                for(int y = 0; y < field[0].length; y++){

                    if((n == 0 || n == field.length - 1)||(y == 0 || y == field[0].length - 1)){
                        field[n][y] = empty;
                        display[n][y] = empty;
                    }

                    else{
                        field[n][y] = unknown;
                        display[n][y] = unknown;
                    }
                }
            }
        }


        public static void printGame(String[][] str){
            for(int n = 1; n < str.length - 1; n++){
                for(int y = 0; y < str[0].length ; y++){

                    if(y > 0 && y < str[0].length)
                        System.out.print("|");
                    else
                        System.out.println("");

                    System.out.print(str[n][y]);
                }
            }
        }


        public void update(){
            printGame(display);
            System.out.println("");
        }


        public void generateMines(int x){
            for(int m = 0; m < x; m++){

                while(true){
                    int n, y = 0;
                    n = (int)(6*Math.random());
                    y = (int)(6*Math.random());


                    if(n >= 1 && n <= 8){
                        if(y >= 1 && y <= 8){

                            if(!field[n][y].equals(mine)){
                                field[n][y] = mine;
                                break;
                            }
                        }
                    }
                }
            }
        }


        public void clear(int n, int y){
            for(int i = (n - 1); i <= (n + 1); i++){
                for(int j = (y - 1); j <= (y + 1); j++){
                    if(field[i][j].equals(unknown) == true){
                        display[i][j] = empty;
                        field[i][j] = empty;
                    }
                }
            }
        }


        public String getTile(int n, int y){
            return field[n][y];
        }


        public void detect(){
            for(int n = 1; n < display.length - 2; n++){
                for(int y = 1; y < display.length - 2; y++){
                    if(field[n][y].equals(empty) == true){
                        int nums = 0;
                        for(int i = (n - 1); i <= (n + 1); i++){
                            for(int j = (y - 1); j <= (y + 1); j++){
                                if(field[i][j].equals(mine) == true)
                                    nums++;
                            }
                        }
                        display[n][y] = " " + nums + " ";
                    }
                }
            }
        }

        public void turn(int n, int y){
            if(field[n][y].equals(unknown) == true){
                isDone = false;
                display[n][y] = empty;
                field[n][y] = empty;
            }else if(field[n][y].equals(mine) == true){
                isDone = true;
                isWin = false;
                System.out.println("You've lost!");
            }else if(display[n][y].equals(empty) == true && field[n][y].equals(empty)){
                isDone = false;
                System.out.println("This tile's been cleared!");
            }
        }


        public void isVictory(){
            int tile = 0;
            for(int i = 0; i < field.length; i++){
                for(int j = 0; j < field[0].length; j++){
                    if(field[i][j].equals(unknown) == true)
                        tile++;
                }
            }
            if(tile != 0)
                isWin = false;
            else{
                isWin = true;
                isDone = true;
            }
        }


        public Boolean getDone(){
            return isDone;
        }


        public Boolean getWin(){
            return isWin;
        }


        public void onEnd(){
            printGame(field);
        }

    }

    public static class GameRunner{


        public static void init(){
            System.out.println("The object of the game is to clear the field of all safe tiles.");
            System.out.println("Play by entering the coordinates of a tile you'd like to select.");
            System.out.println("The range of the tiles is 1-6. The origin is the upper left tile.");
            System.out.println("There are 5 mines. Selecting a tile with a mine will end the game.");
        }

        public static void test(){
            Game game = new Game();
            game.generateMines(5);
            game.update();
            Scanner scan = new Scanner(System.in);

            int n, y;
            System.out.print("Enter an x coordinate.");
            n = scan.nextInt();
            System.out.print("Enter a y coordinate.");
            y = scan.nextInt();



            if(game.getTile(n,y).equals(" mine ") == true){
                game.generateMines(1);
                game.field[n][y] = " x ";
            }

            game.clear(n,y);
            game.detect();
            game.update();


            while(true){
                if(game.getDone() == true && game.getWin() == true){
                    System.out.println("You win!");
                    game.onEnd();
                    break;
                }else if(game.getDone() == true){
                    game.onEnd();
                    break;
                }else if(game.getDone() == false){
                    n = -1;
                    y = -1;     //Resets values.
                    System.out.print("Enter an x coordinate.");
                    y = scan.nextInt();
                    System.out.print("Enter a y coordinate.");
                    n = scan.nextInt();
                    game.turn(n,y);
                    game.isVictory();
                    game.detect();
                    game.update();
                }

            }
        }
    }

}