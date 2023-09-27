
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Danny Metcalfe.
 * @Description:
 * The game class will switch modes and set up new boards when one has a winner.
 * It will also connect the board, mode, and current players to run the game using the mode.
 * When a new mode is selected the computer player will lose information.
 */
public class Game {

    /**
     * Needs the current board for mode and players.
     */
    private Board gameBoard;


    /**
     * Needs to have a current game mode to run.
     */
    private final Mode gameMode;

    /**
     * Needs to know rows, columns, and the borderPane when setting up a new game board.
     */
    private final int rows;
    private final int columns;
    private final BorderPane appPane;

    //current player.
    private Player currentPlayer;
    //Is there a valid move yet?
    private boolean currentValid = false;

    private boolean end = false;

    //This if for the animation timer, once a turn is over then it can continue running the game.
    private Boolean canRun = true;
    //Label for the current player.
    private final Label currentLabel = new Label();


    //Need to know what the last player was to find out who wins.
    private Player last = null;

    //Puts the winner on the screen.
    private final Label lastWinner = new Label("No Player has won. ");

    //Need a box for buttons created later in the game such as a new game button.
    private final HBox topRow = new HBox(40);

    private int currentTurn = 0;

    private Label turnLabel = new Label();

    private int wins = 0;



    private Player firstWin = null;



    /**
     * Creates a new Game and sets up the game.
     * Assumes gameMode and appPane are not null and rows and columns should be at least 3.
     * @param rows How many rows in the board?
     * @param columns How many columns are in the board.
     * @param gameMode The current game mode.
     * @param appPane The BorderPane to place the board in.
     */
    public Game(int rows, int columns, Mode gameMode, BorderPane appPane){
        this.gameBoard = new Board(appPane, this, rows, columns);
        this.gameMode = gameMode;
        this.rows = rows;
        this.columns = columns;
        this.appPane = appPane;
        gameMode.setUpPlayers(gameBoard);
        currentPlayer = gameMode.startingPlayer();
        gameBoard.setCanClick(false);
        VBox top = new VBox(20);
        top.getChildren().add(topRow);
        appPane.setTop(top);
        topRow.getChildren().add(currentLabel);
        topRow.getChildren().add(lastWinner);
        topRow.getChildren().add(turnLabel);


    }

    /**
     * Find out if a move is valid. If it is then the move is make and the player is changed.
     * If a move is not valid nothing happens.
     * If valid then canRun is true.
     * @param fromC first piece column.
     * @param fromR first piece row.
     * @param toC column to move to.
     * @param toR row to move to.
     */
    public void getValidation(int fromC, int fromR, int toC, int toR){
        currentValid = gameMode.validate(gameBoard.getValidationGrid(), fromC, fromR, toC, toR, this.currentPlayer);
        if(currentValid){
            gameBoard.getOverlay().getChildren().clear();
            this.currentPlayer.setLast(fromC, fromR, toC, toR, gameBoard, this);
            gameBoard.movePiece(fromC, fromR, toC, toR);
            this.currentPlayer.clearMoves();
            this.currentPlayer = gameMode.nextPlayer(this.currentPlayer);
            currentValid = false;
            canRun = true;
            gameBoard.setCanClick(false);
        }
        //For when the computer has removed all valid moves.
        if(fromC  < 0){
            canRun = true;
            this.currentPlayer = gameMode.nextPlayer(this.currentPlayer);
        }


    }


