import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an element in the game, such as soil, obstacles, or valuable gems.
 * Each element has properties like its name, whether it can be drilled, if it's valuable, its value, weight, location, and image.
 */
public class Element {

    private String elementName; // Name of the element
    private boolean canItBeDrilled; // Whether the element can be drilled
    private boolean isItValuable; // Whether the element is valuable
    private double elementValue; // Value of the element
    private double elementWeight; // Weight of the element
    private double elementLocationX; // X-coordinate of the element's location
    private double elementLocationY; // Y-coordinate of the element's location
    public Bounds elementBounds; // Bounds of the element
    private Image[] elementsImages; // Images representing the element
    private ImageView elementImage; // ImageView displaying the element

    /**
     * Constructs an element with the given type, location, and image number.
     *
     * @param type            type of the element (e.g., soil, top, obstacle)
     * @param elementLocationX X-coordinate of the element's location
     * @param elementLocationY Y-coordinate of the element's location
     * @param imageNumber     number representing the image to be displayed for the element
     */
    public Element(String type, double elementLocationX, double elementLocationY, int imageNumber) {
        switch (type) {
            case "soil":
                // Soil element
                elementsImages = new Image[] {
                        new Image("assets/underground/soil_01.png"),
                        new Image("assets/underground/soil_02.png"),
                        new Image("assets/underground/soil_03.png"),
                        new Image("assets/underground/soil_04.png"),
                        new Image("assets/underground/soil_05.png")
                };
                elementName = "soil";
                canItBeDrilled = true;
                isItValuable = false;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "top":
                // Top element
                elementsImages = new Image[]{
                        new Image("assets/underground/top_01.png"),
                        new Image("assets/underground/top_02.png"),
                };
                elementName = "top";
                canItBeDrilled = true;
                isItValuable = false;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "top_concrete":
                // Concrete top element
                elementsImages = new Image[]{
                        new Image("assets/underground/top_concrete_01.png"),
                        new Image("assets/underground/top_concrete_02.png"),
                        new Image("assets/underground/top_concrete_03.png")
                };
                elementName = "top_concrete";
                canItBeDrilled = false;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "lava":
                // Lava element
                elementsImages = new Image[]{
                        new Image("assets/underground/lava_01.png"),
                        new Image("assets/underground/lava_02.png"),
                        new Image("assets/underground/lava_03.png")
                };
                elementName = "lava";
                canItBeDrilled = false;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "obstacle":
                // Obstacle element
                elementsImages = new Image[]{
                        new Image("assets/underground/obstacle_01.png"),
                        new Image("assets/underground/obstacle_02.png"),
                        new Image("assets/underground/obstacle_03.png")
                };
                elementName = "obstacle";
                canItBeDrilled = false;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "amazonite":
                // Amazonite element
                elementsImages = new Image[]{
                        new Image("assets/underground/valuable_amazonite.png"),
                };
                elementName = "amazonite";
                canItBeDrilled = true;
                isItValuable = true;
                elementValue = 500000;
                elementWeight = 120;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "diamond":
                // Diamond element
                elementsImages = new Image[]{
                        new Image("assets/underground/valuable_diamond.png"),
                };
                elementName = "diamond";
                canItBeDrilled = true;
                isItValuable = true;
                elementValue = 100000;
                elementWeight = 100;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "emerald":
                // Emerald element
                elementsImages = new Image[]{
                        new Image("assets/underground/valuable_emerald.png"),
                };
                elementName = "emerald";
                canItBeDrilled = true;
                isItValuable = true;
                elementValue = 5000;
                elementWeight = 60;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "platinum":
                // Platinum element
                elementsImages = new Image[]{
                        new Image("assets/underground/valuable_platinum.png"),
                };
                elementName = "platinum";
                canItBeDrilled = true;
                isItValuable = true;
                elementValue = 750;
                elementWeight = 30;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            case "ruby":
                // Ruby element
                elementsImages = new Image[]{
                        new Image("assets/underground/valuable_ruby.png"),
                };
                elementName = "ruby";
                canItBeDrilled = true;
                isItValuable = true;
                elementValue = 20000;
                elementWeight = 80;
                this.elementLocationX = elementLocationX;
                this.elementLocationY = elementLocationY;
                elementImage = new ImageView(elementsImages[imageNumber]);
                elementImage.setFitWidth(60);
                elementImage.setFitHeight(60);
                elementImage.setLayoutX(elementLocationX);
                elementImage.setLayoutY(elementLocationY);
                elementBounds = elementImage.getBoundsInParent();
                break;
            }
    }

    /**
     * Checks if the element can be drilled.
     *
     * @return true if the element can be drilled, otherwise false
     */
    public boolean isCanItBeDrilled() {
        return canItBeDrilled;
    }

    /**
     * Checks if the element is valuable.
     *
     * @return true if the element is valuable, otherwise false
     */
    public boolean isItValuable() {
        return isItValuable;
    }

    /**
     * Gets the value of the element.
     *
     * @return the value of the element
     */
    public double getElementValue() {
        return elementValue;
    }

    /**
     * Gets the weight of the element.
     *
     * @return the weight of the element
     */
    public double getElementWeight() {
        return elementWeight;
    }

    /**
     * Gets the X-coordinate of the element's location.
     *
     * @return the X-coordinate of the element's location
     */
    public double getElementLocationX() {
        return elementLocationX;
    }

    /**
     * Gets the Y-coordinate of the element's location.
     *
     * @return the Y-coordinate of the element's location
     */
    public double getElementLocationY() {
        return elementLocationY;
    }

    /**
     * Gets the ImageView of the element.
     *
     * @return the ImageView of the element
     */
    public ImageView getElementImage() {
        return elementImage;
    }

    /**
     * Gets the bounds of the element.
     *
     * @return the bounds of the element
     */
    public  Bounds getElementBounds() {
        return elementBounds;
    }

    /**
     * Gets the name of the element.
     *
     * @return the name of the element
     */
    public String getElementName() {
        return elementName;
    }
}