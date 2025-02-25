import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game screen where the main gameplay takes place.
 * This class manages the elements, background, and labels displayed during the game.
 */
public class GameScreen {

    private int counter; // Counter for elements
    public static List<Element> elements = new ArrayList<>(); // List to store all elements in the game
    private List<int[]> undergroundLocations = new ArrayList<>(); // List to store soil locations
    public static Timeline fuelTimeLine = new Timeline(); // Timeline for fuel decreasing

    /**
     * Constructs a new GameScreen object.
     * Initializes the game screen by creating background, labels, obstacles, and soil elements.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    public GameScreen(Stage primaryStage) {
        underGroundMaker(); // Initialize the game screen components
        DrillMachine drillMachine = new DrillMachine(primaryStage); // Create the drill machine
    }

    /**
     * Sets up the initial game screen components.
     * Calls methods to create background, labels, obstacles, and soil.
     */
    private void underGroundMaker() {
        addBackground();
        addLabel();
        addObstacle();
        addTop();
        addSoil();
        addOtherThings();
    }

    /**
     * Adds background rectangles to the game screen.
     * Creates a blue rectangle representing the sky and an orange rectangle representing the ground.
     */
    private void addBackground() {
        Rectangle blueRectangle = new Rectangle(900, 105);
        blueRectangle.setFill(Color.web("#191970"));
        Main.mainPane.getChildren().add(blueRectangle);
        Rectangle orangeRectangle = new Rectangle(900, 775);
        orangeRectangle.setLayoutY(105);
        orangeRectangle.setFill(Color.web("#A04000"));
        Main.mainPane.getChildren().add(orangeRectangle);
    }

