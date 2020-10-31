package GameObjects;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    int lifes;
    int gameWidth;
    int gameHeight;

    /**
     * Constructor.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param gameWidth
     * @param gameHeight
     */
    Player(int x, int y, int width, int height, int gameWidth, int gameHeight){
        super(x, y, width, height);
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        lifes = 3;
    }
    @Override
    /**
     * Pinta al jugador en la pantalla.
     */
    public void paint(Graphics graphics) {
        //super.paint(graphics);

        // The paddle
        graphics.setColor(Color.green);
        graphics.fillRect(x, y, width, height);
    }

    @Override
    /**
     * Es llamada cada vez que es presionada una tecla.
     */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (x >= (gameWidth - width)){
                x = (gameWidth - width);
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if (x <= 10){
                x = 10;
            } else {
                moveLeft();
            }
        }
    }

    @Override
    public void paint(Graphics2D graphics2D) {

    }

    public void moveRight(){
        x += 20;
    }

    public void moveLeft(){
        x -= 20;
    }

    
}
