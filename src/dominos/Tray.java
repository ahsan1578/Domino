/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import java.util.LinkedList;


public class Tray {
    private int currentLeftNumber;
    private int currentRightNumber;
    private LinkedList<Domino> tray;
    private boolean isLeftEndTopRow;

    /**
     * Tray is a linked list of dominoes.
     */
    Tray(){
        tray = new LinkedList<>();
        this.isLeftEndTopRow = true;
    }

    public void addFirstDomino(Domino domino){
        tray.add(domino);
        currentLeftNumber = domino.getNumberLeft();
        currentRightNumber = domino.getNumberRight();
    }

    /**
     * This method adds given domino to the tray at the given side given that it was a valid move
     * The valid move is checked by isLegalMove below
     * Adding at the left side means adding as the first item of the linked list
     * Adding at the right side means adding at the last item of the linked list
     * After adding the right end number and left end number is changed accordingly
     * @param side right or left ride of the tray
     * @param domino the domino to be added
     * @return true if the addition was successful, false otherwise
     */
    public boolean addDomino(Side side, Domino domino){
        if(isLegalMove(side,domino)){
            if(side.equals(Side.LEFT)){
                tray.addFirst(domino);
                currentLeftNumber = domino.getNumberLeft();
                if(tray.size() == 1){
                    currentRightNumber = domino.getNumberRight();
                }
                if(tray.size()>1){
                    isLeftEndTopRow = !isLeftEndTopRow;
                }
            }else if(side.equals(Side.RIGHT)){
                tray.addLast(domino);
                currentRightNumber = domino.getNumberRight();
                if(tray.size() == 1){
                    currentLeftNumber = domino.getNumberLeft();
                }
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * Checks if the move is legal for a given domino at the given side
     * Move is legal if the two adjacent numbers match
     * @param side the side where the move is being made
     * @param domino the domino to be added
     * @return true is the move is legal, false otherwise
     */
    public boolean isLegalMove(Side side, Domino domino){
        if(side.equals(Side.LEFT)){
            int x = domino.getNumberRight();
            return x == 0 || currentLeftNumber == 0 || x == currentLeftNumber;
        }else {
            int x = domino.getNumberLeft();
            return x == 0 || currentRightNumber == 0 || x == currentRightNumber;
        }
    }

    /**
     * @return total number of dominoes in the tray
     */
    public int getSize(){
        return tray.size();
    }

    /**
     * @return the tray linked list
     */
    public LinkedList<Domino> getTray(){
        return tray;
    }

    /**
     * This method meant to help gui to draw the tray
     * Although the tray is one list, it's drawn in two rows
     * @return if the left most item in the tray is at the top row
     */
    public boolean isLeftEndTopRow() {
        return isLeftEndTopRow;
    }

    @Override
    public String toString(){
        String str = "";
        String firstLine = "";
        String secondLine = "";
        for(int i = 0; i<tray.size(); i += 2){
            firstLine += tray.get(i).getDomino().toString();
        }
        for(int i = 1; i<tray.size(); i += 2){
            secondLine += tray.get(i).getDomino().toString();
        }
        str = firstLine+"\n"+"   "+secondLine;
        return str;
    }
}
