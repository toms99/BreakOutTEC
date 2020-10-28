package GameObjects;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    Player(int x, int y, int width, int height){
        super(x, y, width, height);

    }
    @Override
    public void paint(Graphics graphics) {
        //super.paint(graphics);

        // The paddle
        graphics.setColor(Color.green);
        graphics.fillRect(x, y, width, height);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (x >= (1000 - width)){
                x = (1000 - width);
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
