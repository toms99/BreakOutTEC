package GameObjects;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Ball extends GameObject {
    public int xDir = -2;
    public int yDir = -3;

    /**
     * Constructor
     * @param x
     * @param y
     */
    public Ball(int x, int y){
        super(x, y, 20, 20);
    }

    /**
     * Pinta a la bola cada vez que es llamada.
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {

        // The ball
        graphics.setColor(Color.white);
        graphics.fillOval(x, y, 20, 20);

    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void paint(Graphics2D graphics2D) {}

    @Override
    public void actionPerformed(ActionEvent e) {}

}
