package GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class GameLoop extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 112;
    private Timer timer;
    private   int delay = 5;

    private WallOfBricks mapGenerator;
    private Player player;
    private Ball ball;


    public GameLoop(){
        play = true;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        mapGenerator = new WallOfBricks(8, 14, 850, 100 );
        player = new Player(310, 700, 100, 8);
        ball = new Ball(120, 350);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics){
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1,1,1000,800);

        // Borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0,0,3,800);
        graphics.fillRect(0,0,1000,3);
        graphics.fillRect(997,0,3,800);

        // The bricks.
        mapGenerator.draw((Graphics2D) graphics);

        player.paint(graphics);
        ball.paint(graphics);

        // The score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("" + score, 590, 30);



        // Winner
        if (totalBricks <= 0){}

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play){
            player.actionPerformed(e);
            if (new Rectangle(ball.x, ball.y, 20, 20).intersects(new Rectangle(player.x, player.y, 100, 8))){
                ball.yDir *= -1;
            }
            A: for (int i = 0; i< mapGenerator.wall.length ; i++){
                for (int j = 0; j < mapGenerator.wall[0].length; j++){
                    if (mapGenerator.wall[i][j] > 0){
                        int brickX = j * mapGenerator.wallOfBricks[0][0].width + 80;
                        int brickY = i * mapGenerator.wallOfBricks[0][0].height + 50;
                        int brickWidth = mapGenerator.wallOfBricks[0][0].width;
                        int brickHeight = mapGenerator.wallOfBricks[0][0].height;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ball.x, ball.y, 20, 20);
                        Rectangle brickRect = rectangle;

                        if (ballRect.intersects(brickRect)){
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks-- ;
                            score += mapGenerator.wallOfBricks[i][j].value;

                            if (ball.x + 19 <= brickRect.x || ball.y +1>= brickRect.x + brickRect.width){
                                ball.xDir *= -1;
                            } else {
                                ball.yDir *= -1;
                            }

                            break A;
                        }
                    }
                }
            }

            ball.x += ball.xDir;
            ball.y += ball.yDir;

            if (ball.x < 0){
                ball.xDir *= -1;
            }

            if (ball.y < 0){
                ball.yDir *= -1;
            }

            if (ball.x > 1000){
                ball.xDir *= -1;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {player.keyPressed(e);}

    @Override
    public void keyReleased(KeyEvent e) {}


}
