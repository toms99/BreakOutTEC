package GameObjects;

import java.awt.*;

public class Brick extends  GameObject{
    public Color myColor;
    int[][] map;
    public int value;
    public boolean life = false;
    public boolean ball = false;
    public boolean doublePlayer = false;
    public boolean splitPlayer = false;
    public boolean speedPlus = false;
    public boolean speedLess = false;

    public Brick(int indexI, int indexJ, int width, int height, int color) {
        super(indexJ * width + 80, indexI * height + 40, width, height);
        if (color == 1){
            myColor = Color.RED;
            value = 1;
        }
        if (color == 2){
            myColor = Color.ORANGE;
            value= 2;
        }
        if (color == 3){
            myColor = Color.yellow;
            value = 3;
        }
        if (color == 4){
            myColor = Color.GREEN;
            value = 1;
        }
        System.out.println(myColor);
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        graphics2D.setColor(myColor);
        graphics2D.fillRect(x, y, width, height);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Color.black);
        graphics2D.drawRect(x, y, width, height);
        /*for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    graphics2D.setColor(myColor);
                    graphics2D.fillRect(j * width + 80, i * height + 40, width, height);
                    graphics2D.setStroke(new BasicStroke(3));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawRect(j * width + 80, i * height + 40, width, height);
                }
            }
        }*/
    }

}
