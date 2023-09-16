import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The Main class will start the application.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        Scene window = new Scene(border);
        primaryStage.setScene(window);
        primaryStage.setTitle("Hexapawn");
        primaryStage.show();

        VBox buttonBox = new VBox();
        Label boxLabel = new Label("Select a Mode. ");
        buttonBox.getChildren().add(boxLabel);
        border.setLeft(buttonBox);
        buttonBox.setSpacing(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setTranslateX(30);
        Button slowMode = new Button("Slow Mode");
        buttonBox.getChildren().add(slowMode);

        Game startMode = new Game(3,3, new SlowMode(Color.ALICEBLUE, Color.BEIGE), border);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(startMode.canRun()) {
                    startMode.runGame();
                }
            }
        };
        timer.start();



    }

}
