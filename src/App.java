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
        //readString("12f62c6r54v69a52n");
        String message = "8f14c6r54v69a52n";

        JFrame frame = new JFrame();
        //GameLoop gameLoop = new GameLoop(8, 14, 1000, 700, 1, 2, 3, 4);
        GameLoop gameLoop = GameLoop.getInstance(message);
        frame.setBounds(10, 10, 1000, 750);
        frame.setTitle("Break Out TEC");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameLoop);

    }


}
