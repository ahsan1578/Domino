/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */


package dominos;

import java.util.LinkedList;

public class Domino {
    private int numberLeft;
    private int numberRight;
    private LinkedList<Integer> domino;
    private boolean isSelected;

    /**
     * This class is the bluprint of a domino object
     * A domino is basically a linkedlist of two numbers from 0 to 6
     * @param numberLeft the number a the left end of domino
     * @param numberRight the number at the right end of domino
     */
    public Domino(int numberLeft, int numberRight){
        this.numberLeft = numberLeft;
        this.numberRight = numberRight;
        this.domino = new LinkedList<>();
        domino.add(numberLeft);
        domino.add(numberRight);
        this.isSelected = false;
    }

    /**
     * @return the domino that is the linkedlist
     */
    public LinkedList<Integer> getDomino(){
        return domino;
    }

    /**
     * This method swaps the righ and left domino numbers
     */
    public void rotateDomino(){
        domino.set(0,numberRight);
        domino.set(1,numberLeft);
        int temp = numberLeft;
        numberLeft = numberRight;
        numberRight = temp;
    }

    /**
     * @return the left number of the domino
     */
    public int getNumberLeft(){
        return numberLeft;
    }

    /**
     * @return the right number on the domino
     */
    public int getNumberRight(){
        return numberRight;
    }

    /**
     * Being a selected domino means that the human player has selected the domino to make next move
     * @param selected a boolean if it's selected or not. True for yes and false for no
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * @return if the domino is selected
     */
    public boolean isSelected() {
        return isSelected;
    }
}
