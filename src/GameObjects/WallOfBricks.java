package GameObjects;

import java.awt.*;
import java.util.Random;

public class WallOfBricks extends GameObject{
    public int wall[][];
    public Brick wallOfBricks[][];
    private Random random;


    /**
     * Constructor.
     * @param rows
     * @param columns
     * @param width
     * @param heigth
     * @param redScore
     * @param orangeScore
     * @param yellowScore
     * @param greenScore
     */
    public WallOfBricks(int rows, int columns, int width, int heigth, int redScore,
                        int orangeScore, int yellowScore, int greenScore) {
        super(0,0,width,heigth);
        wall = new int[rows][columns];
        wallOfBricks = new Brick[rows][columns];
        random = new Random();

        for (int i = 0; i < wall.length; i++){
            for (int j = 0; j < wall[0].length; j++){
                if (i < wall.length/4){
                    wall[i][j] = 1;
                }
                if (i >= wall.length/4 && i< (wall.length)/2){
                    wall[i][j] = 2;
                }
                if (i >= wall.length/2 && i < 3*(wall.length/4)){
                    wall[i][j] = 3;
                }
                if (i >= 3*(wall.length/4)){
                    wall[i][j] = 4;
                }
                wallOfBricks[i][j] = new Brick(i, j, width/wall[0].length, height/wall.length, wall[i][j],
                        redScore, orangeScore, yellowScore, greenScore);
            }
        }

        setAbilities();
    }

    @Override
    public void paint(Graphics2D graphics2D) {

    }

    /**
     * Se encarga de dibujar el muro
     * @param graphics2D
     */
    public void draw(Graphics2D graphics2D){
        for (int i = 0; i < wall.length; i++){
            for (int j = 0; j < wall[0].length; j++){
                if (wall[i][j] > 0){
                    wallOfBricks[i][j].paint(graphics2D);
                }
            }
        }
    }

    /**
     * Setea la habilidad a cada ladrillo de modo random.
     */
    public void setAbilities(){
        for (int i = 0; i<=4 ; i++){
            int ranRow = random.nextInt(wallOfBricks.length);
            int ranColumn = random.nextInt(wallOfBricks[0].length);
            wallOfBricks[ranRow][ranColumn].speedLess = true;

            ranRow = random.nextInt(wallOfBricks.length);
            ranColumn = random.nextInt(wallOfBricks[0].length);
            wallOfBricks[ranRow][ranColumn].speedPlus = true;

            ranRow = random.nextInt(wallOfBricks.length);
            ranColumn = random.nextInt(wallOfBricks[0].length);
            wallOfBricks[ranRow][ranColumn].doublePlayer = true;

            ranRow = random.nextInt(wallOfBricks.length);
            ranColumn = random.nextInt(wallOfBricks[0].length);
            wallOfBricks[ranRow][ranColumn].splitPlayer = true;

            ranRow = random.nextInt(wallOfBricks.length);
            ranColumn = random.nextInt(wallOfBricks[0].length);
            wallOfBricks[ranRow][ranColumn].ball = true;

            ranRow = random.nextInt(wallOfBricks.length);
            ranColumn = random.nextInt(wallOfBricks[0].length);
            wallOfBricks[ranRow][ranColumn].life = true;

        }
    }

    public void setBrickValue(int value, int row, int column){
        wall[row][column] = value;
    }
}