    /**
     * Runs a turn and checks if anyone has one yet.
     * Must be called multiple times until someone has won.
     */
    public void runGame(){
        canRun = false;
        if(!currentValid) {
            currentTurn++;
            turnLabel.setText("Current turn: " + getCurrentTurn());
            currentLabel.setText("Current Player: " + currentPlayer.getPlayerNum());
            boolean hasMoves = gameMode.hasValidMoves(currentPlayer, gameBoard.getValidationGrid());
            boolean reachedEnd = false;
            boolean noPlayerMoves = false;
            if(last != null){
                reachedEnd = gameMode.hasReachedEnd(last, gameBoard.getValidationGrid());
                noPlayerMoves = last.allRemoved();
            }
            //Check if anyone has won and add a next game button if someone has won.
            if(!hasMoves || reachedEnd || noPlayerMoves){
                wins++;
                //For when the computer removed all valid moves.
                if(noPlayerMoves){
                    last.setRemoved();
                    last = currentPlayer;
                }
                //If the current player has no moves or the last player reached the end then they win.
                gameBoard.setCanClick(false);
                lastWinner.setText("Player " + last.getPlayerNum() + " wins. ");
                if(!noPlayerMoves){
                    gameMode.nextPlayer(last).removeLast();
                }
                last.addWin(last.isFirst());
                if(firstWin == null){
                    firstWin = last;
                }
                canRun = false;
                Button nextGame = new Button("Click for next game.");

                //For when there is game limit.
                if(gameMode.numberOfGames() > 0){
                    gameBoard = new Board(appPane, Game.this, rows, columns);
                    topRow.getChildren().remove(nextGame);
                    lastWinner.setText("No player has won. ");
                    gameMode.setUpPlayers(gameBoard);
                    gameMode.resetPlayers();
                    currentPlayer = gameMode.startingPlayer();
                    currentTurn = 0;
                    turnLabel.setText("Current Turn: " + getCurrentTurn());
                    gameMode.hasValidMoves(currentPlayer, gameBoard.getValidationGrid());
                    canRun = true;
                    if(wins >= gameMode.numberOfGames()){
                        canRun = false;
                        Pane pane = new Pane();
                        VBox fullInfo = new VBox(20);
                        HBox info = new HBox(20);
                        fullInfo.getChildren().add(info);
                        HBox ratio1 = new HBox(20);
                        fullInfo.getChildren().add(ratio1);
                        ratio1.setTranslateX(200);
                        ratio1.setTranslateY(200);
                        HBox ratio2 = new HBox(20);
                        fullInfo.getChildren().add(ratio2);
                        ratio2.setTranslateX(200);
                        ratio2.setTranslateY(200);

                        //Building label strings for  end game stats.
                        Label player1 = new Label("Player " + currentPlayer.getPlayerNum() + " has " +
                                (currentPlayer.getWins(false) + currentPlayer.getWins(true)) + " wins.");

                        Label player2 = new Label("Player " + gameMode.nextPlayer(currentPlayer).getPlayerNum() +
                                " has " + (gameMode.nextPlayer(currentPlayer).getWins(false) +
                                gameMode.nextPlayer(currentPlayer).getWins(true)) + " wins.");

                        Label first = new Label("First win was by player " + firstWin.getPlayerNum() +
                                ", player 1 played first. ");

                        Label oneFirst = new Label();
                        int currentNum = currentPlayer.getPlayerNum();
                        int nextNum = gameMode.nextPlayer(currentPlayer).getPlayerNum();

                        oneFirst.setText("When player " + currentNum+ " goes first: Player " + currentNum +
                                " has " + currentPlayer.getWins(true) + " wins, Player " + nextNum +
                                " has " + gameMode.nextPlayer(currentPlayer).getWins(false) + " wins.");
                        ratio1.getChildren().add(oneFirst);

                        Label secondFirst = new Label();
                        secondFirst.setText("When player " + nextNum+ " goes first: Player " + currentNum +
                                " has " + currentPlayer.getWins(false) + " wins, Player " + nextNum +
                                " has " + gameMode.nextPlayer(currentPlayer).getWins(true) + " wins.");
                        ratio2.getChildren().add(secondFirst);


                        info.setTranslateY(200);
                        info.setTranslateX(200);
                        pane.getChildren().add(fullInfo);
                        info.getChildren().add(player1);
                        info.getChildren().add(player2);
                        info.getChildren().add(first);
                        appPane.setCenter(pane);
                    }

                }
                else{
                    topRow.getChildren().add(nextGame);
                }
                //When button is clicked a new game is set up while keeping the same players.
                nextGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        gameBoard = new Board(appPane, Game.this, rows, columns);
                        topRow.getChildren().remove(nextGame);
                        lastWinner.setText("No player has won. ");
                        gameMode.setUpPlayers(gameBoard);
                        currentPlayer = gameMode.startingPlayer();
                        gameMode.resetPlayers();
                        currentTurn = 0;
                        turnLabel.setText("Current Turn: " + getCurrentTurn());
                        gameMode.hasValidMoves(currentPlayer, gameBoard.getValidationGrid());
                        canRun  = true;
                    }
                });
                return;

            }
            last = currentPlayer;
            gameMode.setVisuals(appPane, this, currentPlayer, gameBoard);
            if(currentPlayer.canChoose()){
                currentPlayer.chooseMove(gameBoard);
            }
            else{
                currentPlayer.makeTurn(gameBoard, this);
            }


        }



    }

    /**
     * Checks if runGame should be called.
     * Can run is set to false right when runGame is called so it will not be called before it is done.
     * This is so if it used by a timer it will not be called nearly as often and only when needed.
     * @return Returns true if runGame is ready to be called and false if not.
     */
    public boolean canRun(){
        return canRun;
    }

    /**
     * Gets the current turn.
     * @return Returns the current turn number.
     */
    public int getCurrentTurn(){
        return this.currentTurn;
    }








}
