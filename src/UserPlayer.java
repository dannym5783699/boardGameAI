import javafx.scene.paint.Color;

/**
 * This class is for a user Controled player who clicks the screen to move.
 */
public class UserPlayer implements Player{

    private final Color UserColor;

    private final int vertical;

    private final int horizontal;

    private final int playerNum;


    public UserPlayer(Color playerColor, int vertical, int horizontal, int playerNum){
        this.playerNum = playerNum;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.UserColor = playerColor;
    }

    @Override
    public Color getPlayerColor() {
        return UserColor;
    }

    @Override
    public void makeTurn(Board gameBoard, Game currentGame) {
        gameBoard.setCanClick(true);
    }

    @Override
    public int getVertical() {
        return this.vertical;
    }

    @Override
    public int getHorizontal() {
        return this.horizontal;
    }

    @Override
    public boolean equals(Player otherPlayer) {
        if(this.UserColor.equals(otherPlayer.getPlayerColor())) {
            return true;
        }
        return false;
    }

    @Override
    public int getPlayerNum(){
        return playerNum;
    }




}
