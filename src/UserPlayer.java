import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author Danny Metcalfe
 * @Description: This class is for a user Controlled player who clicks the screen to move.
 */
public class UserPlayer implements Player{

    //A player has a color, directions, and a number.
    private final Color UserColor;

    private final int vertical;

    private final int horizontal;

    private final int playerNum;


    /**
     * Creates a new UserPlayer and sets fields.
     * @param playerColor the player color.
     * @param vertical vertical direction
     * @param horizontal horizontal direction
     * @param playerNum the number of the player
     */
    public UserPlayer(Color playerColor, int vertical, int horizontal, int playerNum){
        this.playerNum = playerNum;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.UserColor = playerColor;
    }

    /**
     * Adds a move to valid moves, a User player does not need a list of moves.
     * @param moveCoordinates Coordinates of the move as fromC, fromR, tC, toR
     */
    @Override
    public void addMove(int [] moveCoordinates) {}


    /**
     * Gets the color of the player
     * @return returns the color of the player.
     */
    @Override
    public Color getPlayerColor() {
        return UserColor;
    }

    /**
     * Makes a move, for a UserPlayer we just allow them to click the screen.
     * @param gameBoard may need the board to make a turn.
     * @param currentGame needs the game to call its validation function.
     */
    @Override
    public void makeTurn(Board gameBoard, Game currentGame) {
        gameBoard.setCanClick(true);
    }

    /**
     * Gets the vertical move direction
     * @return returns the vertical move direction
     */
    @Override
    public int getVertical() {
        return this.vertical;
    }

    /**
     * Gets the horizontal move direction.
     * @return returns the horizontal move direction.
     */
    @Override
    public int getHorizontal() {
        return this.horizontal;
    }

    /**
     * Checks if two players are equal based on color.
     * @param otherPlayer another player to compare to.
     * @return return true if they are equal and false if not.
     */
    @Override
    public boolean equals(Player otherPlayer) {
        if(this.UserColor.equals(otherPlayer.getPlayerColor())) {
            return true;
        }
        return false;
    }

    /**
     * Gets the number of the player.
     * @return returns the number of the player.
     */
    @Override
    public int getPlayerNum(){
        return playerNum;
    }

    /**
     * Clears all valid moves, for a User player nothing is done.
     */
    @Override
    public void clearMoves() {}

    /**
     * Removes a move from valid moves. For a user player nothing is done.
     * @param fromC Column of the piece.
     * @param fromR Row of the piece.
     * @param toC Column to move to.
     * @param toR Row to move to.
     * @param turn current turn number.
     * @param gameBoard the current game board as a Color[][].
     */
    @Override
    public void removeMove(int fromC, int fromR, int toC, int toR, int turn, Color[][] gameBoard) {

    }


    /**
     * Removes the last move. Nothing is done for a user player.
     */
    @Override
    public void removeLast() {

    }

    /**
     * Checks if all valid moves are removed. Always false for a user.
     * @return Returns false
     */
    @Override
    public boolean allRemoved() {
        return false;
    }

    /**
     * Sets if all moves are removed to false. Nothing is done for a user player
     */
    @Override
    public void setRemoved() {

    }

    /**
     * Checks if a user has a choice between clicking and computer moves. Always false for a user player.
     * @return False
     */
    @Override
    public boolean canChoose() {
        return false;
    }

    /**
     * Allows a user to click the board, not needed for a user player.
     * @param gameBoard the current game board.
     */
    @Override
    public void chooseMove(Board gameBoard) {

    }

    /**
     * Gets the moves of a player. A user player has no moves list.
     * @return null
     */
    @Override
    public ArrayList<int[]> getMoves() {
        return null;
    }

    /**
     * Checks if a move is removed. Always false for a user player..
     * @param turn current turn number.
     * @param moveIndex current index in valid moves list.
     * @param gameBoard the current game board.
     * @return false
     */
    @Override
    public boolean isRemoved(int turn, int moveIndex, Board gameBoard) {
        return false;
    }

    /**
     * Sets the last move. Nothing is done for a user player.
     * @param fromC Column of the piece.
     * @param fromR Row of the piece.
     * @param toC Column to move to.
     * @param toR Row to move to.
     * @param gameBoard current game board.
     * @param game current game.
     */
    @Override
    public void setLast(int fromC, int fromR, int toC, int toR, Board gameBoard, Game game) {

    }

    /**
     * Does nothing for the userPlayer currently.
     * @param hasFirst Is the player first to start or not.
     * @return 0
     */
    @Override
    public int getWins(boolean hasFirst) {
        return 0;
    }

    /**
     * Does nothing for a userPlayer currently.
     * @param hasFirst is the player first to start.
     */
    @Override
    public void addWin(boolean hasFirst) {

    }

    /**
     * Does nothing for a userPlayer currently.
     * @param isFirst Is the player first to start.
     */
    @Override
    public void setFirst(boolean isFirst) {

    }

    /**
     * Does nothing for a userPlayer currently.
     * @return false
     */
    @Override
    public boolean isFirst() {
        return false;
    }






}
