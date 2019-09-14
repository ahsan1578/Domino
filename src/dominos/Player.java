/**
 * @author D M Raisul Ahsan
 * version 1.0
 */

package dominos;

import java.util.LinkedList;
import java.util.Random;

public class Player {
    private Boneyard boneyard;
    private LinkedList<Domino> myDominos;

    /**
     * Player creates a player to play the game
     * Starts by randomly taking 7 dominoes from boneyard and adding to the linked list of dominoes named myDominoes
     * @param boneyard the boneyard to take dominoes from
     */
    public Player(Boneyard boneyard){
        this.boneyard = boneyard;
        this.myDominos = new LinkedList<>();
        for(int i = 0; i<7; i++){
            int index = new Random().nextInt(boneyard.getSize());
            myDominos.add(boneyard.getDomino(index));
            boneyard.removeDomino(index);
        }
    }

    /**
     * @return the linked list myDominoes
     */
    public LinkedList<Domino> getMyDominos(){
        return myDominos;
    }

    /**
     * Offers the domino at the given index to play
     * @param index the domino index
     * @return the domino at that index
     */
    public Domino offerDominoToPlay(int index){
        Domino domino = myDominos.get(index);
        myDominos.remove(index);
        return domino;
    }

    /**
     * @return current number of dominoes at player's possession
     */
    public int getSize(){
        return myDominos.size();
    }


    /**
     * Add a domino to player's possession
     * @param d the domino to be added
     */
    public void addDomino(Domino d){
        myDominos.add(d);
    }


    /**
     * Add a domino randomly from the boneyard given that the boneyard is not empty
     */
    public void addDominoFromBoneyard(){
        if(boneyard.getSize()>1){
            int index = new Random().nextInt(boneyard.getSize());
            addDomino(boneyard.getDomino(index));
            boneyard.removeDomino(index);
        }else if(boneyard.getSize()==1){
            addDomino(boneyard.getDomino(0));
            boneyard.removeDomino(0);
        }
    }
}
