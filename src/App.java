import GameObjects.GameLoop;

import javax.swing.JFrame;

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
        GameLoop gameLoop = new GameLoop();

        frame.setBounds(10, 10, 1000, 800);
        frame.setTitle("Break Out TEC");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameLoop);
    }
}
