package GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameExpectator extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int gameWidth;
    private int gameHeight;
    private int score = 0;
    private int totalBricks = 0;
    private Timer timer;
    private int delay = 8;
    private int level = 0;
    private int rows;
    private int columns;
    private Graphics graphics;

    private ArrayList<Ball> balls = new ArrayList<Ball>();

    private int ballsCount = -1;

    private WallOfBricks mapGenerator;
    private Player player;
    int redScore;
    int orangeScore;
    int yellowScore;
    int greenScore;


    /**
     * Constructor
     * @param rows
     * @param columns
     * @param gameWidth
     * @param gameHeight
     * @param redScore
     * @param orangeScore
     * @param yellowScore
     * @param greenScore
     */
    public GameExpectator(int rows, int columns, int gameWidth, int gameHeight,
                          int redScore, int orangeScore, int yellowScore, int greenScore) {
        this.rows = rows;
        this.columns = columns;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        play = true;

        mapGenerator = new WallOfBricks(rows, columns, gameWidth - 7, 100, redScore, orangeScore,
                yellowScore, greenScore);
        totalBricks = rows * columns;
        player = new Player(gameWidth / 2 - 50, gameHeight - 28, 100, 8, gameWidth, gameHeight);
        Ball newball = new Ball(gameWidth / 2, gameHeight / 2);
        ballsCount += 1;
        balls.add(newball);

        this.redScore = redScore;
        this.yellowScore = yellowScore;
        this.greenScore = greenScore;
        this.orangeScore = orangeScore;

        timer = new Timer(delay, this);
        timer.start();
    }

    /**
     * Encargado de pintar la interfaz cada vvuelta del ciclo.
     * @param graphics
     */
    public void paint(Graphics graphics) {
        this.graphics = graphics;
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, gameWidth, gameHeight);

        // Borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, gameHeight);
        graphics.fillRect(0, 0, gameWidth, 3);
        graphics.fillRect(gameWidth - 3, 0, 3, gameHeight);

        // The bricks.
        mapGenerator.draw((Graphics2D) graphics);

        player.paint(graphics);

        //Paint balls
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.paint(graphics);
        }

        // The score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Score: " + score, gameWidth / 2, 30);

        // The lifes
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Lifes: " + player.lifes, 20, 30);

        // The level
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Level : " + level, gameWidth / 4, 30);


        // Winner
        if (totalBricks <= 0) {
            play = false;


            level += 1;
            mapGenerator = new WallOfBricks(rows, columns, gameWidth - 7, 100, redScore,
                    orangeScore, yellowScore, greenScore);
            totalBricks = rows * columns;

            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("serif", Font.BOLD, 50));
            graphics.drawString("2", gameWidth / 3, gameHeight / 3);

            player = new Player(gameWidth / 2 - 50, gameHeight - 28, 100, 8, gameWidth, gameHeight);
            Ball ball = new Ball(gameWidth / 2, gameHeight / 2);
            balls = new ArrayList<Ball>();
            ballsCount = 0;
            balls.add(ball);

            // Timer for new game: 1
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            graphics.setColor(Color.green);
            graphics.setFont(new Font("serif", Font.BOLD, 50));
            graphics.drawString("1", gameWidth / 3, gameHeight / 3);

            play = true;
        }

        // Ball Game Over
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            if (ball.y > gameHeight) {
                player.lifes -= 1;
                ballsCount -= 1;
                balls.remove(ball);
                if (player.lifes > 0) {
                    Ball addingBall = new Ball(gameWidth / 2, gameHeight / 2);
                    balls.add(addingBall);
                    addingBall.paint(graphics);
                }
            }

        }
        if (player.lifes <= 0) {
            play = false;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 25));
            graphics.drawString("Game Over", 190, 300);
        }


        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Funcion encargada de actualizar al cliente de modo que no tenga
     * control con el teclado.
     * @param play
     * @param ballX
     * @param ballY
     * @param ballxDir
     * @param ballyDir
     * @param paddleWidth
     * @param paddleX
     * @param paddleY
     */
    public void updateLoop(boolean play,
                           int ballX,
                           int ballY,
                           int ballxDir,
                           int ballyDir,
                           int paddleWidth,
                           int paddleX,
                           int paddleY) {
        timer.start();
        balls.clear();
        Ball newBall = new Ball(ballX, ballY);
        newBall.yDir = ballyDir;
        newBall.xDir = ballxDir;
        balls.add(newBall);
        player.width = paddleWidth;
        player.x = paddleX;
        player.y = paddleY;


        if (play) {
            for (int b = 0; b < balls.size(); b++) {
                Ball ball = balls.get(b);
                for (int i = 0; i < mapGenerator.wall.length; i++) {
                    for (int j = 0; j < mapGenerator.wall[0].length; j++) {
                        if (mapGenerator.wall[i][j] > 0) {
                            int brickX = j * mapGenerator.wallOfBricks[0][0].width + 5;
                            int brickY = i * mapGenerator.wallOfBricks[0][0].height + 50;
                            int brickWidth = mapGenerator.wallOfBricks[0][0].width;
                            int brickHeight = mapGenerator.wallOfBricks[0][0].height;

                            Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                            Rectangle ballRect = new Rectangle(ball.x, ball.y, 20, 20);
                            Rectangle brickRect = rectangle;

                            if (ballRect.intersects(brickRect)) {
                                mapGenerator.setBrickValue(0, i, j);
                            }
                        }
                    }
                }

            }
            repaint();
        }

    }
}

