import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Random;

/**
 * @author Danny Metcalfe
 * @Description: This class will set up a computer player and work with Game and Board to make moves and run.
 * This class is a type of Player and it will implement Player.
 */
public class ComputerPlayer implements Player{

    //Each player has a Color, directions, and a player number.
    private final Color computerColor;

    private final int vertical;

    private final int horizontal;

    private final int playerNum;

    //Need to know current moves, removed moves, and last move to have the computer make a turn.
    //and to remove their last move.
    private final ArrayList<int[]> currentMoves;
    private final ArrayList<int[]> removedMoves = new ArrayList<>();
    private final ArrayList<Color[][]> removedBoard = new ArrayList<>();
    private int[] lastMove = new int[4];
    private Color[][] lastBoard;
    private int lastTurn;

    //No moves is used for the case that a computer player has removed all valid moves.
    private boolean noMoves = false;

    //If a computer player can have pieces clicked or decided by the computer.
    private final boolean chooseMoves;

    private int numFirst = 0;
    private int numSecond = 0;
    private boolean isStarting;



    /**
     * Creates a new Computer player.
     * @param playerColor Color of the player.
     * @param vertical vertical offset
     * @param horizontal horizontal offset
     * @param playerNum number of the player.
     * @param choose if true then a computer player can have moves clicked.
     */
    public ComputerPlayer(Color playerColor, int vertical, int horizontal, int playerNum, boolean choose ){
        this.chooseMoves = choose;
        currentMoves = new ArrayList<int[]>();
        this.computerColor = playerColor;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.playerNum = playerNum;
    }


    /**
     * Adds a move by their coordinates to an array.
     * @param moveCoordinates array of coordinates in the order: fromC, fromR, toC, toR
     */
    @Override
    public void addMove(int[] moveCoordinates) {
        currentMoves.add(moveCoordinates);
    }

    /**
     * Gets the color off the player.
     * @return Returns the color of this player.
     */
    @Override
    public Color getPlayerColor() {
        return this.computerColor;
    }

    /**
     * Make turn will have the computer make a turn and check if the move is removed or not.
     * It will also call getValidation in game to make the move and have game handle certain checks.
     * @param gameBoard
     * @param currentGame
     */
    @Override
    public void makeTurn(Board gameBoard, Game currentGame) {
        Random random = new Random();
        int randomIndex;
        if(currentMoves.size() > 0){
            randomIndex = random.nextInt(0, currentMoves.size());
        }
        else{
            randomIndex = 0;
        }
        int fromC = currentMoves.get(randomIndex)[0];
        int fromR = currentMoves.get(randomIndex)[1];
        int toC = currentMoves.get(randomIndex)[2];
        int toR = currentMoves.get(randomIndex)[3];
        //Check if a random move is removed.
        boolean removed = true;
        while(removed){
            removed = isRemoved(currentGame.getCurrentTurn(), randomIndex, gameBoard);
            if(removed){
                currentMoves.remove(randomIndex);
                if(currentMoves.size() == 0){
                    removeLast();
                    clearMoves();
                    noMoves = true;
                    //error condition if the computer player happens to have no moves, then it loses.
                    currentGame.getValidation(-1, fromR, toC, toR);
                    return;
                }
                randomIndex = random.nextInt(0, 1);
                fromC = currentMoves.get(randomIndex)[0];
                fromR = currentMoves.get(randomIndex)[1];
                toC = currentMoves.get(randomIndex)[2];
                toR = currentMoves.get(randomIndex)[3];
            }
        }
        currentGame.getValidation(fromC, fromR, toC, toR);

    }

    /**
     * Gets the player vertical direction.
     * @return Returns the vertical direction of the player.
     */
    @Override
    public int getVertical() {
        return this.vertical;
    }

    /**
     * Gets the horizontal direction of the player.
     * @return Returns the horizontal direction of the player.
     */
    @Override
    public int getHorizontal() {
        return this.horizontal;
    }

    /**
     * Checks if the player is equal to another player based on color.
     * @param otherPlayer other Player class
     * @return Returns true if color is equal and false if not.
     */
    @Override
    public boolean equals(Player otherPlayer) {
        if(this.computerColor.equals(otherPlayer.getPlayerColor())){
            return true;
        }
        return false;
    }

    /**
     * Gets the number of this player.
     * @return Return the number of the player.
     */
    @Override
    public int getPlayerNum() {
        return this.playerNum;
    }


