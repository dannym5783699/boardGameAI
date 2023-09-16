import javafx.scene.paint.Color;

public interface Player {

    public Color getPlayerColor();
    public void makeTurn(Board gameBoard, Game currentGame);

    public int getVertical();

    public int getHorizontal();

    public boolean equals(Player otherPlayer);

    public int getPlayerNum();

}
