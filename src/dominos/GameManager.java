/**
 * @author D M Raisul Ahsa
 * @version 1.0
 */

package dominos;


public class GameManager {
    private Boneyard boneyard;
    private Player humanPlayer;
    private ComputerPlayer computerPlayer;
    private Tray tray;
    private boolean gameOver;
    private String currentTurn;
    private int humanPoints;
    private int computerPoints;
    private Side humanPlayingSide;
    private int humanPlayingDominoIndex;

    /**
     * Game manager is the controller that coordinates the game
     * Creates a boneyard, tray, a human player, a computer player to start with
     * sets the human intended playing side to left (can be changed later using button click)
     * and human intended playing domino index to 28 (also can be changed later using button click)
     */
    public GameManager(){
        boneyard = new Boneyard();
        tray = new Tray();
        humanPlayer = new Player(boneyard);
        computerPlayer = new ComputerPlayer(boneyard, tray);
        gameOver = false;
        currentTurn = "human";
        humanPoints = 0;
        computerPoints = 0;
        humanPlayingSide = Side.LEFT;
        humanPlayingDominoIndex = 28;
    }


    /**
     * Checks if a player has move. Goes through every domino try both ends of the domino with both ends of the tray (by rotating the domino)
     * Calls the method isLegalMove on tray object to check is the move is legal at particular side of the tray for a particular domino
     * @param player The player whose dominoes will be checked
     * @return true if the player has a move, false otherwise
     */
    public boolean playerHasMove(Player player){
        boolean hasMove = false;
        if(player.getSize() == 0){
            return false;
        }
        for(Domino d: player.getMyDominos()){
            if(tray.isLegalMove(Side.LEFT,d)){
                hasMove = true;
                break;
            }else if(tray.isLegalMove(Side.RIGHT,d)){
                hasMove = true;
                break;
            }else {
                d.rotateDomino();
                if(tray.isLegalMove(Side.LEFT,d)){
                    hasMove = true;
                    d.rotateDomino();
                    break;
                }else if(tray.isLegalMove(Side.RIGHT,d)){
                    hasMove = true;
                    d.rotateDomino();
                    break;
                }else {
                    d.rotateDomino();
                }
            }
        }
        return hasMove;
    }

    /**
     * Checks if the game is over
     * First checks if the boneyard size is zero
     * If yes then checks is either of the players are done playing
     * or if both doesn't have any move
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver(){
        if(boneyard.getSize() == 0){
            if(computerPlayer.getSize() == 0){
                return true;
            }else if(humanPlayer.getSize() == 0){
                return true;
            }else if(!playerHasMove(humanPlayer) && !playerHasMove(computerPlayer)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method find the winner
     * It counts all the remaining dots of the both players
     * The player with less remaining dots wins
     * @return the winner player
     */
    public Player getWinner(){
        int humanPoint = 0;
        int comPoint = 0;
        if(humanPlayer.getSize()>0){
            for(Domino d: humanPlayer.getMyDominos()){
                humanPoint += d.getNumberLeft()+d.getNumberRight();
            }
        }
        humanPoints = humanPoint;
        if(computerPlayer.getSize()>0){
            for (Domino d: computerPlayer.getMyDominos()){
                comPoint += d.getNumberLeft()+d.getNumberRight();
            }
        }
        computerPoints = comPoint;
        if(comPoint<humanPoint){
            return computerPlayer;
        }else if(humanPoint<comPoint){
            return humanPlayer;
        }else {
            return null;
        }
    }

    /**
     * @return total number of remaining dots of the computer
     */
    public int getComputerPoints() {
        return computerPoints;
    }

    /**
     * @return total number of remaining dots of the human player
     */
    public int getHumanPoints(){
        return humanPoints;
    }

    /**
     * This methos is used to set computer's remaining dots
     * Computer points are set to zero when the human player starts a new game
     * @param computerPoints the remaining dots of the computer
     */
    public void setComputerPoints(int computerPoints) {
        this.computerPoints = computerPoints;
    }

    /**
     * This methos is used to set human's remaining dots
     * human points are set to zero when the human player starts a new game
     * @param humanPoints the remaining dots of the computer
     */
    public void setHumanPoints(int humanPoints) {
        this.humanPoints = humanPoints;
    }

    /**
     * @return the tray
     */
    public Tray getTray(){
        return tray;
    }


    /**
     * This method sets human playing side from button input
     * @param side the side human chooses to play on
     */
    public void setHumanPlayingSide(Side side){
        this.humanPlayingSide = side;
    }

    /**
     * Sets human's chosen domino index in human's myDominos for the next move
     * @param index the index of the domino that human chooses to play
     */
    public void setHumanPlayingDominoIndex(int index) {
        this.humanPlayingDominoIndex = index;
    }

    /**
     * @return current human chosen domino index
     */
    public int getHumanPlayingDominoIndex() {
        return humanPlayingDominoIndex;
    }

    /**
     * @return the human player
     */
    public Player getHumanPlayer(){
        return humanPlayer;
    }

    /**
     * Makes human move which is basically adding the human chosen domino to the tray
     * Also changes the turn after making the move
     * Keeps the turn to itself if human doesn't have any possible move and boneyard is empty and game is not over
     * @param index of the human chosen domino
     * @param side the side at which human chose to play
     */
    public void makeHumanMove(int index, Side side){
        Domino d = humanPlayer.offerDominoToPlay(index);
        tray.addDomino(side,d);
        if(isGameOver()){
            gameOver = true;
        }
        if(playerHasMove(computerPlayer) || boneyard.getSize()>0){
            currentTurn = "com";
        }
    }

    /**
     * Makes the computer move calling the play() method of Computer Player
     * Changes the turn after the move
     * Keeps the turn to itself if human doesn't have any possible move and boneyard is empty and game is not over
     */
    public void makeComputerMove(){
        if(playerHasMove(computerPlayer)){
            computerPlayer.play();
            if(isGameOver()){
                gameOver = true;
            }
            if(playerHasMove(humanPlayer) || boneyard.getSize()>0){
                currentTurn = "human";
            }
        }else {
            computerPlayer.addDominoFromBoneyard();
            if(isGameOver()){
                gameOver = true;
            }
            if(playerHasMove(humanPlayer) || boneyard.getSize()>0){
                currentTurn = "human";
            }
        }
    }

    /**
     * Sets the current turn
     * @param str "com" or "human"
     */
    public void setCurrentTurn(String str){
        currentTurn = str;
    }


    /**
     * @return human chosen side
     */
    public Side getHumanPlayingSide() {
        return humanPlayingSide;
    }

    /**
     * @return boneyard, more precisely boneyard at the current state
     */
    public Boneyard getBoneyard() {
        return boneyard;
    }

    /**
     * @return the computer player
     */
    public ComputerPlayer getComputerPlayer() {
        return computerPlayer;
    }

    /**
     * Makes the human move
     * Check if the game is over
     * Make computer and check if the game over in loop until the move is given back to computer player
     */
    public void runTheGame(){
        if(!isGameOver()){
            if(currentTurn.equals("human")){
                makeHumanMove(humanPlayingDominoIndex,humanPlayingSide);
                if(isGameOver()){
                    return;
                }
            }
            if(currentTurn.equals("com")){
                while(currentTurn.equals("com")){
                    makeComputerMove();
                    if(isGameOver()){
                        break;
                    }
                }
            }
        }
    }
}
