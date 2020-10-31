package GameObjects;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class GameObject implements KeyListener, ActionListener {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public GameObject(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint(Graphics graphics){ }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public abstract void paint(Graphics2D graphics2D);
}
