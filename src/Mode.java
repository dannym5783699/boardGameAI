import javafx.scene.paint.Color;

public abstract class Mode {


    private Player[] playersList;

    private int hasList = 0;

    public boolean validate(Board.BoardPiece[][] boardPieces, int fromC, int fromR, int toC, int toR, Player current){
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


    public void setPlayersList(Player[] playersList) {
        if(hasList == 0) {
            this.playersList = playersList;
            hasList = 1;
        }

    }


    public Player[] getPlayersList(){
        return playersList;
    }


    public abstract int numberOfGames();

    public abstract void setUpPlayers(Board game);

    public abstract Player nextPlayer(Player current);

    public boolean hasValidMoves(Player current, Board.BoardPiece[][] boardPieces){
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
                                    Color newPieceColor = boardPieces[newColumn][newRow].getPieceColor();
                                    if(newColumn == c && newPieceColor.equals(Color.TRANSPARENT)){
                                        return true;
                                    }
                                    if(!newPieceColor.equals(Color.TRANSPARENT) && newColumn != c &&
                                            !newPieceColor.equals(current.getPlayerColor())){
                                        return true;
                                    }
                                }
                                newColumn++;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


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

    public abstract Player startingPlayer();







}
