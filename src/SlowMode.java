import javafx.scene.paint.Color;

public class SlowMode extends Mode {




    public SlowMode(Color player1, Color player2){
        Player[] players = new Player[2];
        players[0] = new UserPlayer(player1,-1, 0, 1);
        players[1] = new UserPlayer(player2, 1, 0, 2);
        super.setPlayersList(players);
    }


    @Override
    public int numberOfGames() {
        return -1;
    }

    @Override
    public void setUpPlayers(Board game) {
        game.initializePlayer(game.getRows()-1, getPlayersList()[0].getPlayerColor());
        game.initializePlayer(0, getPlayersList()[1].getPlayerColor());
    }


    @Override
    public Player nextPlayer(Player current){
        if(current.equals(getPlayersList()[0])){
            return getPlayersList()[1];
        }
        return getPlayersList()[0];
    }

    @Override
    public Player startingPlayer() {
        return getPlayersList()[0];
    }

}
