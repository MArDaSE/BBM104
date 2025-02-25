import java.util.ArrayList;

public class Main { // Main class responsible for managing classrooms and decorations

    // Lists to store classrooms and decorations
    static ArrayList<Classroom> classroomList = new ArrayList<>();
    static ArrayList<Decoration> decorationList = new ArrayList<>();

    // Main method to execute the program
    public static void main(String[] args) {

        // Read classroom and decoration data from files
        readFromFileItems(args[0]); // Read classroom data
        readFromFileDecorate(args[1]); // Read decoration data
        // Process each classroom
        for (Classroom c : classroomList) {
            // Check if the wall type is Wallpaper or Paint
            if (c.getWallType().contentEquals("Wallpaper") || c.getWallType().contentEquals("Paint")) {
                // Generate formatted string for classrooms with Wallpaper or Paint walls
                String stringFormatted = String.format("Classroom %s used %dm2 of %s for walls and used %d Tiles for flooring, these costed %dTL.%n", c.getName(), (int)Math.ceil(c.getWallArea()), c.getWallType(), c.getTileAmountFloor(), (int)Math.ceil(c.getFloorPrice() + c.getWallPrice()));
                // Update total price
                Classroom.totalPrice += (int)Math.ceil(c.getFloorPrice() + c.getWallPrice());
                // Write formatted string to file
                Writer.writeToFile(args[2], stringFormatted, true, false);
            } else {
                // Generate formatted string for classrooms with Tile walls
                String stringFormatted = String.format("Classroom %s used %d Tiles for walls and used %d Tiles for flooring, these costed %dTL.%n", c.getName(), c.getTileAmountWall(), c.getTileAmountFloor(), (int)Math.ceil(c.getFloorPrice() + c.getWallPrice()));
                // Update total price
                Classroom.totalPrice += (int)Math.ceil(c.getFloorPrice() + c.getWallPrice());
                // Write formatted string to file
                Writer.writeToFile(args[2], stringFormatted, true, false);
            }
        }
        // Generate formatted string for total price
        String stringFormatted = String.format("Total price is: %dTL.", Classroom.totalPrice);
        // Write formatted string to file
        Writer.writeToFile(args[2], stringFormatted, true, false);
    }

    // Method to read classroom data from file
    public static void readFromFileItems (String path) {
        // Read data from file
        String[] items = Reader.readFile(path, true, true);
        // Process each line
        for (String string : items) {
            // Split line into parts
            String[] inputItems = string.split("\t");

            // Check if it's a classroom
            if (inputItems[0].contentEquals("CLASSROOM")) {

                // Generate and add classroom to the list
                classroomList.add(generateBuild(inputItems[1], inputItems[2], Integer.parseInt(inputItems[3]), Integer.parseInt(inputItems[4]), Integer.parseInt(inputItems[5]))) ;
            } else {
                // Check if it's a decoration
                if (inputItems.length != 5) {
                    // Add decoration to the list
                    decorationList.add(Decoration.getDecoration(inputItems[1], inputItems[2], Integer.parseInt(inputItems[3])));
                } else {
                    // Add decoration with tile area to the list
                    decorationList.add(Decoration.getDecoration(inputItems[1], inputItems[2], Integer.parseInt(inputItems[3]), Integer.parseInt(inputItems[4])));
                }
            }
        }
    }

    // Method to read decoration data from file and decorate classrooms
    public static void readFromFileDecorate (String path) {
        // Read data from file
        String[] decorates = Reader.readFile(path, true, true);
        // Process each line
        for (String string : decorates) {
            // Split line into parts
            String[] inputDecorates = string.split("\t");
            // Process each classroom
            for (Classroom c : classroomList) {
                // Check if the classroom matches the decoration
                if (c.getName().contentEquals(inputDecorates[0])) {
                    // Check each decoration
                    for (Decoration d : decorationList) {
                        // Check if the decoration matches the wall decoration and it's a Tile
                        if (d.getName().contentEquals(inputDecorates[1]) && d.getType().contentEquals("Tile")) {
                            // Calculate and set wall price
                            c.setWallPrice(Math.ceil(c.getWallArea() / d.getTileArea()) * d.getPrice());
                            // Set wall type to Tile
                            c.setWallType("Tile");
                            // Calculate and set tile amount for wall
                            c.setTileAmountWall((int)Math.ceil(c.getWallArea() / d.getTileArea()));
                        } else if (d.getName().contentEquals(inputDecorates[1])) {
                            // Calculate and set wall price
                            c.setWallPrice(c.getWallArea() * d.getPrice());
                            // Set wall type
                            c.setWallType(d.getType());
                        }
                    }
                    // Check each decoration
                    for (Decoration d : decorationList) {
                        // Check if the decoration matches the floor decoration
                        if (d.getName().contentEquals(inputDecorates[2])) {
                            // Calculate and set floor price
                            c.setFloorPrice(Math.ceil(c.getFloorArea() / d.getTileArea()) * d.getPrice());
                            // Set floor type to Tile
                            c.setFloorType("Tile");
                            // Calculate and set tile amount for floor
                            c.setTileAmountFloor((int)Math.ceil(c.getFloorArea() / d.getTileArea()));
                        }
                    }
                }
            }
        }
    }

    // Method to generate a classroom using the Builder pattern
    public static Classroom generateBuild(String name, String type, int width, int length, int height) {
        // Build and return the classroom
        Classroom classroom = Builder.classroomBuilder()
                .setName(name)
                .setType(type)
                .setWidth(width)
                .setLength(length)
                .setHeight(height)
                .build();

        return classroom;
    }
}
