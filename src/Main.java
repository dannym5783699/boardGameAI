import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Main class will start each game mode on click.
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

    }

}
