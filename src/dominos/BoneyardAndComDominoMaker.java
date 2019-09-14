/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BoneyardAndComDominoMaker extends Canvas {
    private ComputerPlayer computerPlayer;
    private Boneyard boneyard;
    private GraphicsContext gc;

    /**
     * Makes computer and boneyard dominoes
     * @param computerPlayer computer player to get the computer dominoes
     * @param boneyard boneyard to get the boneyard dominoes
     */
    public BoneyardAndComDominoMaker(ComputerPlayer computerPlayer, Boneyard boneyard){
        this.gc = this.getGraphicsContext2D();
        this.computerPlayer = computerPlayer;
        this.boneyard = boneyard;
        this.setHeight(200);
        this.setWidth(1820);
    }


    /**
     * Draws the dominoes of computer and boneyard, but draws them upside down
     * Using the DominoPaint to draw individual dominoes
     */
    public void draw(){
        DominoPaint dominoPaint = new DominoPaint();
        gc.setFill(Color.PURPLE);
        gc.fillRect(0,0,getWidth(),getHeight());

        gc.strokeRect(0,0,getWidth()/2,getHeight());
        gc.strokeRect(getWidth()/2,0,getWidth()/2,getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial",20));
        gc.fillText("Boneyard", (getWidth()/4)-35,25);
        gc.fillText("Computer", (getWidth()*3/4)-35,25);

        int boneyardSecondRowSize = boneyard.getSize()/2;
        int boneyardFirstRowSize = boneyard.getSize() - boneyardSecondRowSize;

        double countBonTopRowX = (getWidth()/4)-((boneyardFirstRowSize/2.0)*100)-5;
        double countBonBottomRowX = (getWidth()/4)-((boneyardSecondRowSize/2.0)*100)-5;
        for(int i = 0; i<boneyardFirstRowSize; i++){
            Domino domino = new Domino(0,0);
            dominoPaint.drawDomino(gc,domino,countBonTopRowX,50,false);
            countBonTopRowX += 110;
        }

        for(int i = 0; i<boneyardSecondRowSize; i++){
            Domino domino = new Domino(0,0);
            dominoPaint.drawDomino(gc,domino,countBonBottomRowX,110,false);
            countBonBottomRowX += 110;
        }

        double countComRowX = 3*(getWidth()/4)-((computerPlayer.getSize()/2.0)*100)-5;
        for(int i = 0; i<computerPlayer.getSize();i++){
            Domino domino = new Domino(0,0);
            dominoPaint.drawDomino(gc,domino,countComRowX,50,false);
            countComRowX += 110;
        }
    }

    /**
     * Sets a new boneyard for new game
     * @param boneyard the new boneyard
     */
    public void setBoneyard(Boneyard boneyard) {
        this.boneyard = boneyard;
    }

    /**
     * Sets a new computer player for new game
     * @param computerPlayer the new computer player
     */
    public void setComputerPlayer(ComputerPlayer computerPlayer) {
        this.computerPlayer = computerPlayer;
    }
}
