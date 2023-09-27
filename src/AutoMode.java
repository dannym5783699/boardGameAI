import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * @author Danny Metcalfe
 * @Description: This class extends mode to create an AutoMode where a computer
 * plays against another computer player.
 */
public class AutoMode extends Mode{

    //How many games to play.
    private final int turns;

    //Keeps track of who started last game.
    private Player lastStart;
    /**
     * Creates an AutoMode game mode, requires two colors and the number of turns.
     * @param player1 Color for player1.
     * @param player2 Color for player2.
     * @param turns how many games to play.
     */
    public AutoMode(Color player1, Color player2, int turns){
        this.turns = turns;
        Player[] players = new Player[2];
        players[0] = new ComputerPlayer(player1, -1, 0, 1, false);
        players[1] = new ComputerPlayer(player2, 1, 0, 2, false);
        lastStart = players[1];
        super.setPlayersList(players);
    }

    /**
     * Gets the number of games to play.
     * @return the number of games.
     */
    @Override
    public int numberOfGames() {
        return turns;
    }

    /**
     * Sets up the players in the gameBoard
     * @param game the board to start the players in.
     */
    @Override
    public void setUpPlayers(Board game) {
        game.initializePlayer(game.getRows()-1, getPlayersList()[0].getPlayerColor());
        game.initializePlayer(0, getPlayersList()[1].getPlayerColor());
    }

    /**
     * Gets the next player.
     * @param current current player.
     * @return Returns the next player in the game.
     */
    @Override
    public Player nextPlayer(Player current) {
        if(current.equals(getPlayersList()[0])){
            return getPlayersList()[1];
        }
        return getPlayersList()[0];
    }

    /**
     * Gets the starting player.
     * @return Returns the player that goes first.
     */
    @Override
    public Player startingPlayer() {
        if(lastStart.equals(getPlayersList()[1])) {
            lastStart.setFirst(false);
            lastStart = getPlayersList()[0];
            lastStart.setFirst(true);
            return getPlayersList()[0];
        }
        lastStart.setFirst(false);
        lastStart = getPlayersList()[1];
        lastStart.setFirst(true);
        return getPlayersList()[1];
    }

    /**
     * Sets up any needed mode visuals.
     * For this class it does nothing.
     * @param gameScreen pane to put visuals in
     * @param currentGame may need access to the game
     * @param current current player
     * @param gameBoard the board may be needed to see current moves or the state of the game.
     */
    @Override
    public void setVisuals(BorderPane gameScreen, Game currentGame, Player current, Board gameBoard) {

    }
}
