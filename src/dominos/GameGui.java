/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package dominos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class GameGui extends Application {
    private GameManager game;
    private TrayMaker trayMaker;
    private BoneyardAndComDominoMaker boneyardAndComDominoMaker;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage){
        game = new GameManager();

        /**
         * Create a border pane on which all the nodes are going to be
         */
        BorderPane root = new BorderPane();

        /**
         * Draw the boneyard and computer player dominoes
         */
        boneyardAndComDominoMaker = new BoneyardAndComDominoMaker(game.getComputerPlayer(),game.getBoneyard());
        boneyardAndComDominoMaker.draw();
        root.setTop(boneyardAndComDominoMaker);

        /**
         * Draw the tray
         */
        trayMaker = new TrayMaker(game.getTray());
        trayMaker.drawTray();
        root.setCenter(trayMaker);

        /**
         * Draw the human dominoes
         */
        HumanDominoMaker humanDominoMaker = new HumanDominoMaker();
        humanDominoMaker.drawHumanDominoes(game.getHumanPlayer().getMyDominos());

        /**
         * Get the co-ordinate of mouse clicks on human dominoes and calculate the domino index
         */
        humanDominoMaker.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            boolean validIndexSet = false;
            if(x<=110 && x>=10){
                int countY = 0;
                for(int i = 0; i<game.getHumanPlayer().getSize(); i++){
                    if(y>=(10+countY) && y<=(10+countY+50)){
                        if(game.getHumanPlayingDominoIndex()!=28){
                            game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).setSelected(false);
                        }
                        game.setHumanPlayingDominoIndex(i);
                        game.getHumanPlayer().getMyDominos().get(i).setSelected(true);
                        humanDominoMaker.drawHumanDominoes(game.getHumanPlayer().getMyDominos());
                        validIndexSet = true;
                        break;
                    }
                    countY = 10+countY+50;
                }
            }
            if(!validIndexSet){
                game.setHumanPlayingDominoIndex(28);
            }
        });


        /**
         * Create a dialog box to show when the game is over
         * There will two buttons
         * Restart button will start a new game
         * Exit button will exit the program
         */
        ButtonType restartButtonType = new ButtonType("Play again", ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.NEXT_FORWARD);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.getDialogPane().getButtonTypes().addAll(restartButtonType,exitButtonType);
        Button restartButton = (Button)dialog.getDialogPane().lookupButton(restartButtonType);
        Button exitButton = (Button)dialog.getDialogPane().lookupButton(exitButtonType);
        restartButton.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 17px");
        exitButton.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 17px");


        /**
         * There will be  button to get domino from boneyard
         * Use this alert to show that human player still doesn't need a domino from boneyard
         * when there is a possible valid move with human dominoes
         */
        Alert notFromDomino = new Alert(Alert.AlertType.WARNING);
        notFromDomino.setHeaderText(null);
        notFromDomino.setContentText("You still can make valid move with your dominoes");

        /**
         * This button is used to get domino from boneyard
         * Every time when human player gets a domino from boneyard it checks if the game is over
         */
        Button getFromBoneyard = new Button("Get from \nboneyard");
        getFromBoneyard.setPrefWidth(120);
        getFromBoneyard.setMaxWidth(Control.USE_PREF_SIZE);
        getFromBoneyard.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 17px");
        getFromBoneyard.setOnMouseClicked(event -> {
            if(!game.playerHasMove(game.getHumanPlayer())){
                if(game.getBoneyard().getSize()>0){
                    game.getHumanPlayer().addDominoFromBoneyard();
                }
                humanDominoMaker.drawHumanDominoes(game.getHumanPlayer().getMyDominos());
                game.setCurrentTurn("com");
                game.runTheGame();
                trayMaker.drawTray();
                boneyardAndComDominoMaker.draw();
                if(game.isGameOver()){
                    dialog.setHeaderText("Game Over!");
                    if(game.getWinner().equals(game.getHumanPlayer())){
                        dialog.setContentText("You won by "+(game.getComputerPoints()-game.getHumanPoints())+" points" +
                                "\nDots remaining: You -"+game.getHumanPoints()+"   Computer- "+game.getComputerPoints());
                    }else{
                        dialog.setContentText("Computer won by "+(-game.getComputerPoints()+game.getHumanPoints())+" points" +
                                "\nDots remaining- You:"+game.getHumanPoints()+"   Computer: "+game.getComputerPoints());
                    }
                    Optional<ButtonType> madeChoice =  dialog.showAndWait();
                    ButtonType select = null;
                    if(madeChoice.isPresent()){
                        select = madeChoice.get();
                    }
                    if(select.equals(exitButtonType)){
                        stage.close();
                    }else if(select.equals(restartButtonType)){
                        startNewGame(game,trayMaker,boneyardAndComDominoMaker,humanDominoMaker);
                    }
                }
            }else{
                notFromDomino.showAndWait();
            }
        });

        /**
         * Create a vertical bar to put the human dominoes on
         */
        VBox rightBar = new VBox();
        rightBar.setPadding(new Insets(0,0,0,0));
        rightBar.setSpacing(0);
        rightBar.getChildren().add(humanDominoMaker);
        rightBar.getChildren().add(getFromBoneyard);
        root.setRight(rightBar);

        /**
         * This alert is used when human tries to make a move without choosing a domino
         */
        Alert notValidIndexAlert = new Alert(Alert.AlertType.WARNING);
        notValidIndexAlert.setHeaderText(null);
        notValidIndexAlert.setContentText("You didn't select any domino");


        /**
         * This alert shows that human doesn't have a domino to make valid move with
         * and that the player needs to get domino from boneyard
         */
        Alert getDominoFromBoneyard = new Alert(Alert.AlertType.WARNING);
        getDominoFromBoneyard.setHeaderText(null);
        getDominoFromBoneyard.setContentText("Please get domino from the boneyard.");

        Alert notLegalMove = new Alert(Alert.AlertType.WARNING);
        notLegalMove.setHeaderText(null);
        notLegalMove.setContentText("Not a legal move. Please select a different domino.");


        /**
         * Create a HBox to contain the buttons to rotate and make move or start over
         */
        HBox playBar = new HBox();
        playBar.setPadding(new Insets(20,260,20,260));
        playBar.setSpacing(100);
        playBar.setStyle("-fx-background-color: #403d99");

        /**
         * This button is used to rotate the domino before making a move
         */
        Button rotateButton = new Button("I want to rotate");
        rotateButton.setPrefSize(250,40);
        rotateButton.setMaxSize(Control.USE_PREF_SIZE,Control.USE_PREF_SIZE);
        rotateButton.setStyle("-fx-background-color: #011330; -fx-text-fill: white; -fx-font-size: 17px");
        rotateButton.setOnMouseClicked(event -> {
            if(!game.playerHasMove(game.getHumanPlayer())){
                getDominoFromBoneyard.showAndWait();
            }else if(game.getHumanPlayingDominoIndex()==28){
                notValidIndexAlert.showAndWait();
            }else {
                game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).rotateDomino();
                humanDominoMaker.drawHumanDominoes(game.getHumanPlayer().getMyDominos());
            }
        });

        /**
         * This button is used to play at the left end of the tray
         */
        Button playLeft = new Button("Play at the left side");
        playLeft.setPrefSize(250,40);
        playLeft.setMaxSize(Control.USE_PREF_SIZE,Control.USE_PREF_SIZE);
        playLeft.setStyle("-fx-background-color: #011330; -fx-text-fill: white; -fx-font-size: 17px");
        playLeft.setOnMouseClicked(event -> {
            game.setHumanPlayingSide(Side.LEFT);
            if(!game.playerHasMove(game.getHumanPlayer())){
                game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).setSelected(false);
                getDominoFromBoneyard.showAndWait();
            }else if(game.getHumanPlayingDominoIndex()==28){
                notValidIndexAlert.showAndWait();
            }else if(!game.getTray().isLegalMove(game.getHumanPlayingSide(),
                    game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()))){
                game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).setSelected(false);
                notLegalMove.showAndWait();
            }else {
                game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).setSelected(false);
                game.runTheGame();
                game.setHumanPlayingDominoIndex(28);
                trayMaker.drawTray();
                humanDominoMaker.drawHumanDominoes(game.getHumanPlayer().getMyDominos());
                boneyardAndComDominoMaker.draw();
                if(game.isGameOver()){
                    dialog.setHeaderText("Game Over!");
                    if(game.getWinner().equals(game.getHumanPlayer())){
                        dialog.setContentText("You won by "+(game.getComputerPoints()-game.getHumanPoints())+" points" +
                                "\nDots remaining: You -"+game.getHumanPoints()+"   Computer- "+game.getComputerPoints());
                    }else{
                        dialog.setContentText("Computer won by "+(-game.getComputerPoints()+game.getHumanPoints())+" points" +
                                "\nDots remaining- You:"+game.getHumanPoints()+"   Computer: "+game.getComputerPoints());
                    }
                    Optional<ButtonType> madeChoice =  dialog.showAndWait();
                    ButtonType select = null;
                    if(madeChoice.isPresent()){
                        select = madeChoice.get();
                    }
                    if(select.equals(exitButtonType)){
                        stage.close();
                    }else if(select.equals(restartButtonType)){
                        startNewGame(game,trayMaker,boneyardAndComDominoMaker,humanDominoMaker);
                    }
                }
            }
        });

        /**
         * Used to play at the right end of the tray
         */
        Button playRight = new Button("Play at the right side");
        playRight.setPrefSize(250,40);
        playRight.setMaxSize(Control.USE_PREF_SIZE,Control.USE_PREF_SIZE);
        playRight.setStyle("-fx-background-color: #011330; -fx-text-fill: white; -fx-font-size: 17px");
        playRight.setOnMouseClicked(event -> {
            game.setHumanPlayingSide(Side.RIGHT);
            if(!game.playerHasMove(game.getHumanPlayer())){
                game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).setSelected(false);
                getDominoFromBoneyard.showAndWait();
            }else if(game.getHumanPlayingDominoIndex()==28){
                notValidIndexAlert.showAndWait();
            }else if(!game.getTray().isLegalMove(game.getHumanPlayingSide(),
                    game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()))){
                notLegalMove.showAndWait();
            }else {
                game.getHumanPlayer().getMyDominos().get(game.getHumanPlayingDominoIndex()).setSelected(false);
                game.runTheGame();
                game.setHumanPlayingDominoIndex(28);
                trayMaker.drawTray();
                humanDominoMaker.drawHumanDominoes(game.getHumanPlayer().getMyDominos());
                boneyardAndComDominoMaker.draw();
                if(game.isGameOver()){
                    dialog.setHeaderText("Game Over!");
                    if(game.getWinner().equals(game.getHumanPlayer())){
                        dialog.setContentText("You won by "+(game.getComputerPoints()-game.getHumanPoints())+" points" +
                                "\nDots remaining- You:"+game.getHumanPoints()+"   Computer: "+game.getComputerPoints());
                    }else{
                        dialog.setContentText("Computer won by "+(-game.getComputerPoints()+game.getHumanPoints())+" points" +
                                "\nDots remaining- You:"+game.getHumanPoints()+"   Computer: "+game.getComputerPoints());
                    }
                    Optional<ButtonType> madeChoice =  dialog.showAndWait();
                    ButtonType select = null;
                    if(madeChoice.isPresent()){
                        select = madeChoice.get();
                    }
                    if(select.equals(exitButtonType)){
                        stage.close();
                    }else if(select.equals(restartButtonType)){
                        startNewGame(game,trayMaker,boneyardAndComDominoMaker,humanDominoMaker);
                    }
                }
            }
        });


        /**
         * Used to start over a new game in the middle of current game
         */
        Button newGameButton =  new Button("Start over");
        newGameButton.setPrefSize(250,40);
        newGameButton.setMaxSize(Control.USE_PREF_SIZE,Control.USE_PREF_SIZE);
        newGameButton.setStyle("-fx-background-color: #011330; -fx-text-fill: white; -fx-font-size: 17px");

        newGameButton.setOnMouseClicked(event -> {
            startNewGame(game,trayMaker,boneyardAndComDominoMaker,humanDominoMaker);
        });

        /**
         * Set all the node on the border pane
         */
        playBar.getChildren().add(rotateButton);
        playBar.getChildren().add(playLeft);
        playBar.getChildren().add(playRight);
        playBar.getChildren().add(newGameButton);


        /**
         * Show the stage
         */
        root.setBottom(playBar);
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * Sets the stage for a new game
     * @param game new game manager for the new game
     * @param trayMaker traymaker that is used to draw the new tray
     * @param bnd Boneyard and computer dominos maker to draw new boneyard and ccomputer dominoes
     * @param hdm Humandomino maker to draw new human dominoes
     */
    private void startNewGame(GameManager game, TrayMaker trayMaker, BoneyardAndComDominoMaker bnd, HumanDominoMaker hdm){
        game = new GameManager();
        trayMaker.setTray(game.getTray());
        trayMaker.setFirstMove(true);
        bnd.setBoneyard(game.getBoneyard());
        bnd.setComputerPlayer(game.getComputerPlayer());
        trayMaker.drawTray();
        bnd.draw();
        hdm.drawHumanDominoes(game.getHumanPlayer().getMyDominos());
        game.setComputerPoints(0);
        game.setHumanPoints(0);
    }
}
