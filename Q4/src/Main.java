import java.util.ArrayList;

public class Main {
    // ArrayList to store products
    private static final ArrayList<Product> products = new ArrayList<>();

    /**
     * Main method to read commands from a file and process them.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("This program expects two arguments: <input> <output>");
        } else {
            // Read lines from the file
            String[] lines = Reader.readFile(args[0], true, true);
            try {
                // Process each line
                for (String line : lines) {
                    processManager(line, args[1]);
                }
            } catch (NullPointerException e) {
                System.out.println("No commands found");
            }
        }
    }

    /**
     * Process a command line.
     *
     * @param line the command line to process
     * @param <T>  the type of the command line
     */
    private static <T extends String> void processManager(T line, String path) {
        String[] command = line.split("\t");
        switch (command[0]) {
            case "ADD":
                addProduct(command);
                break;
            case "REMOVE":
                removeProduct(command[1], path);
                break;
            case "DISPLAY":
                displayProducts(path);
                break;
            case "SEARCHBYBARCODE":
                searchProduct(command[1], "byBarcode", path);
                break;
            case "SEARCHBYNAME":
                searchProduct(command[1], "byName", path);
                break;
        }
    }

    /**
     * Add a product based on the command line.
     *
     * @param productFeatures features of the product to add
     */
    private static void addProduct(String[] productFeatures) {
        switch (productFeatures[1]) {
            case "Book":
                products.add(new Book<>(productFeatures[2], productFeatures[3], productFeatures[4], Double.parseDouble(productFeatures[5])));
                break;
            case "Toy":
                products.add(new Toy<>(productFeatures[2], productFeatures[3], productFeatures[4], Double.parseDouble(productFeatures[5])));
                break;
            case "Stationery":
                products.add(new Stationery<>(productFeatures[2], productFeatures[3], productFeatures[4], Double.parseDouble(productFeatures[5])));
                break;
        }
    }

    /**
     * Remove a product by its barcode.
     *
     * @param barcode the barcode of the product to remove
     * @param <T>     the type of the barcode
     */
    private static <T extends String> void removeProduct(T barcode, String path) {
        Writer.writeToFile(path, "REMOVE RESULTS:", true, true);
        boolean isItRemoved = false;
        for (Product product : products) {
            if (product.getBarcode().contentEquals(barcode)) {
                products.remove(product);
                isItRemoved = true;
                break;
            }
        }
        if (isItRemoved) {
            Writer.writeToFile(path, "Item is removed.", true, true);
        } else {
            Writer.writeToFile(path, "Item is not found.", true, true);
        }
        Writer.writeToFile(path, "------------------------------", true, true);
    }

    /**
     * Display all products.
     */
    private static void displayProducts(String path) {
        Writer.writeToFile(path, "INVENTORY:", true, true);
        if (products.isEmpty()) {
            Writer.writeToFile(path, "No products found.", true, true);
        } else {
            printerOrdered(path);
        }
        Writer.writeToFile(path, "------------------------------", true, true);
    }

    /**
     * Search for a product by barcode or name.
     *
     * @param keyword the keyword to search for
     * @param type    the type of search (byBarcode or byName)
     * @param <T>     the type of the keyword
     */
    private static <T extends String> void searchProduct(T keyword, String type, String path) {
        Writer.writeToFile(path, "SEARCH RESULTS:", true, true);
        boolean isThere = false;
        switch (type) {
            case "byBarcode":
                for (Product product : products) {
                    if (product.getBarcode().contentEquals(keyword)) {
                        printer(product, path);
                        isThere = true;
                        break;
                    }
                }
                break;
            case "byName":
                for (Product product : products) {
                    if (product.getName().contentEquals(keyword)) {
                        printer(product, path);
                        isThere = true;
                        break;
                    }
                }
                break;
        }
        if (!isThere) {
            Writer.writeToFile(path, "Item is not found.", true, true);
        }
        Writer.writeToFile(path, "------------------------------", true, true);
    }

    /**
     * Print all products ordered by type.
     */
    private static void printerOrdered(String path) {
        for (Product product : products) {
            if (product instanceof Book) {
                printer(product, path);
            }
        }
        for (Product product : products) {
            if (product instanceof Toy) {
                printer(product, path);
            }
        }
        for (Product product : products) {
            if (product instanceof Stationery) {
                printer(product, path);
            }
        }
    }

    /**
     * Print details of a product.
     *
     * @param product the product to print details of
     * @param <T>     the type of the product
     */
    private static <T extends Product> void printer(T product, String path) {
        if (product instanceof Book) {
            Writer.writeToFile(path, String.format("Author of the %s is %s. Its barcode is %s and its price is %s",
                    product.getName(), ((Book<?>) product).getAuthor(), product.getBarcode(), product.getPrice()), true, true);
        } else if (product instanceof Toy) {
            Writer.writeToFile(path, String.format("Color of the %s is %s. Its barcode is %s and its price is %s",
                    product.getName(), ((Toy<?>) product).getColor(), product.getBarcode(), product.getPrice()), true, true);
        } else if (product instanceof Stationery) {
            Writer.writeToFile(path, String.format("Kind of the %s is %s. Its barcode is %s and its price is %s",
                    product.getName(), ((Stationery<?>) product).getKind(), product.getBarcode(), product.getPrice()), true, true);
        }
    }
}