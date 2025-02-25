import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class represents the main entry point of the HU-LOAD game application.
 * It extends the JavaFX Application class and launches the game.
 */
public class Main extends Application {
    public static Pane mainPane = new Pane(); // The main pane where the game's content is displayed.
    public static Scene scene = new Scene(mainPane, 900, 880); // The primary scene of the game, initialized with a main pane.

    /**
     * The start method overridden from Application class, initializes and starts the game.
     *
     * @param primaryStage the primary stage of the application
     * @throws Exception if an exception occurs during initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GameScreen gameScreen = new GameScreen(primaryStage); // Create a new instance of GameScreen passing the primary stage
            primaryStage.setTitle("HU-LOAD"); // Set the title of the primary stage
            primaryStage.setScene(scene); // Set the scene of the primary stage
            primaryStage.show(); // Show the primary stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method, starting point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args); // Launch the application
    }
}
