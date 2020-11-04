import GameObjects.GameLoop;
import server.SocketCliente;

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

        /* Se crea el socket cliente */
        new SocketCliente();

        JFrame frame = new JFrame();
        //GameLoop gameLoop = new GameLoop(8, 14, 1000, 700, 1, 2, 3, 4);
        GameLoop gameLoop = GameLoop.getInstance();
        frame.setBounds(10, 10, 1000, 750);
        frame.setTitle("Break Out TEC");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameLoop);
    }
}
