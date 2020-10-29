package GameObjects;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Ball extends GameObject {
    public int xDir = -1;
    public int yDir = -2;

    public Ball(int x, int y){
        super(x, y, 20, 20);
    }

    @Override
    public void paint(Graphics graphics) {

        // The ball
        graphics.setColor(Color.white);
        graphics.fillOval(x, y, 20, 20);

        // Ball Game Over
        if (y > 1000){
            //super.play = false;
            xDir = 0;
            yDir = 0;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 25));
            graphics.drawString("Game Over", 190, 300);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void paint(Graphics2D graphics2D) {}

    @Override
    public void actionPerformed(ActionEvent e) {}

}
