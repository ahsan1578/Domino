/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TrayMaker extends Canvas {
    private Tray tray;
    private GraphicsContext gc;
    private Image table;
    private boolean isFirstMove;


    /**
     * A canvas node that draws the tray
     * @param tray the tray to be drawn
     */
    TrayMaker(Tray tray){
        this.tray = tray;
        gc = this.getGraphicsContext2D();
        this.setWidth(1700);
        this.setHeight(650);
        this.table = new Image(getClass().getResourceAsStream("table3.png"));
        this.isFirstMove = true;
    }

    /**
     * Draws the tray in two rows
     * Adjacent same numbers are placed vertically above and below in the same column
     * Uses the DominoPaint to draw each domino individually
     */
    public void drawTray(){
        gc.drawImage(table,0,0,getWidth(),getHeight());
        double countTopRow = 67.5+50*((28-tray.getSize())/2.0);
        double countBottomRow = 67.5+50*((28-tray.getSize())/2.0)+50;
        DominoPaint dominoPaint = new DominoPaint();
        double yEven = 0;
        double yOdd = 0;
        if(tray.isLeftEndTopRow()){
        }
        if(tray.isLeftEndTopRow()){
            yEven = (getHeight()/2)-55;
            yOdd = (getHeight()/2)+5;
        }else {
            yOdd = (getHeight()/2)-55;
            yEven = (getHeight()/2)+5;
        }
        for(int i = 0; i<tray.getSize(); i+=2){
            dominoPaint.drawDomino(gc,tray.getTray().get(i),countTopRow,yEven,false);
            countTopRow+=105;
        }
        for(int i = 1; i<tray.getSize(); i+=2){
            dominoPaint.drawDomino(gc,tray.getTray().get(i),countBottomRow,yOdd,false);
            countBottomRow+=105;
        }

        if(isFirstMove){
            gc.setFill(Color.WHITE);
            gc.fillText("For the first move playing at the left and right are same",650,500);
            isFirstMove = false;
        }
    }

    public void setTray(Tray tray){
        this.tray = tray;
    }

    public void setFirstMove(boolean firstMove){
        isFirstMove = firstMove;
    }
}
