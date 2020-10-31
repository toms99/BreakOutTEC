package GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Main loop of the game
 */
public class GameLoop extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private  int gameWidth;
    private  int gameHeight;
    private int score = 0;
    private int totalBricks = 0;
    private Timer timer;
    private int delay = 8;
    private  int level = 0;
    private  int rows;
    private  int columns;
    private Graphics graphics;

    private ArrayList<Ball> balls = new ArrayList<Ball>();

    private int ballsCount = -1;

    private WallOfBricks mapGenerator;
    private Player player;

    int redScore;
    int orangeScore;
    int yellowScore;
    int greenScore;
    GameExpectator gameExpectator;

    /**
     * GameLoop constructor. Crea todos los objetos necesarios para desplegar
     * la interfaz y el juego.
     * @param rows: numero de filas
     * @param columns: numero de columnas
     * @param gameWidth: ancho del fondo
     * @param gameHeight: altura del fondo
     */
    public GameLoop(int rows, int columns, int gameWidth, int gameHeight, int redScore, int orangeScore,
                    int yellowScore, int greenScore){
        this.rows = rows;
        this.columns = columns;
        this.gameWidth = gameWidth;
        this. gameHeight = gameHeight;
        this.redScore = redScore;
        this.yellowScore = yellowScore;
        this.greenScore = greenScore;
        this.orangeScore = orangeScore;

        play = true;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        mapGenerator = new WallOfBricks(rows, columns, gameWidth - 7, 100,
                redScore, orangeScore, yellowScore, greenScore);
        totalBricks = rows*columns;
        player = new Player(gameWidth/2 - 50, gameHeight - 28, 100, 8, gameWidth, gameHeight);
        Ball newball = new Ball(gameWidth/2, gameHeight/2);
        ballsCount += 1;
        balls.add(newball);

        timer = new Timer(delay, this);
        timer.start();

        // Create Expectator
        JFrame frame = new JFrame();
        gameExpectator = new GameExpectator(rows, columns, gameWidth, gameHeight, redScore, orangeScore, yellowScore, greenScore);

        frame.setBounds(10, 10, 1000, 750);
        frame.setTitle("Break Out TEC");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameExpectator);



    }

    /**
     * Dibuja la interfaz.
     * @param graphics
     */
    public void paint(Graphics graphics){
        this.graphics = graphics;
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1,1,gameWidth,gameHeight);

        // Borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0,0,3,gameHeight);
        graphics.fillRect(0,0,gameWidth,3);
        graphics.fillRect(gameWidth - 3,0,3,gameHeight);

        // The bricks.
        mapGenerator.draw((Graphics2D) graphics);

        player.paint(graphics);

        //Paint balls
        for (int i =0; i< balls.size(); i++){
            Ball ball = balls.get(i);
            ball.paint(graphics);
        }

        // The score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Score: " + score, gameWidth/2, 30);

        // The lifes
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Lifes: " + player.lifes, 20, 30);

        // The level
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Level : " + level, gameWidth/4, 30);


        // Winner
        if (totalBricks <= 0){
            play = false;


            level += 1;
            mapGenerator = new WallOfBricks(rows, columns, gameWidth - 7, 100 ,
                    redScore, orangeScore, yellowScore, greenScore );
            totalBricks = rows*columns;

            graphics.setColor(Color.yellow);
            graphics.setFont(new Font("serif", Font.BOLD, 50));
            graphics.drawString("2", gameWidth/3, gameHeight/3);

            player = new Player(gameWidth/2 - 50, gameHeight - 28, 100, 8, gameWidth, gameHeight);
            Ball ball = new Ball(gameWidth/2, gameHeight/2);
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
            graphics.drawString("1", gameWidth/3, gameHeight/3);

            play = true;
        }

        // Ball Game Over
        for (int i =0; i < balls.size(); i++){
            Ball ball = balls.get(i);
            if (ball.y > gameHeight){
                player.lifes -= 1;
                ballsCount -= 1;
                balls.remove(ball);
                if (player.lifes > 0){
                    Ball addingBall = new Ball(gameWidth/2, gameHeight/2);
                    balls.add(addingBall);
                    addingBall.paint(graphics);
                }
            }

        }
        if (player.lifes <= 0){
            play = false;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 25));
            graphics.drawString("Game Over", 190, 300);
        }


        graphics.dispose();
    }

    @Override
    /**
     * Es llamado que vez que se completa una acción de
     */
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            for (int b = 0; b < balls.size(); b++) {
                Ball ball = balls.get(b);
                player.actionPerformed(e);
                if (new Rectangle(ball.x, ball.y, 20, 20).intersects(new Rectangle(player.x, player.y, player.width, player.height))) {
                    ball.yDir *= -1;
                }
                A:
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
                                applyAbility(mapGenerator.wallOfBricks[i][j]);
                                System.out.println("Me: " + i + " " + j + " have this ability: " + mapGenerator.wallOfBricks[i][j].hasAbility());
                                totalBricks --;
                                score += mapGenerator.wallOfBricks[i][j].value;

                                if (ball.x + 19 <= brickRect.x || ball.y + 1 >= brickRect.x + brickRect.width) {
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

                if (ball.x < 0) {
                    ball.xDir *= -1;
                }

                if (ball.y < 0) {
                    ball.yDir *= -1;
                }

                if (ball.x > gameWidth - ball.width) {
                    ball.xDir *= -1;
                }
                gameExpectator.updateLoop(play, ball.x, ball.y, ball.xDir, ball.yDir, player.width, player.x, player.y);
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

    public void applyAbility(Brick brick) {
        String ability  = brick.hasAbility();
        if (ability != "none") {

            if (ability == "life") {
                player.lifes += 1;
            }
            if (ability == "ball") {
                Ball newBall = new Ball(gameWidth / 2, gameHeight / 2);
                ballsCount += 1;
                System.out.println("Balls count is " + ballsCount);
                balls.add(newBall);
            }
            if (ability == "doublePlayer") {
                player.width *= 2;
            }
            if (ability == "splitPlayer") {
                player.width /= 2;
            }
            if (ability == "speedPlus") {
                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);
                    ball.xDir *= 2;
                    ball.yDir *= 2;
                }
            }
            if (ability == "speedLess") {
                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);

                    if (Math.abs(ball.yDir) != 1 ){
                        ball.yDir *= 0.75;
                    }
                }
            }
        }
    }



}