    /**
     * Checks if a move is removed from the valid moves.
     * @param turn current game turn.
     * @param currentMoveIndex index of the current valid move in removedMoves.
     * @param gameBoard The game board to check if the game state is the same,
     * @return Returns true if the move is removed and false if not.
     */
    public boolean isRemoved(int turn, int currentMoveIndex, Board gameBoard){
        for (int m = 0; m< removedMoves.size(); m++) {
            if(removedMoves.get(m)[0] == turn){
                boolean sameMove = true;
                for(int i = 0; i< 4; i++){
                    if(removedMoves.get(m)[i+1] != currentMoves.get(currentMoveIndex)[i]){
                        sameMove = false;
                        break;
                    }
                }
                //If the turn and move is the same then we check the board last.
                if(sameMove){
                    Board.BoardPiece[][] currentBoard = gameBoard.getValidationGrid();
                    boolean sameBoard = true;
                    for(int c = 0; c < currentBoard.length; c++){
                        for(int r = 0; r < currentBoard[c].length; r++){
                            if( !currentBoard[c][r].getPieceColor().equals(removedBoard.get(m)[c][r]) ){
                                sameBoard = false;
                                break;
                            }
                        }
                    }
                    if(sameBoard){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets the last move of the player.
     * @param fromC Old column
     * @param fromR Old row
     * @param toC Column to move to.
     * @param toR Row to move to.
     * @param gameBoard the current game board to save the game state.
     * @param game The current game to save the current turn.
     */
    @Override
    public void setLast(int fromC, int fromR, int toC, int toR, Board gameBoard, Game game) {
        lastMove[0] = fromC;
        lastMove[1] = fromR;
        lastMove[2] = toC;
        lastMove[3] = toR;

        //Make a copy of the board using the colors and save it.
        lastBoard = new Color[gameBoard.getColumns()][gameBoard.getRows()];
        for(int c = 0; c< lastBoard.length; c++){
            for(int r = 0; r<lastBoard[c].length; r++){
                lastBoard[c][r] = gameBoard.getValidationGrid()[c][r].getPieceColor();
            }
        }
        lastTurn = game.getCurrentTurn();
    }

    /**
     * Gets wins depending on if the player is starting or not.
     * @param hasFirst Is the player first to start or not.
     * @return If the player was not starting then return other wins. Otherwise return starting wins.
     */
    @Override
    public int getWins(boolean hasFirst) {
        if(hasFirst){
            return numFirst;
        }
        return numSecond;
    }

    /**
     * Adds a win to either starting wins or other wins.
     * @param hasFirst is the player first to start.
     */
    @Override
    public void addWin(boolean hasFirst) {
        if(hasFirst){
            numFirst++;
        }
        else{
            numSecond++;
        }
    }

    /**
     * Sets if the player is starting or not.
     * @param isFirst Is the player first to start.
     */
    @Override
    public void setFirst(boolean isFirst) {
        isStarting = isFirst;
    }

    /**
     * Checks if the player is starting.
     * @return Returns true if the player is first to play and false if not.
     */
    @Override
    public boolean isFirst() {
        return isStarting;
    }


    /**
     * Clears all valid moves.
     */
    public void clearMoves(){
        currentMoves.clear();
    }


    /**
     * Removes a move from the valid moves.
     * @param fromC Old column
     * @param fromR Old row
     * @param toC Column to move to
     * @param toR Row to move to.
     * @param turn Current turn number.
     * @param gameBoard current game board as a Color array.
     */
    public void removeMove(int fromC, int fromR, int toC, int toR, int turn, Color[][] gameBoard){
        int[] removedMove = new int[]{turn, fromC, fromR, toC, toR};
        Color[][] copyBoard = new Color[gameBoard.length][gameBoard[0].length];
        for(int c = 0; c< copyBoard.length; c++){
            for(int r = 0; r<copyBoard[c].length; r++){
                copyBoard[c][r] = gameBoard[c][r];
            }
        }
        removedMoves.add(removedMove);
        removedBoard.add(copyBoard);

    }


    /**
     * Removes the saved last move.
     */
    @Override
    public void removeLast() {
        removeMove(lastMove[0], lastMove[1], lastMove[2], lastMove[3], lastTurn, lastBoard);
    }

    /**
     * Checks if all valid moves were removed.
     * @return Returns true if they are all removed and false if not.
     */
    @Override
    public boolean allRemoved() {
        return noMoves;
    }

    /**
     * Sets if all moves were removed or not.
     */
    @Override
    public void setRemoved() {
        noMoves = false;
    }

    /**
     * Checks if moves can be clicked on or only chosen the by computer.
     * @return True if the user or computer can make the move and false if only the computer can move.
     */
    @Override
    public boolean canChoose() {
        return chooseMoves;
    }

    /**
     * Allows the board to become clickable to choose pieces.
     * @param gameBoard the board is needed to set clickable.
     */
    @Override
    public void chooseMove(Board gameBoard) {
        gameBoard.setCanClick(true);

    }

    /**
     * Gets the list of current moves.
     * @return Returns the list of the current moves.
     */
    @Override
    public ArrayList<int[]> getMoves() {
        return currentMoves;
    }


}
