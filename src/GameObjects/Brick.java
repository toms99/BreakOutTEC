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

    public Brick(int indexI, int indexJ, int width, int height, int color,
                 int redScore, int orangeScore, int yellowScore, int greenScore) {
        super(indexJ * width + 5, indexI * height + 40, width, height);
        if (color == 1){
            myColor = Color.RED;
            value = redScore;
        }
        if (color == 2){
            myColor = Color.ORANGE;
            value= orangeScore;
        }
        if (color == 3){
            myColor = Color.yellow;
            value = yellowScore;
        }
        if (color == 4){
            myColor = Color.GREEN;
            value = greenScore;
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
    }

    public String hasAbility(){
        String result = "none";
        if (life) {result = "life";}
        if (ball) {result = "ball";}
        if (doublePlayer) {result = "doublePlayer";}
        if (splitPlayer) {result = "splitPlayer";}
        if (speedPlus) {result = "speedPlus";}
        if (speedLess) {result = "speedLess";}
        return  result;
    }
}
