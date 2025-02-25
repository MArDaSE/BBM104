import java.util.ArrayList;

/**
 * This class ensures the correct placement of the products in the machine.
 * It realises product sales according to the user's choice.
 * And it contains a method that prints the current state of the machine.
 */
public class GMMMachine {

    public static ArrayList<Product> slotList = new ArrayList<>();  // A product array list to hold our product objects.
    public static ArrayList<Integer> amountList = new ArrayList<>();    // An integer array list to hold the number of products in the slots.

    /**
     * This method places the received product object in the appropriate slot.
     * Gives an error message if there is no available slot to place the product.
     *
     * @param product product object.
     * @param path the path to the file that the programme will read.
     * @throws Exception the machine will give an error when all slots are full.
     */
    public static void addProduct(Product product, String path) throws Exception {

        if (slotList.size() == 0) { // The first product is placed in the machine.
            // The first product is added to the machine.
            slotList.add(product);
            amountList.add(1);
            return;
        }

        for (byte i = 0; i < 25; i++) { // The product is added by checking the slots in the machine.

            if (i == 24) {  // If there is no available space in the slots for the product.

                Writer.writeToFile(path, "INFO: There is no available place to put " + product.getProductName(), true, true);
                if (isFull()) { // If the machine is completely full, it sends an information message.

                    throw new Exception("Slots are full");
                }
                break;
            }
            // If the corresponding slot is empty and there is the same product in the previous slot.
            if (slotList.size() == i) {
                if (slotList.get(i - 1).getProductName().equals(product.getProductName())) {    // If the same product has already been placed and there is space in the slot, it places the product in that slot.

                    int a = amountList.get(i);
                    if (a != 10) {

                        amountList.set(i, a + 1);   // Updates the number of products in the slot.
                        break;
                    }
                } else {    // The product is placed in another slot.

                    slotList.add(product);  // Adds the product to the slot.
                    amountList.add(1);
                }
                break;
            } else {    // The product is placed in another slot.

                if (slotList.get(i).getProductName().equals(product.getProductName())) {

                    int a = amountList.get(i);
                    if (a != 10) {

                        amountList.set(i, a + 1);
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method checks whether the machine is fully loaded.
     *
     * @return boolean value.
     */
    public static boolean isFull() {

        boolean isFull = false; // A checker is defined to determine whether the GMMMachine is full.

        if (amountList.size() == 24) { // If the size of the amountList is 24, i.e. all slots are full.

            for (int i : amountList) { // A loop is initiated to check all slots.

                if (i == 10) { // The amount is checked for each slot.

                    isFull = true; // If the slot is full, the controller is set to true.
                } else { // If a slot is not occupied, the controller is false and the loop is exited.

                    isFull = false;
                    break;
                }
            }
        }
        return isFull; // Returns the occupancy status of the GMMMachine.
    }

    /**
     * This method writes the current state of the machine to the file.
     *
     * @param path path to the file where the current state of the machine will be written.
     */
    public static void printStatement(String path) {
        // Print the title.
        Writer.writeToFile(path, "-----Gym Meal Machine-----", true, false);

        for (byte i = 0; i < slotList.size(); i++) { // Loop through the slot list is initialised.

            if (i % 4 == 0) { // A space is left after every 4 products.

                Writer.writeToFile(path, "", true, true);
            }

            if (amountList.get(i) == 0) { // It is checked if a product is in stock.
                // If not in stock, an appropriate message is printed.
                Writer.writeToFile(path, "___(" + 0 + ", " + 0 + ")___", true, false);
            } else { // If it is in stock, product name, calorie and quantity information is printed.

                Writer.writeToFile(path, slotList.get(i).getProductName() + "(" + slotList.get(i).getProductCalorie() + ", " + amountList.get(i) + ")___", true, false);
            }
        }

        if (slotList.size() < 24) { // If the slot list is less than 24, appropriate messages are printed for the missing slots.

            for (int a = slotList.size(); a < 24; a++) {

                if (a % 4 == 0) {

                    Writer.writeToFile(path, "", true, true);
                }
                Writer.writeToFile(path, "___(" + 0 + ", " + 0 + ")___", true, false);
            }
        }
        // The last part prints a few blank lines and a separator line.
        Writer.writeToFile(path, "", true, true);
        Writer.writeToFile(path, "----------", true, true);
    }
}
