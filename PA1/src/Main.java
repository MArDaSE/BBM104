import java.util.ArrayList;

public class Main {

    private static ArrayList<Product> productList = new ArrayList<>(); // An array list created to hold the products.

    /**
     * This method is the main method of the machine program.
     * Reads the product list from the file.
     * Prints the status of the machine.
     * Reads the purchase list from the file.
     *
     * @param args arguments.
     */
    public static void main(String[] args) {

        getProductList(args[0], args[2]);

        GMMMachine.printStatement(args[2]);

        getPurchaseList(args[1], args[2]);

        GMMMachine.printStatement(args[2]);

    }

    /**
     * This method reads the products list from the file.
     * It creates new objects from the product class with the information it receives and sends these objects to the product placement method.
     *
     * @param pathForProduct The path to the file where the method will read the products list.
     * @param pathForWriter The path to the file where the method will print the purchase transactions.
     */
    public static void getProductList(String pathForProduct, String pathForWriter) {

        String[] fileOfProducts = Reader.readFile(pathForProduct, true, true); // The products array it took from the file.

        for (short i = 0; i < fileOfProducts.length; i++) {

            String[] products = fileOfProducts[i].split("\t"); // Pieces for comfortable use of the string.
            String[] nutritionalValues = products[2].split(" "); // Nutritional values of the product.

            String name = products[0];
            double price = Double.parseDouble(products[1]);
            double protein = Double.parseDouble(nutritionalValues[0]);
            double carbohydrate = Double.parseDouble(nutritionalValues[1]);
            double fat = Double.parseDouble(nutritionalValues[2]);

            productList.add(new Product(name, price, protein, carbohydrate, fat)); // Creates a new object and adds it to the array list.
        }

        for (Product product : productList) {

            try {
                GMMMachine.addProduct(product, pathForWriter); // Sends the product to the add method to add it to the machine.
            } catch (Exception e) {
                Writer.writeToFile(pathForWriter, "INFO: The machine is full!", true, true); // Error message given when trying to add product to a fully loaded machine.
                break;
            }
        }
    }

    /**
     * This method reads the purchase list from the file.
     * With the information it receives, it creates a new object from the Customer class and sends this object to the purchase method.
     *
     * @param pathForPurchase The path to the file where the method will read the purchase list.
     * @param pathForWriter The path to the file where the method will print the purchase transactions.
     */
    public static void getPurchaseList(String pathForPurchase, String pathForWriter) {

        String[] fileOfPurchase = Reader.readFile(pathForPurchase, true, true); // The purchase arrays it took from the file.

            for (short i = 0; i < fileOfPurchase.length; i++) {

                    String[] purchaseElements = fileOfPurchase[i].split("\t");  // Pieces for comfortable use of the string.
                    String purchaseType = purchaseElements[0];
                    String purchaseStringMoney = purchaseElements[1];
                    String purchaseChoiceType = purchaseElements[2];
                    int purchaseNumber = Integer.parseInt(purchaseElements[3]);

                    Customer customer = new Customer(purchaseType, purchaseChoiceType, purchaseNumber, purchaseStringMoney); // Creates a new customer object.
                    Customer.buyProduct(customer, pathForWriter); // Sends the customer object to the purchase method.
            }
    }
}