    /**
     * Adds labels for fuel, haul, and money to the game screen.
     * Also creates a timeline to update fuel, haul, and money values continuously.
     */
    private void addLabel() {
        // Add fuel label
        Label fuelLabel = new Label(String.format("fuel:%.4f", DrillMachine.fuelCapacity));
        fuelLabel.setTextFill(Color.WHITE);
        fuelLabel.setFont(Font.font(30));
        fuelLabel.setLayoutX(5);
        Main.mainPane.getChildren().add(fuelLabel);
        // Add haul label
        Label haulLabel = new Label(String.format("haul:%d", (int)DrillMachine.collectedWeight));
        haulLabel.setTextFill(Color.WHITE);
        haulLabel.setFont(Font.font(30));
        haulLabel.setLayoutY(30);
        haulLabel.setLayoutX(5);
        Main.mainPane.getChildren().add(haulLabel);
        // Add money label
        Label moneyLabel = new Label(String.format("money:%d", (int)DrillMachine.collectedMoney));
        moneyLabel.setTextFill(Color.WHITE);
        moneyLabel.setFont(Font.font(30));
        moneyLabel.setLayoutY(60);
        moneyLabel.setLayoutX(5);
        Main.mainPane.getChildren().add(moneyLabel);
        // Create a keyframe to update labels continuously
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event -> {
            DrillMachine.fuelCapacity -= 0.1111;
            fuelLabel.setText(String.format("fuel:%.4f", DrillMachine.fuelCapacity));
            haulLabel.setText(String.format("haul:%d", (int)DrillMachine.collectedWeight));
            moneyLabel.setText(String.format("money:%d", (int)DrillMachine.collectedMoney));
            // Check if fuel is depleted
            if (DrillMachine.fuelCapacity <= 0) {
                // Display game over message
                Main.mainPane.getChildren().remove(0, Main.mainPane.getChildren().size() - 1);
                Rectangle greenGameOver = new Rectangle(900, 880);
                greenGameOver.setFill(Color.web("#1C7003"));
                Main.mainPane.getChildren().add(greenGameOver);

                Label gameOver = new Label("GAME OVER");
                gameOver.setTextFill(Color.WHITE);
                gameOver.setFont(Font.font( 50));
                gameOver.setLayoutX(270);
                gameOver.setLayoutY(350);
                Main.mainPane.getChildren().add(gameOver);
                Label collectedMoney = new Label(String.format("Collected Money: %d", (int)DrillMachine.collectedMoney));
                collectedMoney.setTextFill(Color.WHITE);
                collectedMoney.setFont(Font.font(50));
                collectedMoney.setLayoutX(180);
                collectedMoney.setLayoutY(410);
                Main.mainPane.getChildren().add(collectedMoney);

                fuelTimeLine.stop();
            }
        });
        fuelTimeLine.getKeyFrames().add(keyFrame);
        fuelTimeLine.setCycleCount(Animation.INDEFINITE);
        fuelTimeLine.play();
    }

    /**
     * Adds obstacles to the game screen.
     * Obstacles are placed at regular intervals and at the edges of the game screen.
     */
    private void addObstacle() {
        // Add obstacles horizontally
        for (int i = 160; i < 880; i += 60) {
            counter++;
            // Add obstacles on the left side
            Element obstacleLeft = new Element("obstacle", 0, i, counter % 3);
            elements.add(obstacleLeft);
            Main.mainPane.getChildren().add(obstacleLeft.getElementImage());
            // Add obstacles on the right side
            Element obstacleRight = new Element("obstacle", 840, i, counter % 3);
            elements.add(obstacleRight);
            Main.mainPane.getChildren().add(obstacleRight.getElementImage());
        }
        // Add obstacles vertically
        for (int i = 60; i < 840; i += 60) {
            counter++;
            Element obstacle = new Element("obstacle", i, 820, counter % 3);
            elements.add(obstacle);
            Main.mainPane.getChildren().add(obstacle.getElementImage());
        }
    }

    /**
     * Adds top elements to the game screen.
     * Concrete elements are added at fixed positions and other elements are added with random variations.
     * Concrete elements are added in a row from 180 to 300 on the x-axis and at 100 on the y-axis.
     * Other elements are added randomly on the x-axis with certain exclusions.
     */
    private void addTop() {
        // Add concrete elements in a row from x=180 to x=300
        for (int i = 180; i < 360; i += 60) {
            Element element = new Element("top_concrete", i, 100, (i / 60) - 3);
            elements.add(element);
            Main.mainPane.getChildren().add(element.getElementImage());
        }
        // Add other elements with random variations
        for (int i = 0; i < 900; i += 60) {
            // Skip certain x positions
            if (i == 180 || i == 240 || i == 300) {
                continue;
            }
            counter++;
            // Add elements at other x positions
            Element element = new Element("top", i, 100, counter % 2);
            elements.add(element);
            Main.mainPane.getChildren().add(element.getElementImage());
        }
    }

    /**
     * Adds soil elements to the game screen.
     * Soil elements are randomly placed within a grid, with certain positions excluded.
     * Lava elements are placed at random positions within the grid.
     * Precious elements (diamond, amazonite, platinum, emerald, ruby) are placed at random positions within the grid.
     * Regular soil elements are placed at the remaining positions within the grid.
     */
    private void addSoil() {
        // Create a grid of soil positions
        for (int x = 60; x < 840; x += 60) {
            for (int y = 160; y < 820; y += 60) {
                int[] locations = {x, y};
                undergroundLocations.add(locations);
            }
        }
        // Add lava elements randomly
        int counter = 0;
        Random randomNumber = new Random();
        while (counter < 8){
            counter++;
            int randomLocation = randomNumber.nextInt(undergroundLocations.size());
            Element lavaElement = new Element("lava", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], counter % 3);
            elements.add(lavaElement);
            Main.mainPane.getChildren().add(lavaElement.getElementImage());
            undergroundLocations.remove(randomLocation);
        }
        // Add obstacles elements randomly
        counter = 0;
        while (counter < 3) {
            counter++;
            counter++;
            int randomLocation = randomNumber.nextInt(undergroundLocations.size());
            Element lavaElement = new Element("obstacle", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], counter % 3);
            elements.add(lavaElement);
            Main.mainPane.getChildren().add(lavaElement.getElementImage());
            undergroundLocations.remove(randomLocation);
        }
        // Add other precious elements randomly
        counter = 0;
        while (counter < 11) {
            counter++;
            int randomLocation = randomNumber.nextInt(undergroundLocations.size());
            switch (counter % 5) {
                case 0:
                    Element diamond = new Element("diamond", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], 0);
                    elements.add(diamond);
                    Main.mainPane.getChildren().add(diamond.getElementImage());
                    undergroundLocations.remove(randomLocation);
                    break;
                case 1:
                    Element amazonite = new Element("amazonite", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], 0);
                    elements.add(amazonite);
                    Main.mainPane.getChildren().add(amazonite.getElementImage());
                    undergroundLocations.remove(randomLocation);
                    break;
                case 2:
                    Element platinum = new Element("platinum", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], 0);
                    elements.add(platinum);
                    Main.mainPane.getChildren().add(platinum.getElementImage());
                    undergroundLocations.remove(randomLocation);
                    break;
                case 3:
                    Element emerald = new Element("emerald", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], 0);
                    elements.add(emerald);
                    Main.mainPane.getChildren().add(emerald.getElementImage());
                    undergroundLocations.remove(randomLocation);
                    break;
                case 4:
                    Element ruby = new Element("ruby", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], 0);
                    elements.add(ruby);
                    Main.mainPane.getChildren().add(ruby.getElementImage());
                    undergroundLocations.remove(randomLocation);
                    break;
            }
        }
        // Add regular soil elements to the remaining positions
        while (!undergroundLocations.isEmpty()) {
            counter++;
            int randomLocation = randomNumber.nextInt(undergroundLocations.size());
            Element element = new Element("soil", undergroundLocations.get(randomLocation)[0], undergroundLocations.get(randomLocation)[1], counter % 5);
            elements.add(element);
            Main.mainPane.getChildren().add(element.getElementImage());
            undergroundLocations.remove(randomLocation);
        }
    }

    private void addOtherThings() {
        Image home = new Image("assets/extras/sprite/Overground_3.png/");
        ImageView homeView = new ImageView(home);
        homeView.setFitHeight(100);
        homeView.setFitWidth(100);
        homeView.setLayoutX(220);
        homeView.setLayoutY(0);
        Main.mainPane.getChildren().add(homeView);
    }
}
