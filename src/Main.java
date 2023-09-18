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

        //Add buttons and text field for the number of games.
        VBox buttonBox = new VBox();
        Label boxLabel = new Label("Select a Mode. ");
        buttonBox.getChildren().add(boxLabel);
        border.setLeft(buttonBox);
        buttonBox.setSpacing(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setTranslateX(30);
        Button slowMode = new Button("Slow Mode");
        buttonBox.getChildren().add(slowMode);
        Button fast = new Button("Fast Mode");
        buttonBox.getChildren().add(fast);
        Button auto = new Button("Auto Mode");
        buttonBox.getChildren().add(auto);
        TextField games = new TextField();
        games.setPromptText("Number of games. ");
        buttonBox.getChildren().add(games);
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
                        if(newValue > 0){
                            numGames = newValue;
                        }
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
                game = new Game(3,3, new SlowMode(Color.WHITESMOKE, Color.BLACK), border);
            }
        });

        fast.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * When fastMode is clicked then a fast mode game is started.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                game = new Game(3,3, new FastMode(Color.WHITESMOKE, Color.BLACK), border);
            }
        });

        auto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * If auto is selected then an AutoMade game is started.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                game = new Game(3,3, new AutoMode(Color.WHITESMOKE, Color.BLACK, numGames), border);
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
