/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import java.util.LinkedList;

public class ComputerPlayer extends Player {
    private Boneyard boneyard;
    private Tray tray;

    /**
     * An extension of player with a special method for computer to play
     * @param boneyard current boneyard being used in the game
     * @param tray current tray being used in the game
     */
    public ComputerPlayer(Boneyard boneyard, Tray tray){
        super(boneyard);
        this.boneyard = boneyard;
        this.tray = tray;
    }

    /**
     * The only special method that computer player has.
     * It finds out the best possible move for computer and make the move
     * Main principle that it uses to find the best possible move is
     * **Try to get rid of most number of dots
     */
    public void play(){
        LinkedList<Domino> validDominos = new LinkedList<>();
        for(Domino d: getMyDominos()){
            if(tray.isLegalMove(Side.LEFT,d)){
                validDominos.add(d);
            }else if(tray.isLegalMove(Side.RIGHT,d)){
                validDominos.add(d);
            }else {
                d.rotateDomino();
                if (tray.isLegalMove(Side.LEFT, d)) {
                    validDominos.add(d);
                } else if (tray.isLegalMove(Side.RIGHT, d)) {
                    validDominos.add(d);
                } else {
                    d.rotateDomino();
                }
            }
        }
        int maxDot = 0;
        Domino maxDotDomino = validDominos.get(0);
        for (Domino d: validDominos){
            if((d.getNumberRight()+d.getNumberLeft())>maxDot){
                maxDotDomino = d;
                maxDot = d.getNumberLeft()+d.getNumberRight();
            }
        }
        if(tray.isLegalMove(Side.LEFT,maxDotDomino)){
            tray.addDomino(Side.LEFT,maxDotDomino);
            getMyDominos().remove(maxDotDomino);
        }else if(tray.isLegalMove(Side.RIGHT,maxDotDomino)){
            tray.addDomino(Side.RIGHT,maxDotDomino);
            getMyDominos().remove(maxDotDomino);
        }
    }
}
