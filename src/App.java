import GameObjects.GameLoop;

import javax.swing.*;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        readString("12f62c6r54v69a52n");
        /*JFrame frame = new JFrame();
        //GameLoop gameLoop = new GameLoop(8, 14, 1000, 700, 1, 2, 3, 4);
        GameLoop gameLoop = GameLoop.getInstance();
        frame.setBounds(10, 10, 1000, 750);
        frame.setTitle("Break Out TEC");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameLoop);*/

    }

    public static void readString(String message){
        int rows = 0, columns=0, red=0, green=0, yellow=0, orange=0;
        String result = "";
        for (int i =0; i< message.length(); i++){
            char symbol = message.charAt(i);
            if (symbol == 'f'){
                int var = Integer.parseInt((result));
                rows = var;
                result = "";
            }
            else if (symbol == 'c'){
                int var = Integer.parseInt((result));
                columns = var;
                result = "";
            }
            else if (symbol == 'r'){
                int var = Integer.parseInt((result));
                red = var;
                result = "";
            }
            else if (symbol == 'v'){
                int var = Integer.parseInt((result));
                green = var;
                result = "";
            }
            else if (symbol == 'a'){
                int var = Integer.parseInt((result));
                yellow = var;
                result = "";
            }
            else if (symbol == 'n'){
                int var = Integer.parseInt((result));
                orange = var;
                result = "";
            } else {
                result += ("" + symbol);
            }

        }
        System.out.println(rows + "\n" + columns + "\n" + red + "\n" + green + "\n" + yellow + "\n" + orange + "\n");
    }
}
