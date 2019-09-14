/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import java.util.LinkedList;

public class Boneyard {
    private LinkedList<Domino> boneyard = new LinkedList<>();

    /**
     * Boneyard is a linked list of dominoes from where the players draw their dominoes
     * Constructor fills the boneyard with 28 dominoes
     */
    public Boneyard(){
        for(int i = 0; i<7; i++){
            for(int j=i; j<7;j++){
                Domino domino = new Domino(i,j);
                boneyard.add(domino);
            }
        }
    }

    /**
     * @return the linked list of dominoes
     */
    public LinkedList<Domino> getBoneyard(){
        return boneyard;
    }

    /**
     * @return the number of dominoes in the boneyard
     */
    public int getSize(){
        return boneyard.size();
    }

    /**
     * @param domino remove the givn domino from boneyard
     */
    public void removeDomino(Domino domino){
        boneyard.remove(domino);
    }

    /**
     * @param dominoIndex remove the domino at the given index
     */
    public void removeDomino(int dominoIndex){
        boneyard.remove(dominoIndex);
    }

    /**
     * @param dominoIndex the index of the domino which is to be returned
     * @return returns the domino at the given index
     */
    public Domino getDomino(int dominoIndex){
        return boneyard.get(dominoIndex);
    }

    /**
     * @return true is the boneyard is empty, false otherwise
     */
    public boolean isEmpty(){
        return boneyard.size() <= 0;
    }
}
