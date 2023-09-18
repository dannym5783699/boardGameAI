import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * @author Danny Metcalfe
 * @Description: Creates a normal game where a user plays vs ai, with no extra visuals or features.
 */
public class FastMode extends Mode{


    /**
     * Creates a new FastMode.
     * @param player1 needs a player 1 color
     * @param player2 needs a player 2 color
     */
    public FastMode(Color player1, Color player2){
        Player[] players = new Player[2];
        players[0] = new UserPlayer(player1, -1, 0, 1);
        players[1] = new ComputerPlayer(player2, 1, 0, 2, false);
        super.setPlayersList(players);
    }

    /**
     * Returns how many games to play
     * @return how many games to play, -1 is for no limit.
     */
    @Override
    public int numberOfGames() {
        return -1;
    }

    /**
     * Sets up the needed players in the game Board.
     * @param game needs a Board to initialize the players in the board.
     */
    @Override
    public void setUpPlayers(Board game) {
        game.initializePlayer(game.getRows()-1, getPlayersList()[0].getPlayerColor());
        game.initializePlayer(0, getPlayersList()[1].getPlayerColor());
    }

    /**
     * Gets the next player in the game.
     * @param current current player.
     * @return Returns the next player.
     */
    @Override
    public Player nextPlayer(Player current) {
        if(current.equals(getPlayersList()[0])){
            return getPlayersList()[1];
        }
        return getPlayersList()[0];
    }

    /**
     * Gets the first player to play.
     * @return Returns the first player that will play.
     */
    @Override
    public Player startingPlayer() {
        return getPlayersList()[0];
    }

    /**
     * Sets up extra mode visuals, does nothing in FastMode.
     * @param gameScreen needs a pane to display in.
     * @param currentGame may need the game.
     * @param current may need to know the current player.
     * @param gameBoard may need the game to see the current game state.
     */
    @Override
    public void setVisuals(BorderPane gameScreen, Game currentGame, Player current, Board gameBoard) {

    }
}
