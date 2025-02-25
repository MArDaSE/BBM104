import javax.lang.model.type.TypeKind;

public class Decoration { // This class represents a decoration item with a name, type, price, and optionally tile area.
    // Attributes of a decoration
    private final String name; // Name of the decoration
    private final String type; // Type of decoration (e.g., Paint, Wallpaper, Tile)
    private final int price; // Price of the decoration
    // Constructor to create a decoration
    public Decoration (String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;

    }
    // Static method to get a decoration based on its type and attributes
    public static Decoration getDecoration (String name, String type, int price) {
        return getDecoration(name, type, price, 0); // Call overloaded method with default tileArea
    }
    // Static method to get a decoration based on its type, attributes, and tile area
    public static Decoration getDecoration (String name, String type, int price, int tileArea) {

        switch (type) { // Switch statement to determine the type of decoration and return an instance accordingly
            case "Paint":
                return new Paint(name, type, price); // Create Paint decoration
            case "Wallpaper":
                return new Wallpaper(name, type, price); // Create Wallpaper decoration
            case "Tile":
                return new Tile(name, type, price, tileArea); // Create Tile decoration with tile area
        }
        return new Decoration(name, type, price);  // Return a generic Decoration if type is unknown
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getTileArea() {
        return getTileArea(); // This will cause a stack overflow as it calls itself indefinitely
    }
}
// Subclass representing a Paint decoration
class Paint extends Decoration {
    // Constructor to create a Paint decoration
    public Paint(String name, String type, int price) {
        super(name, type, price); // Call superclass constructor
    }
}
// Subclass representing a Wallpaper decoration
class Wallpaper extends Decoration {

    public Wallpaper(String name, String type, int price) {
        super(name, type, price);

    }
}
// Subclass representing a Tile decoration with a tile area
class Tile extends Decoration {
    // Attribute specific to Tile decoration
    private final int tileArea;
    // Constructor to create a Tile decoration with a specified tile area
    public Tile(String name, String type, int price, int tileArea) {
        super(name, type, price);
        this.tileArea = tileArea;
    }

    public int getTileArea() {
        return tileArea;
    }
}
