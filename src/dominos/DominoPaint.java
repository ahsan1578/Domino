/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class DominoPaint{

    /**
     * Draw a given domino for a given GraphicsContext at a given position
     * @param gc GraphicsContext
     * @param domino the domino to be drawn
     * @param x x co-ordinate of the position
     * @param y y co-ordinate of the position
     * @param isSelected if the domino is the human chosen domino so that it can be highlighted
     */
    public void drawDomino(GraphicsContext gc, Domino domino, double x, double y, boolean isSelected){
        gc.setFill(Color.rgb(110,8,8,0.2));
        RadialGradient shadowGrad = new RadialGradient(0,0,
                x+15,y+35,28,false, CycleMethod.NO_CYCLE,
                new Stop(0,Color.rgb(0,0,0,1)),
                new Stop(1,Color.rgb(0,0,0,0)));
        gc.setFill(shadowGrad);
        gc.fillRoundRect(x-10,y+10,50,50,15,15);


        RadialGradient shadowTwoGrad = new RadialGradient(0,0,x+65,y+35,
                28,false, CycleMethod.NO_CYCLE,
                new Stop(0,Color.rgb(0,0,0,1)),
                new Stop(1,Color.rgb(0,0,0,0)));
        gc.setFill(shadowTwoGrad);
        gc.fillRoundRect(x+40,y+10,50,50,15,15);


        gc.setFill(Color.rgb(110,8,8,1));
        gc.fillRoundRect(x-5,y+5,50,50,15,15);
        gc.fillRoundRect(x+45,y+5,50,50,15,15);

        drawDie(gc,domino.getNumberLeft(),x,y,x+25,y+25);
        drawDie(gc,domino.getNumberRight(),x+50,y,x+75,y+25);


        gc.setStroke(Color.rgb(120,4,4));
        gc.strokeLine(x+50,y+5,x+50,y+45);

        if(isSelected){
            gc.setFill(Color.rgb(0,0,255,0.2));
            gc.fillRoundRect(x,y,50,50,15,15);
            gc.fillRoundRect(x+50,y,50,50,15,15);
        }
    }

    public void drawDie(GraphicsContext gc, int value, double x, double y, double radialGradX, double radialGradY){
        RadialGradient rectTwoGrad = new RadialGradient(0,
                0,radialGradX,radialGradY,30,false, CycleMethod.NO_CYCLE,
                new Stop(0,Color.rgb(214,140,140,1)),
                new Stop(1,Color.rgb(210,8,8,1)));
        gc.setFill(rectTwoGrad);
        gc.fillRoundRect(x,y,50,50,15,15);

        gc.setFill(Color.WHITE);
        switch (value){
            case 1:
                gc.fillOval(x+20,y+20,10,10);
                break;
            case 2:
                gc.fillOval(x+5,y+5,10,10);
                gc.fillOval(x+35,y+35,10,10);
                break;
            case 3:
                gc.fillOval(x+5,y+5,10,10);
                gc.fillOval(x+35,y+35,10,10);
                gc.fillOval(x+20,y+20,10,10);
                break;
            case 4:
                gc.fillOval(x+5,y+5,10,10);
                gc.fillOval(x+35,y+35,10,10);
                gc.fillOval(x+35,y+5,10,10);
                gc.fillOval(x+5,y+35,10,10);
                break;
            case 5:
                gc.fillOval(x+5,y+5,10,10);
                gc.fillOval(x+35,y+35,10,10);
                gc.fillOval(x+35,y+5,10,10);
                gc.fillOval(x+5,y+35,10,10);
                gc.fillOval(x+20,y+20,10,10);
                break;
            case 6:
                gc.fillOval(x+7.5,y+7.5,10,10);
                gc.fillOval(x+19.5,y+7.5,10,10);
                gc.fillOval(x+31.5,y+7.5,10,10);
                gc.fillOval(x+7.5,y+33.5,10,10);
                gc.fillOval(x+19.5,y+33.5,10,10);
                gc.fillOval(x+31.5,y+33.5,10,10);
                break;

        }
    }
}
