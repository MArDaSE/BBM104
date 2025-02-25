import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * This class represents the drill machine used in the HU-LOAD game.
 * It controls the movement and actions of the drill within the game environment.
 */
public class DrillMachine {

    public static double fuelCapacity = 10000; // The fuel capacity of the drill machine.
    public static double collectedWeight; // The total weight of collected elements.
    public static double collectedMoney; // The total money collected from valuable elements.
    private Image[] drillImages; // Image arrays for different drill positions.
    private Image[] upDrillImages; // Image arrays for up drill direction.
    private Image[] downDrillImages; // Image arrays for down drill direction.
    private Image[] rightDrillImages; // Image arrays for right drill direction.
    private Image[] leftDrillImages; // Image arrays for left drill direction.
    public ImageView drillImage; // ImageView for the drill
    public Bounds drillBounds; // Bounds of the drill image
    private int counterFrame; // Frame counter for drill animation

    /**
     * Constructor for DrillMachine class. Initializes the drill and its animations.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    public DrillMachine(Stage primaryStage) {
        addImages(); // Add images for animations
        // The sounds that will be played when the game starts and when the drill moves upwards.
        MediaPlayer startGame = new MediaPlayer(new Media(new File("assets/extras/sound/challenge_complete.wav").toURI().toString()));
        startGame.setCycleCount(MediaPlayer.INDEFINITE);
        startGame.play();
        MediaPlayer rotorSound = new MediaPlayer(new Media(new File("assets/extras/sound/pod_rotor12.mp3").toURI().toString()));
        rotorSound.setCycleCount(MediaPlayer.INDEFINITE);
        // Animation timeline for up drill direction.
        Timeline upAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> updateFrame("up"))
        );
        upAnimation.setCycleCount(Animation.INDEFINITE);
        // Animation timeline for down drill direction.
        Timeline downAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> updateFrame("down"))
        );
        downAnimation.setCycleCount(Animation.INDEFINITE);
        // Animation timeline for right drill direction.
        Timeline rightAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> updateFrame("right"))
        );
        rightAnimation.setCycleCount(Animation.INDEFINITE);
        // Animation timeline for left drill direction.
        Timeline leftAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> updateFrame("left"))
        );
        leftAnimation.setCycleCount(Animation.INDEFINITE);
        // Gravity timeline.
        Timeline gravity = new Timeline();
        KeyFrame gravityKey = new KeyFrame(Duration.millis(0.1), event -> {
            if (gravity()) {
                drillImage.setLayoutY(drillImage.getLayoutY() + 0.03);
                drillBounds = drillImage.getBoundsInParent();
            }
        });
        gravity.getKeyFrames().add(gravityKey);
        gravity.setCycleCount(Animation.INDEFINITE);
        gravity.play();
        // The mechanism for moving the drill with the directional keys.
        Main.scene.setOnKeyPressed(event -> {
            startGame.stop(); // Ends the start music.
            // Up moving.
            if (event.getCode() == KeyCode.UP) {
                gravity.stop(); // Gravity's holding it while it won't affect it in flight.
                fuelCapacity -= 20; // More fuel is used while flying.
                upAnimation.play(); // Propeller movement.
                rotorSound.play(); // Propeller sound.
                // If there is nothing on the drill, it will move. And obeying the screen limits.
                if (isThereAnyElementAboveTheMachine() && drillBounds.getMinY() >= 0) {
                    moveUp();
                }
                // Down moving.
            } else if (event.getCode() == KeyCode.DOWN) {
                fuelCapacity -= 10; // More fuel is used while moving.
                // If the drill is on the floor, the animation runs.
                if (!gravity()) {
                    downAnimation.play();
                }
                // If there is a suitable object under the drill, it moves by digging.  And obeying the screen limits.
                if (isThereAnyElementUnderTheMachine() && drillBounds.getMaxY() <= 880 && !gravity()) {
                    moveDown();
                }
                // Right moving.
            } else if (event.getCode() == KeyCode.RIGHT) {
                fuelCapacity -= 10;
                if (!gravity()) {
                    rightAnimation.play();
                }
                if (isThereAnyElementRightOfTheMachine() && drillBounds.getMaxX() <= 900) {
                    moveRight();
                }
                // Left moving.
            } else if (event.getCode() == KeyCode.LEFT) {
                fuelCapacity -= 10;
                if (!gravity()) {
                    leftAnimation.play();
                }
                if (isThereAnyElementLeftOfTheMachine() && drillBounds.getMinX() >= 0) {
                    moveLeft();
                }
            }
        });
        // What happens when you stop pressing the key.
        Main.scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP) {
                rotorSound.stop(); // The sound of the propeller stops.
                upAnimation.stop(); // The up movement animation stops.
                gravity.play(); // Gravity starts.
            } else if (event.getCode() == KeyCode.DOWN) {
                downAnimation.stop();
                gravity.play();
            } else if (event.getCode() == KeyCode.RIGHT) {
                rightAnimation.stop();
                gravity.play();
            } else if (event.getCode() == KeyCode.LEFT) {
                leftAnimation.stop();
                gravity.play();
            }
        });
    }

    /**
     * From path takes photos of the drill.
     * Method to load drill images.
     */
    private void addImages() {
        // Load drill images
        drillImages = new Image[60];
        for (int i = 0; i < 60; i++) {
            drillImages[i] = new Image(String.format("assets/drill/drill_%s%d.png", (i < 9) ? "0" : "", i + 1));
        }
        // Set initial drill image and position
        drillImage = new ImageView(drillImages[35]);
        drillImage.setFitWidth(60);
        drillImage.setFitHeight(60);
        drillImage.setLayoutX(240);
        drillImage.setLayoutY(50);
        drillBounds = drillImage.getBoundsInParent();
        Main.mainPane.getChildren().add(drillImage);
        // Set images for up direction animation.
        upDrillImages = new Image[5];
        for (int i = 0; i < 5; i++) {
            upDrillImages[i] = drillImages[i + 22];
        }
        // Set images for down direction animation.
        downDrillImages = new Image[5];
        for (int i = 0; i < 5; i++) {
            downDrillImages[i] = drillImages[i + 39];
        }
        // Set images for right direction animation.
        rightDrillImages = new Image[5];
        for (int i = 0; i < 5; i++) {
            rightDrillImages[i] = drillImages[i + 55];
        }
        // Set images for left direction animation.
        leftDrillImages = new Image[5];
        for (int i = 0; i < 5; i++) {
            leftDrillImages[i] = drillImages[i];
        }
    }

