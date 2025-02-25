public class Builder { // This class represents a builder for creating instances of the Classroom class
    // Attributes for defining the properties of a classroom
    private String name;
    private String type; // Type of the classroom (e.g., Circle, Rectangle)
    private int width;
    private int length;
    private int height;
    // Static method to create a new instance of the Builder class for building a classroom
    public static Builder classroomBuilder() {
        return new Builder(); // Return a new instance of Builder
    }
    // Method to build a classroom object based on the provided attributes
    public Classroom build() {
        Classroom classroom = new Classroom(); // Create a new instance of the Classroom class
        // Set the attributes of the classroom using the builder's attributes
        classroom.setName(name);
        classroom.setType(type);
        classroom.setWidth(width);
        classroom.setLength(length);
        classroom.setHeight(height);
        classroom.setFloorArea(calculateFloorArea(type)); // Calculate and set the floor area of the classroom
        classroom.setWallArea(calculateWallArea(type)); // Calculate and set the wall area of the classroom

        return classroom; // Return the built classroom object
    }
    // Method to set the name of the classroom
    public Builder setName(String name) {
        this.name = name;
        return this; // Return the builder object for method chaining
    }
    // Method to set the type of the classroom
    public Builder setType(String type) {
        this.type = type;
        return this;
    }
    // Method to set the width of the classroom
    public Builder setWidth(int width) {
        this.width = width;
        return this;
    }
    // Method to set the length of the classroom
    public Builder setLength(int length) {
        this.length = length;
        return this;
    }
    // Method to set the height of the classroom
    public Builder setHeight(int height) {
        this.height = height;
        return this;
    }
    // Method to calculate the floor area of the classroom based on its type
    public double calculateFloorArea(String type) {
        if (type.contentEquals("Circle")) { // Calculate the floor area of a circular classroom
            return Math.PI * ((double)width / 2) * ((double)width / 2);
        } else {
            return width * length; // Calculate the floor area of a rectangular classroom
        }
    }
    // Method to calculate the wall area of the classroom based on its type
    public double calculateWallArea(String type) {
        if (type.contentEquals("Circle")) { // Calculate the wall area of a circular classroom
            return 2 * Math.PI * ((double)width / 2) * height;
        } else { // Calculate the wall area of a rectangular classroom
            return (width * 2 + length * 2) * height;
        }
    }
}

// Class representing a classroom
class Classroom {
    // Attributes of a classroom
    private String name;
    private String type;
    private int width;
    private int length;
    private int height;
    private double floorArea;
    private double wallArea;
    private double floorPrice;
    private double wallPrice;
    private String floorType;
    private String wallType;
    private int tileAmountFloor;
    private int tileAmountWall;
    public static int totalPrice;

    // Setter and getter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFloorArea(double floorArea) {
        this.floorArea = floorArea;
    }

    public void setWallArea(double wallArea) {
        this.wallArea = wallArea;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public double getFloorArea() {
        return floorArea;
    }

    public double getWallArea() {
        return wallArea;
    }

    public double getFloorPrice() {
        return floorPrice;
    }

    public double getWallPrice() {
        return wallPrice;
    }

    public void setFloorPrice(double floorPrice) {
        this.floorPrice = floorPrice;
    }

    public void setWallPrice(double wallPrice) {
        this.wallPrice = wallPrice;
    }

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public String getWallType() {
        return wallType;
    }

    public void setWallType(String wallType) {
        this.wallType = wallType;
    }

    public int getTileAmountFloor() {
        return tileAmountFloor;
    }

    public void setTileAmountFloor(int tileAmountFloor) {
        this.tileAmountFloor = tileAmountFloor;
    }

    public int getTileAmountWall() {
        return tileAmountWall;
    }

    public void setTileAmountWall(int tileAmountWall) {
        this.tileAmountWall = tileAmountWall;
    }
}