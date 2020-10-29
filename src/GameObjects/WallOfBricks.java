package GameObjects;

import java.awt.*;

public class WallOfBricks extends GameObject{
    public int wall[][];
    public Brick wallOfBricks[][];



    public WallOfBricks(int rows, int columns, int width, int heigth) {
        super(0,0,width,heigth);
        wall = new int[rows][columns];
        wallOfBricks = new Brick[rows][columns];


        for (int i = 0; i < wall.length; i++){
            for (int j = 0; j < wall[0].length; j++){
                if (i < wall.length/4){
                    wall[i][j] = 1;
                    //wallOfBricks[i][j] = new Brick(i, j, width/wall[0].length, height/wall.length, 1);
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
                //wallOfBricks[i][j] = new Brick(width/wall[0].length, height/wall.length, wall[i][j], wall);
                wallOfBricks[i][j] = new Brick(i, j, width/wall[0].length, height/wall.length, wall[i][j]);
            }
        }
    }

    @Override
    public void paint(Graphics2D graphics2D) {

    }

    public void draw(Graphics2D graphics2D){
        for (int i = 0; i < wall.length; i++){
            for (int j = 0; j < wall[0].length; j++){
                if (wall[i][j] > 0){
                    wallOfBricks[i][j].paint(graphics2D);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int column){
        wall[row][column] = value;
    }
}
