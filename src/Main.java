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

        Board test = new Board(border, 5, 5);
        test.initializePlayer(2, Color.CHOCOLATE);
        //test.initializePlayer(0, Color.BLACK);


    }

}
