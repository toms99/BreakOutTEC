import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private  int totalBricks = 21;
    private  Timer timer;
    private  MapGenerator mapGenerator;
    private  int delay = 8;

    private int playerX = 310;

    private int ballX = 120;
    private int ballY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        mapGenerator = new MapGenerator(3, 7);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics){
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1,1,692,592);

        // Borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0,0,3,592);
        graphics.fillRect(0,0,692,3);
        graphics.fillRect(691,0,3,592);

        // The bricks.
        mapGenerator.draw((Graphics2D) graphics);

        // The paddle
        graphics.setColor(Color.green);
        graphics.fillRect(playerX, 550, 100, 8);

        // The ball
        graphics.setColor(Color.white);
        graphics.fillOval(ballX, ballY, 20, 20);

        // The score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("" + score, 590, 30);

        // Ball Game Over
        if (ballY > 570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 25));
            graphics.drawString("Game Over", 190, 300);
        }

        // Winner
        if (totalBricks <= 0){

        }

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play){
            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYDir *= -1;
            }

            A: for (int i = 0; i< mapGenerator.map.length ; i++){
                for (int j = 0; j < mapGenerator.map[0].length; j++){
                    if (mapGenerator.map[i][j] > 0){
                        int brickX = j * mapGenerator.brickWidth + 80;
                        int brickY = i * mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
                        Rectangle brickRect = rectangle;

                        if (ballRect.intersects(brickRect)){
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks-- ;
                            score += 5;

                            if (ballX + 19 <= brickRect.x || ballY +1>= brickRect.x + brickRect.width){
                                ballXDir *= -1;
                            } else {
                                ballYDir *= -1;
                            }

                            break A;
                        }
                    }
                }
            }

            ballX += ballXDir;
            ballY += ballYDir;

            if (ballX < 0){
                ballXDir *= -1;
            }

            if (ballY < 0){
                ballYDir *= -1;
            }

            if (ballX > 670){
                ballXDir *= -1;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX <= 10){
                playerX = 10;
            } else {
                moveLeft();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) { }

   public void moveRight(){
        play = true;
        playerX+= 20;
   }

    public void moveLeft(){
        play = true;
        playerX-= 20;
    }
}
