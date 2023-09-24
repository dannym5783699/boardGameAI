import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author Danny Metcalfe
 * @Description: This class is for running a slow mode which allows a user to see
 * the valid computer moves and either choose for them or allow the computer to choose a move.
 */
public class SlowMode extends Mode {


    /**
     * Creates a new slow mode.
     * @param player1 the color for player 1
     * @param player2 the color for player 2
     */
    public SlowMode(Color player1, Color player2){
        Player[] players = new Player[2];
        players[0] = new UserPlayer(player1,-1, 0, 1);
        players[1] = new ComputerPlayer(player2, 1, 0, 2, true);
        super.setPlayersList(players);
    }


    /**
     * Gets the number of games to play.
     * @return Returns how many games are to be played.
     */
    @Override
    public int numberOfGames() {
        return -1;
    }

    /**
     * Sets up all the needed players for the game.
     * @param game requires a Board to set up the players.
     */
    @Override
    public void setUpPlayers(Board game) {
        game.initializePlayer(game.getRows()-1, getPlayersList()[0].getPlayerColor());
        game.initializePlayer(0, getPlayersList()[1].getPlayerColor());
    }


    /**
     * Gets the next player in the game.
     * @param current current player.
     * @return returns the next player.
     */
    @Override
    public Player nextPlayer(Player current){
        if(current.equals(getPlayersList()[0])){
            return getPlayersList()[1];
        }
        return getPlayersList()[0];
    }

    /**
     * Gets the starting player
     * @return returns the starting player.
     */
    @Override
    public Player startingPlayer() {
        return getPlayersList()[0];
    }

    /**
     * Sets up extra visuals such as current moves and buttons.
     * @param gameScreen needs a place to put visuals.
     * @param currentGame may need game functions.
     * @param current may need the current player.
     * @param gameBoard may need to see the current game state.
     */
    @Override
    public void setVisuals(BorderPane gameScreen, Game currentGame, Player current, Board gameBoard) {
        HBox hbox = new HBox(20);
        VBox buttons = new VBox(10);
        HBox coordinates = new HBox(20);
        Button compChoice = new Button("Let the computer decide.");
        gameScreen.setBottom(coordinates);
        gameScreen.setRight(hbox);
        hbox.getChildren().add(buttons);
        if(current.canChoose()) {

            Label posMove = new Label(" Possible Moves:  ");
            coordinates.getChildren().add(posMove);
            buttons.getChildren().add(compChoice);
            ArrayList<int[]> currentMoves = current.getMoves();
            int totalMoves = currentMoves.size();
            int removedMoves = 0;
            ArrayList<Label> coordinateLabels = new ArrayList<>();
            int[] percentages = new int[totalMoves];

            for(int i = 0; i<totalMoves; i++){
                coordinateLabels.add(new Label("c" + currentMoves.get(i)[0] + " r" +
                        currentMoves.get(i)[1] + " To c" + currentMoves.get(i)[2] + " r" +
                        currentMoves.get(i)[3] ));
                coordinates.getChildren().add(coordinateLabels.get(i));
                if(current.isRemoved(currentGame.getCurrentTurn(), i, gameBoard )){
                    percentages[i] = 0;
                    removedMoves++;
                }
                else{
                    percentages[i] = 1;
                }
            }
            double compChance = 1.0/ (totalMoves - removedMoves);
            compChance = compChance * 100;
            for(int i = 0; i<totalMoves; i++){
                if(percentages[i] != 0){
                    percentages[i] = (int) Math.round(compChance);
                }
                String format = String.valueOf(percentages[i]);
                coordinateLabels.get(i).setText(coordinateLabels.get(i).getText() +" Chance: " + format +"%");
            }


        }
        compChoice.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * If the button was clicked then the computer makes a move.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                if(current.canChoose()){
                    buttons.getChildren().clear();
                    current.makeTurn(gameBoard, currentGame);
                    coordinates.getChildren().clear();
                }
            }
        });
    }

}