    /**
     * Method to move the drill to up direction.
     * Moves the position of the drill to up by 5 units.
     */
    private void moveUp() {
        drillBounds = drillImage.getBoundsInParent();
        drillImage.setLayoutY(drillImage.getLayoutY() - 5);
    }

    /**
     * Method to move the drill to down direction.
     * Moves the position of the drill to down by 5 units.
     */
    private void moveDown() {
        drillBounds = drillImage.getBoundsInParent();
        drillImage.setLayoutY(drillImage.getLayoutY() + 5);
    }

    /**
     * Method to move the drill to right direction.
     * Moves the position of the drill to right by 5 units.
     */
    private void moveRight() {
        drillBounds = drillImage.getBoundsInParent();
        drillImage.setLayoutX(drillImage.getLayoutX() + 5);
    }

    /**
     * Method to move the drill to left direction.
     * Moves the position of the drill to left by 5 units.
     */
    private void moveLeft() {
        drillBounds = drillImage.getBoundsInParent();
        drillImage.setLayoutX(drillImage.getLayoutX() - 5);
    }

    private boolean isThereAnyElementAboveTheMachine() {
        for (Element element : GameScreen.elements) {
            if ((element.getElementBounds().getMinY() <= (drillBounds. getMinY() - 10) && element.getElementBounds().getMaxY() >= (drillBounds.getMinY() - 10)) &&
                    (((drillBounds.getMinX() + drillBounds.getMaxX()) / 2) >= element.getElementBounds().getMinX() && (((drillBounds.getMinX() + drillBounds.getMaxX()) / 2) <= element.getElementBounds().getMaxX()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there is any element under the drill machine.
     * If an element is found under the drill, it performs appropriate actions.
     *
     * @return true if there is no element under the drill or if the drill successfully interacts with the element,
     *         false otherwise
     */
    private boolean isThereAnyElementUnderTheMachine() {
        for (Element element : GameScreen.elements) {
            if ((element.getElementBounds().getMaxY() >= (drillBounds. getMaxY() - 10) && element.getElementBounds().getMinY() <= (drillBounds.getMaxY() - 10)) &&
                    (((drillBounds.getMinX() + drillBounds.getMaxX()) / 2) >= element.getElementBounds().getMinX() && (((drillBounds.getMinX() + drillBounds.getMaxX()) / 2) <= element.getElementBounds().getMaxX()))) {
                // Check if the element is lava and gravity is applied
                if (element.getElementName().contentEquals("lava") && !gravity()) {
                    lavaGameOver();
                }
                // Check if the element can be drilled and gravity is not applied
                if (element.isCanItBeDrilled() && !gravity()) {
                    // If the element is valuable, collect money and weight
                    if (element.isItValuable()) {
                        collectedMoney += element.getElementValue();
                        collectedWeight += element.getElementWeight();
                    }
                    GameScreen.elements.remove(element); // Remove the element from the game screen
                    Main.mainPane.getChildren().remove(element.getElementImage());
                    drillImage.setLayoutY(element.getElementLocationY()); // Move the drill to the element's location
                    return true;
                } else {
                    return false;
                }
            }
        }
        // If no element is found under the drill or all interactions are completed successfully
        return true;
    }

    /**
     * Checks if there is any element to the right of the drill machine.
     * If an element is found to the right of the drill, it performs appropriate actions.
     *
     * @return true if there is no element to the right of the drill or if the drill successfully interacts with the element,
     *         false otherwise
     */
    private boolean isThereAnyElementRightOfTheMachine() {
        for (Element element : GameScreen.elements ) {
            if ((drillBounds.getMaxX() - 1 >= element.getElementBounds().getMinX() && drillBounds.getMaxX() - 1 <= element.getElementBounds().getMaxX()) &&
                    ((((drillBounds.getMinY() + drillBounds.getMaxY()) / 2) >= element.getElementBounds().getMinY()) && (((drillBounds.getMinY() + drillBounds.getMaxY()) / 2) <= element.getElementBounds().getMaxY()))) {
                // Check if the element is lava and gravity is applied
                if (element.getElementName().contentEquals("lava") && !gravity()) {
                    lavaGameOver();
                }
                // Check if the element can be drilled and gravity is not applied
                if (element.isCanItBeDrilled() && !gravity()) {
                    // If the element is valuable, collect money and weight
                    if (element.isItValuable()) {
                        collectedMoney += element.getElementValue();
                        collectedWeight += element.getElementWeight();
                    }
                    // Remove the element from the game screen
                    GameScreen.elements.remove(element); // Remove the element from the game screen
                    Main.mainPane.getChildren().remove(element.getElementImage());
                    drillImage.setLayoutX(element.getElementLocationX()); // Move the drill to the element's location
                    return true;
                } else {
                    return false;
                }
            }
        }
        // If no element is found to the right of the drill or all interactions are completed successfully
        return true;
    }

    /**
     * Checks if there is any element to the left of the drill machine.
     * If an element is found to the left of the drill, it performs appropriate actions.
     *
     * @return true if there is no element to the left of the drill or if the drill successfully interacts with the element,
     *         false otherwise
     */
    private boolean isThereAnyElementLeftOfTheMachine() {
        for (Element element : GameScreen.elements) {
            if ((drillBounds.getMinX() + 1 <= element.getElementBounds().getMaxX() && drillBounds.getMinX() + 1 >= element.getElementBounds().getMinX()) &&
                    ((((drillBounds.getMinY() + drillBounds.getMaxY()) / 2) >= element.getElementBounds().getMinY()) && (((drillBounds.getMinY() + drillBounds.getMaxY()) / 2) <= element.getElementBounds().getMaxY()))) {
                // Check if the element is lava and gravity is applied
                if (element.getElementName().contentEquals("lava") && !gravity()) {
                    lavaGameOver();
                }
                // Check if the element can be drilled and gravity is not applied
                if (element.isCanItBeDrilled() && !gravity()) {
                    // If the element is valuable, collect money and weight
                    if (element.isItValuable()) {
                        collectedMoney += element.getElementValue();
                        collectedWeight += element.getElementWeight();
                    }
                    // Remove the element from the game screen
                    GameScreen.elements.remove(element);
                    Main.mainPane.getChildren().remove(element.getElementImage());
                    // Move the drill to the element's location
                    drillImage.setLayoutX(element.getElementLocationX());
                    return true;
                } else {
                    return false;
                }
            }
        }
        // If no element is found to the left of the drill or all interactions are completed successfully
        return true;
    }

    /**
     * Checks if the drill is affected by gravity, i.e., if there is an element below it.
     *
     * @return true if the drill is not affected by gravity (no element below), false if it is affected by gravity (element below)
     */
    private boolean gravity() {
        for (Element element : GameScreen.elements) {
            if (((element.getElementBounds().getMinY() <= (drillBounds.getMaxY() - 10)) && (((drillBounds.getMaxY() - 10) <= element.getElementBounds().getMaxY()))) && (((element.getElementBounds().getMinX() ) <= ((drillBounds.getMaxX() + drillBounds.getMinX()) / 2.0)) && (((drillBounds.getMaxX() + drillBounds.getMinX()) / 2.0) <= element.getElementBounds().getMaxX() ))) {
                // If an element is found below the drill, gravity is not applied
                return false;
            }
        }
        // If no element is found below the drill, gravity is applied
        return true;
    }

    /**
     * Handles the game over scenario when the drill machine encounters lava.
     * It stops the game, plays an explosion sound, and displays a game over message.
     */
    private void lavaGameOver() {
        // Stop fuelTimeline
        GameScreen.fuelTimeLine.stop();
        // Play explosion sound
        MediaPlayer explosionSound = new MediaPlayer(new Media(new File("assets/extras/sound/explosion1.mp3").toURI().toString()));
        explosionSound.play();

        // Remove all existing nodes from the game screen
        Main.mainPane.getChildren().remove(0, Main.mainPane.getChildren().size() - 1);
        // Display a red rectangle covering the entire game screen
        Rectangle greenGameOver = new Rectangle(900, 880);
        greenGameOver.setFill(Color.web("#922B21"));
        Main.mainPane.getChildren().add(greenGameOver);

        // Display "GAME OVER" message
        Label gameOver = new Label("GAME OVER");
        gameOver.setTextFill(Color.WHITE);
        gameOver.setFont(Font.font( 50));
        gameOver.setLayoutX(270);
        gameOver.setLayoutY(380);
        Main.mainPane.getChildren().add(gameOver);
    }

    /**
     * Updates the frame of the drill machine based on the given direction.
     * This method changes the image of the drill machine to simulate its movement in the specified direction.
     *
     * @param direction a String indicating the direction of the drill machine ("up", "down", "right", or "left")
     */
    private void updateFrame(String direction) {
        counterFrame++; // Increase the frame counter
        // Update the image of the drill machine based on the specified direction
        switch (direction) {
            case "up":
                drillImage.setImage(upDrillImages[counterFrame % 5]);
                break;
            case "down":
                drillImage.setImage(downDrillImages[counterFrame % 5]);
                break;
            case "right":
                drillImage.setImage(rightDrillImages[counterFrame % 5]);
                break;
            case "left":
                drillImage.setImage(leftDrillImages[counterFrame % 5]);
                break;
        }
    }
}
