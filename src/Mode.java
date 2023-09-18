import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * @author Danny Metcalfe
 * @Description: This is the class that all current modes extend since the main logic is the same.
 * This class is mainly used for the finding valid moves or validating current moves.
 */
public abstract class Mode {

    private Player[] playersList;

    private int hasList = 0;

    /**
     * Checks if a move is allowed.
     * @param boardPieces the current game board.
     * @param fromC column of piece to move
     * @param fromR row of piece to move
     * @param toC column to move to
     * @param toR row to move to
     * @param current current player.
     * @return returns true if valid and false if invalid
     */
    public boolean validate(Board.BoardPiece[][] boardPieces, int fromC, int fromR, int toC, int toR, Player current){
        //Used for a special case
        if(fromC < 0){
            return false;
        }
        if((fromR + current.getVertical() == toR) && current.getVertical() != 0){
            Color newColor = boardPieces[toC][toR].getPieceColor();
            if((fromC - toC == 1 || fromC-toC == -1) &&
                    (!newColor.equals(Color.TRANSPARENT)) && !newColor.equals(current.getPlayerColor())){
                return true;
            }
            if( (fromC - toC == 0) && newColor.equals(Color.TRANSPARENT)){
                return true;
            }
        }
        return false;
    }


    /**
     * Sets the list of players only once.
     * @param playersList list of players in the game.
     */
    public void setPlayersList(Player[] playersList) {
        if(hasList == 0) {
            this.playersList = playersList;
            hasList = 1;
        }

    }


    /**
     * Returns a list of all the current players
     * @return Returns a list of all the current players.
     */
    public Player[] getPlayersList(){
        return playersList;
    }

    /**
     * Gets the number of games.
     * @return Returns how many games to play
     */
    public abstract int numberOfGames();

    /**
     * Sets up the players in the board.
     * @param game requires a Board to set up the players.
     */
    public abstract void setUpPlayers(Board game);

    /**
     * Gets the next player in the game.
     * @param current current player.
     * @return returns the next player.
     */
    public abstract Player nextPlayer(Player current);

    /**
     * Checks if a player has any valid moves and adds them to their move lists.
     * @param current current player
     * @param boardPieces current board.
     * @return returns true if they have any moves.
     */
    public boolean hasValidMoves(Player current, Board.BoardPiece[][] boardPieces){
        boolean hasMoves = false;
        int playerRowOffset = current.getVertical();
        int playerColumnOffset = current.getHorizontal();
        for(int c = 0; c< boardPieces.length; c++){
            for(int r = 0; r<boardPieces[c].length; r++){
                if(boardPieces[c][r].getPieceColor().equals(current.getPlayerColor())){
                    if(playerRowOffset != 0 && playerColumnOffset == 0){
                        int newRow = r+playerRowOffset;
                        if((newRow < boardPieces[c].length) && (newRow >= 0)){
                            int newColumn = c-1;
                            for(int i = 0; i< 3; i++){
                                if(newColumn < boardPieces.length && newColumn >=0){
                                    int[] move = new int[]{c, r, newColumn, newRow};
                                    Color newPieceColor = boardPieces[newColumn][newRow].getPieceColor();
                                    if(newColumn == c && newPieceColor.equals(Color.TRANSPARENT)){
                                        current.addMove(move);
                                        hasMoves = true;
                                    }
                                    if(!newPieceColor.equals(Color.TRANSPARENT) && newColumn != c &&
                                            !newPieceColor.equals(current.getPlayerColor())){
                                        current.addMove(move);
                                        hasMoves = true;
                                    }
                                }
                                newColumn++;
                            }
                        }
                    }
                }
            }
        }
        return hasMoves;
    }

    /**
     * Checks if a player has reached the other side.
     * @param current current player.
     * @param boardPieces current baord.
     * @return returns true if they have reached the end.
     */
    public boolean hasReachedEnd(Player current, Board.BoardPiece[][] boardPieces){
        if(current.getVertical() != 0 && current.getHorizontal() == 0){
            int rowCheck;
            if(current.getVertical() < 0){
                rowCheck = 0;
            }
            else{
                rowCheck = boardPieces[0].length -1;
            }
            for(int c = 0; c< boardPieces.length; c++){
                if(boardPieces[c][rowCheck].getPieceColor().equals(current.getPlayerColor())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the starting player
     * @return returns the starting player.
     */
    public abstract Player startingPlayer();


    /**
     * Resets the current moves of all players.
     */
    public void resetPlayers(){
        for (Player player: playersList) {
            player.clearMoves();
        }
    }

    /**
     * Sets up any extra visual effects for a mode.
     * @param gameScreen needs a place to put visuals.
     * @param currentGame may need game functions.
     * @param current may need the current player.
     * @param gameBoard may need to see the current game state.
     */
    public abstract void setVisuals(BorderPane gameScreen, Game currentGame, Player current, Board gameBoard);







}
