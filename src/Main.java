import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Danny Metcalfe
 * @Description: This class starts the application and handles any mode selection.
 */
public class Main extends Application {
    /**
     * Launches the application.
     * @param args no command line arguments are used.
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Game game;
    private int numGames = 20;

    private int columns = 3;
    private int rows = 3;

    //Different values for each player color with the default.
    private double player1R = Color.WHITESMOKE.getRed();

    private double player1B = Color.WHITESMOKE.getBlue();
    private double player1G = Color.WHITESMOKE.getGreen();

    private Color player1Color = new Color(player1R, player1G, player1B, 1);

    private double player2R = Color.BLACK.getRed();
    private double player2G = Color.BLACK.getGreen();
    private double player2B = Color.BLACK.getBlue();

    private Color player2Color = new Color(player2R, player2G, player2B, 1);

    /**
     * Sets up the window and mode selection. Also runs the game.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        Scene window = new Scene(border);
        primaryStage.setScene(window);
        primaryStage.setTitle("Hexapawn");
        primaryStage.show();

        HBox controls = new HBox(30);
        border.setLeft(controls);

        //Add buttons and text field for the number of games.
        VBox buttonBox = new VBox();
        Label boxLabel = new Label("Select a Mode. ");
        buttonBox.getChildren().add(boxLabel);
        buttonBox.setSpacing(30);
        buttonBox.setAlignment(Pos.CENTER);
        Button slowMode = new Button("Slow Mode");
        buttonBox.getChildren().add(slowMode);
        Button fast = new Button("Fast Mode");
        buttonBox.getChildren().add(fast);
        Button auto = new Button("Auto Mode");
        buttonBox.getChildren().add(auto);
        TextField games = new TextField();
        games.setPromptText("Number of games. ");
        buttonBox.getChildren().add(games);

        TextField numberOfColumns = new TextField();
        numberOfColumns.setPromptText("Number of Columns ");
        buttonBox.getChildren().add(numberOfColumns);

        TextField numberOfRows = new TextField();
        numberOfRows.setPromptText("Number of rows");
        buttonBox.getChildren().add(numberOfRows);

        controls.getChildren().add(buttonBox);

        //Set up fields for player colors.
        VBox colorBox = new VBox(30);
        controls.getChildren().add(colorBox);
        colorBox.setAlignment(Pos.CENTER);
        Label colors = new Label("Player 1 colors: ");
        colorBox.getChildren().add(colors);

        TextField player1RedValue = new TextField();
        player1RedValue.setPromptText("Player 1 Red (0-1) ");
        colorBox.getChildren().add(player1RedValue);

        TextField player1GreenValue = new TextField();
        player1GreenValue.setPromptText("Player 1 Green (0-1)");
        colorBox.getChildren().add(player1GreenValue);

        TextField player1BlueValue = new TextField();
        player1BlueValue.setPromptText("Player 1 Blue (0-1)");
        colorBox.getChildren().add(player1BlueValue);

        Label player2Colors = new Label("Player 2 Colors: ");
        colorBox.getChildren().add(player2Colors);

        TextField player2RedValue = new TextField();
        player2RedValue.setPromptText("Player 2 Red (0-1)");
        colorBox.getChildren().add(player2RedValue);

        TextField player2GreenValue = new TextField();
        player2GreenValue.setPromptText("Player 2 Green (0-1)");
        colorBox.getChildren().add(player2GreenValue);

        TextField player2BlueValue = new TextField();
        player2BlueValue.setPromptText("Player 2 Blue (0-1)");
        colorBox.getChildren().add(player2BlueValue);


        player2BlueValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid player 2 blue value and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        double newBlue = Double.parseDouble(player2BlueValue.getText());
                        if(newBlue > 1 || newBlue < 0){
                            throw new NumberFormatException();
                        }
                        Color newColor = new Color(player2R, player2G, newBlue, 1);
                        if(newColor.equals(player1Color) || newColor.equals(Color.TRANSPARENT)){
                            throw new IllegalArgumentException();
                        }
                        player2B = newBlue;
                        player2Color = newColor;

                    }catch(NumberFormatException e){
                        System.out.println("Invalid Player 2 Blue");
                    }catch(IllegalArgumentException e){
                        System.out.println("Player 1 and 2 cannot match. ");
                    }
                }
            }
        });


        player2GreenValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid player 2 green value and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        double newGreen = Double.parseDouble(player2GreenValue.getText());
                        if(newGreen > 1 || newGreen < 0){
                            throw new NumberFormatException();
                        }
                        Color newColor = new Color(player2R, newGreen, player2B, 1);
                        if(newColor.equals(player1Color) || newColor.equals(Color.TRANSPARENT)){
                            throw new IllegalArgumentException();
                        }
                        player2G = newGreen;
                        player2Color = newColor;

                    }catch(NumberFormatException e){
                        System.out.println("Invalid Player 2 Green");
                    }catch(IllegalArgumentException e){
                        System.out.println("Player 1 and 2 cannot match. ");
                    }
                }
            }
        });

        player2RedValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid player 2 red value and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        double newRed = Double.parseDouble(player2RedValue.getText());
                        if(newRed > 1 || newRed < 0){
                            throw new NumberFormatException();
                        }
                        Color newColor = new Color(newRed, player2G, player2B, 1);
                        if(newColor.equals(player1Color) || newColor.equals(Color.TRANSPARENT)){
                            throw new IllegalArgumentException();
                        }
                        player2R = newRed;
                        player2Color = newColor;

                    }catch(NumberFormatException e){
                        System.out.println("Invalid Player 2 Red");
                    }catch(IllegalArgumentException e){
                        System.out.println("Player 1 and 2 cannot match. ");
                    }
                }
            }
        });


        player1BlueValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid player 1 blue value and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        double newBlue = Double.parseDouble(player1BlueValue.getText());
                        if(newBlue > 1 || newBlue < 0){
                            throw new NumberFormatException();
                        }
                        Color newColor = new Color(player1R, player1G, newBlue, 1);
                        if(newColor.equals(player2Color) || newColor.equals(Color.TRANSPARENT)){
                            throw new IllegalArgumentException();
                        }
                        player1B = newBlue;
                        player1Color = newColor;

                    }catch(NumberFormatException e){
                        System.out.println("Invalid Player 1 Blue");
                    }catch(IllegalArgumentException e){
                        System.out.println("Player 1 and 2 cannot match. ");
                    }
                }
            }
        });

        player1GreenValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid player 1 Green value and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        double newGreen = Double.parseDouble(player1GreenValue.getText());
                        if(newGreen > 1 || newGreen < 0){
                            throw new NumberFormatException();
                        }
                        Color newColor = new Color(player1R, newGreen, player1B, 1);
                        if(newColor.equals(player2Color) || newColor.equals(Color.TRANSPARENT)){
                            throw new IllegalArgumentException();
                        }
                        player1G = newGreen;
                        player1Color = newColor;

                    }catch(NumberFormatException e){
                        System.out.println("Invalid Player 1 Green");
                    }catch(IllegalArgumentException e){
                        System.out.println("Player 1 and 2 cannot match. ");
                    }
                }
            }
        });

        player1RedValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid player 1 red value and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        double newRed = Double.parseDouble(player1RedValue.getText());
                        if(newRed > 1 || newRed < 0){
                            throw new NumberFormatException();
                        }
                        Color newColor = new Color(newRed, player1G, player1B, 1);
                        if(newColor.equals(player2Color) || newColor.equals(Color.TRANSPARENT)){
                            throw new IllegalArgumentException();
                        }
                        player1R = newRed;
                        player1Color = newColor;

                    }catch(NumberFormatException e){
                        System.out.println("Invalid Player 1 Red");
                    }catch(IllegalArgumentException e){
                        System.out.println("Player 1 and 2 cannot match. ");
                    }
                }
            }
        });


        numberOfRows.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid row number and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        int newRows = Integer.parseInt(numberOfRows.getText());
                        if(newRows > 15 || newRows < 3){
                            throw new NumberFormatException();
                        }
                        rows = newRows;
                    }catch(NumberFormatException e){
                        System.out.println("Invalid rows.");
                    }
                }
            }
        });

        numberOfColumns.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Checks for a valid column number and assigns it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        int newColumns = Integer.parseInt(numberOfColumns.getText());
                        if(newColumns > 15 || newColumns < 3 ){
                            throw new NumberFormatException();
                        }
                        columns = Integer.parseInt(numberOfColumns.getText());
                    }catch(NumberFormatException e){
                        System.out.println("Invalid columns. ");
                    }
                }
            }
        });

        games.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * When a number is entered and enter is pressed then we get the number of games and parse it.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        int newValue = Integer.parseInt(games.getText());
                        if(newValue <= 0){
                            throw new NumberFormatException();
                        }
                        numGames = newValue;
                    }catch(NumberFormatException e){
                        System.out.println("Invalid number");
                    }
                }
            }
        });
        game = null;
        slowMode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * When SlowMode is clicked we start a SlowMode game.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                game = new Game(rows,columns, new SlowMode(player1Color, player2Color), border);
            }
        });

        fast.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * When fastMode is clicked then a fast mode game is started.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                game = new Game(rows,columns, new FastMode(player1Color, player2Color), border);
            }
        });

        auto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * If auto is selected then an AutoMade game is started.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                game = new Game(rows,columns, new AutoMode(player1Color, player2Color, numGames), border);
            }
        });


        AnimationTimer timer = new AnimationTimer() {
            /**
             * If game is not null and is runable then we can runGame for each turn.
             * @param now
             *            The timestamp of the current frame given in nanoseconds. This
             *            value will be the same for all {@code AnimationTimers} called
             *            during one frame.
             */
            @Override
            public void handle(long now) {
                if(game != null) {
                    if (game.canRun()) {
                        game.runGame();
                    }
                }
            }
        };
        timer.start();



    }

}
