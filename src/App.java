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

        JFrame frame = new JFrame();
        GameLoop gameLoop = new GameLoop(8, 14, 1000, 700);

        frame.setBounds(10, 10, 1000, 750);
        frame.setTitle("Break Out TEC");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameLoop);
    }
}
