import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author Danny Metcalfe
 * @Description: This interface describes what each type of player should implement.
 */
public interface Player {
    /**
     * Each player has an add move.
     * @param moveCoordinates Coordinates of the move as fromC, fromR, tC, toR
     */
    public void addMove(int[] moveCoordinates);

    /**
     * Each player needs to be able to give a color.
     * @return returns the color of the player.
     */
    public Color getPlayerColor();

    /**
     * Each player has to make a turn.
     * @param gameBoard may need the board to make a turn.
     * @param currentGame needs the game to call its validation function.
     */
    public void makeTurn(Board gameBoard, Game currentGame);

    /**
     * Gets the vertical direction of the player.
     * @return Returns the vertical move direction of the player.
     */
    public int getVertical();

    /**
     * Gets the horizontal move direction from the player.
     * @return returns the horizontal move direction from the player.
     */
    public int getHorizontal();

    /**
     * Checks if two players are equal
     * @param otherPlayer another player to compare to.
     * @return Returns true if they are equal and false if not.
     */
    public boolean equals(Player otherPlayer);

    /**
     * Each player should be able to return their player number.
     * @return returns the number of the player.
     */
    public int getPlayerNum();

    /**
     * Clears all valid moves.
     */
    public void clearMoves();

    /**
     * Removes a move.
     * @param fromC Column of the piece.
     * @param fromR Row of the piece.
     * @param toC Column to move to.
     * @param toR Row to move to.
     * @param turn current turn number.
     * @param gameBoard the current game board as a Color[][].
     */
    public void removeMove(int fromC, int fromR, int toC, int toR, int turn, Color[][] gameBoard);

    /**
     * Removes the last move made.
     */
    public void removeLast();

    /**
     * Checks if all valid moves were removed.
     * @return returns true is they were removed and false if not.
     */
    public boolean allRemoved();

    /**
     * Sets removed to false.
     */
    public void setRemoved();

    /**
     * Checks if a players moves can be made by both the computer or by clicking.
     * @return returns true if yes and false if not.
     */
    public boolean canChoose();

    /**
     * Allows the user to click on a move for computer players.
     * @param gameBoard the current game board.
     */
    public void chooseMove(Board gameBoard);

    /**
     * Gets all valid moves.
     * @return returns all the possible valid moves.
     */
    public ArrayList<int[]> getMoves();


    /**
     * Checks if a move was removed or not.
     * @param turn current turn number.
     * @param moveIndex current index in valid moves list.
     * @param gameBoard the current game board.
     * @return returns true if the move was removed and false if not.
     */
    public boolean isRemoved(int turn, int moveIndex, Board gameBoard);

    /**
     * Sets the last move of the player.
     * @param fromC Column of the piece.
     * @param fromR Row of the piece.
     * @param toC Column to move to.
     * @param toR Row to move to.
     * @param gameBoard current game board.
     * @param game current game.
     */
    public void setLast(int fromC, int fromR, int toC, int toR, Board gameBoard, Game game);

    /**
     * Gets the current number of wins by a player.
     * @return returns how many games have been won.
     */
    public int getWins();

    /**
     * Adds one win to the current wins by a player.
     */
    public void addWin();

}
