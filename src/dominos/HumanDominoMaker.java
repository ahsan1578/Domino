/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class HumanDominoMaker extends Canvas {
    private GraphicsContext gc;
    private DominoPaint dominoPaint;


    /**
     * Extends canvas node and draw human dominoes
     */
    public HumanDominoMaker(){
        this.gc = this.getGraphicsContext2D();
        this.dominoPaint = new DominoPaint();
        this.setHeight(610);
        this.setWidth(120);
    }

    /**
     * Draws the human dominoes in a vertical column using the DominoPaint to draw each domino
     * @param humanDominoes the list of dominoes that are in human player's possession
     */
    public void drawHumanDominoes(LinkedList<Domino> humanDominoes){
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0,120,610);
        int countY = 10;

        for(Domino d: humanDominoes){
            if(d.isSelected()){
                dominoPaint.drawDomino(this.gc,d,10,countY,true);
            }else {
                dominoPaint.drawDomino(this.gc,d,10,countY,false);
            }
            countY += 60;
        }
    }
}