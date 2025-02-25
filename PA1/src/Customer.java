public class Customer {

    private String type; // The customer's Payment type.
    private int money; // The money amount of the customer.
    private String stringMoney; // The money amount of the customer from string.
    private String choiceType; // The customer's choice.
    private int choiceNumber; // The customer's choice number.

    /**
     * This method is a constructor method that defines the specified properties of the customer.
     *
     * @param type Payment type.
     * @param choiceType The customer's choice.
     * @param choiceNumber The customer's choice number.
     * @param stringMoney The money amount of the customer from string.
     */
    public Customer(String type, String choiceType, int choiceNumber, String stringMoney) {
        this.type = type;
        this.choiceType = choiceType;
        this.choiceNumber = choiceNumber;
        this.stringMoney = stringMoney;
    }

    // Getters and setters methods.
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public String getStringMoney() {
        return stringMoney;
    }

    public void setStringMoney(String stringMoney) {
        this.stringMoney = stringMoney;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(String choiceType) {
        this.choiceType = choiceType;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
    }

    /**
     * This method allows the user to purchase products from the machine as desired.
     * The user can select the product by slot number, calorie count, protein count, carbohydrate count or fat count.
     * If the user's choice is successful, the user receives the product and the change.
     * Prints an information message if the selection is unsuccessful.
     *
     * @param customer the customer object on which the transaction will be made.
     * @param path path to the file where the information messages will be written.
     */
    public static byte buyProduct(Customer customer, String path) {

        Writer.writeToFile(path, "INPUT: " + customer.getType() + "\t" + customer.getStringMoney() + "\t" + customer.getChoiceType() + "\t" + customer.getChoiceNumber(), true, true); // Information about the purchase is written to the file.
        customer.setMoney(calculateMoney(customer.getStringMoney(), path)); // For calculate customer's money.

        int choiceNumber = customer.getChoiceNumber();   // The number of selections.
        int moneyAmount = customer.getMoney();  // The amount of money of the user.

        switch (customer.getChoiceType()) {
            case "NUMBER":  // If the user wants to select by slot number.
                if (choiceNumber < 0 || 23 < choiceNumber) { // Invalid number status.

                    Writer.writeToFile(path, "INFO: Number cannot be accepted. Please try again with another number.", true, true);
                    Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                    return -1;
                } else { // Valid number status.

                    if (GMMMachine.slotList.size() - 1 < choiceNumber || GMMMachine.amountList.get(choiceNumber) <= 0) { // If the machine is empty, the first product is inserted.

                        Writer.writeToFile(path, "INFO: This slot is empty, your money will be returned.", true, true);
                        Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                        return -1;
                    }
                    if (moneyAmount < GMMMachine.slotList.get(choiceNumber).getProductPrice()) { // Insufficient money situation.

                        Writer.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
                        Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                        return -1;
                    } else if (GMMMachine.slotList.get(choiceNumber).getProductPrice() < moneyAmount && 0 < GMMMachine.amountList.get(choiceNumber)) { // The purchase is successful.

                        Writer.writeToFile(path, "PURCHASE: You have bought one " + GMMMachine.slotList.get(choiceNumber).getProductName(), true, true);
                        Writer.writeToFile(path, "RETURN: Returning your change: " + (int)(moneyAmount - GMMMachine.slotList.get(choiceNumber).getProductPrice()) + " TL", true, true);
                        GMMMachine.amountList.set(choiceNumber, GMMMachine.amountList.get(choiceNumber) - 1);
                        break;
                    }
                }
                return 1;

            case "CALORIE": // If the user wants to select according to the calorie count.
                boolean isItSoldCal = false; // A checker to check whether the product has been sold.

                for (Product product : GMMMachine.slotList) {
                    // It is checked if the calorie of the product is within the range of +-5 to the selected calorie value and if the product is in stock.
                    if (product.getProductCalorie() <= choiceNumber + 5 && choiceNumber - 5 <= product.getProductCalorie() && 0 < GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product))) {

                        if (moneyAmount < product.getProductPrice()) { // Insufficient money situation.

                            Writer.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                            return -1;
                        } else { // The purchase is successful.

                            Writer.writeToFile(path, "PURCHASE: You have bought one " + product.getProductName(), true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + (int)(moneyAmount - product.getProductPrice()) + " TL", true, true);
                            GMMMachine.amountList.set(GMMMachine.slotList.indexOf(product), GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product)) - 1);
                            isItSoldCal = true; // The product has been sold.
                            break; // Exit from the loop.
                        }
                    }
                }
                if (!isItSoldCal) { // If the product is not sold.

                    Writer.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                    Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                    return -1;
                }
                return 1;

            case "PROTEIN": // If the user wants to select according to the amount of protein.
                boolean isItSoldPro = false; // A checker to check whether the product has been sold.

                for (Product product : GMMMachine.slotList) {
                    // It is checked if the protein of the product is within the range of +-5 to the selected protein value and if the product is in stock.
                    if (product.getProductProtein() <= choiceNumber + 5 && choiceNumber - 5 <= product.getProductProtein() && 0 < GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product))) {

                        if (moneyAmount < product.getProductPrice()) { // Insufficient money situation.

                            Writer.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                            return -1;
                        } else { // The purchase is successful.

                            Writer.writeToFile(path, "PURCHASE: You have bought one " + product.getProductName(), true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + (int)(moneyAmount - product.getProductPrice()) + " TL", true, true);
                            GMMMachine.amountList.set(GMMMachine.slotList.indexOf(product), GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product)) - 1);
                            isItSoldPro = true; // The product has been sold.
                            break; // Exit from the loop.
                        }
                    }
                }

                if (!isItSoldPro) { // If the product is not sold.

                    Writer.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                    Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                    return -1;
                }
                return 1;

            case "CARB":    // If the user wants to select according to the amount of carbohydrates.
                boolean isItSoldCar = false; // A checker to check whether the product has been sold.

                for (Product product : GMMMachine.slotList) {
                    // It is checked if the carbohydrate of the product is within the range of +-5 to the selected carbohydrate value and if the product is in stock.
                    if (product.getProductCarbonhydrate() <= choiceNumber + 5 && choiceNumber - 5 <= product.getProductCarbonhydrate() && 0 < GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product))) {

                        if (moneyAmount < product.getProductPrice()) { // Insufficient money situation.

                            Writer.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                            return -1;
                        } else { // The purchase is successful.

                            Writer.writeToFile(path, "PURCHASE: You have bought one " + product.getProductName(), true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + (int)(moneyAmount - product.getProductPrice()) + " TL", true, true);
                            GMMMachine.amountList.set(GMMMachine.slotList.indexOf(product), GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product)) - 1);
                            isItSoldCar = true; // The product has been sold.
                            break; // Exit from the loop.
                        }
                    }
                }

                if (!isItSoldCar) { // If the product is not sold.
                    Writer.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                    Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                    return -1;
                }
                return 1;

            case "FAT": // If the user wants to select according to the amount of fat.
                boolean isItSoldFat = false; // A checker to check whether the product has been sold.

                for (Product product : GMMMachine.slotList) {
                    // It is checked if the fat of the product is within the range of +-5 to the selected fat value and if the product is in stock.
                    if (product.getProductFat() <= choiceNumber + 5 && choiceNumber - 5 <= product.getProductFat() && 0 < GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product))) {

                        if (moneyAmount < product.getProductPrice()) { // Insufficient money situation.

                            Writer.writeToFile(path, "INFO: Insufficient money, try again with more money.", true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                            return -1;
                        } else { // The purchase is successful.

                            Writer.writeToFile(path, "PURCHASE: You have bought one " + product.getProductName(), true, true);
                            Writer.writeToFile(path, "RETURN: Returning your change: " + (int)(moneyAmount - product.getProductPrice()) + " TL", true, true);
                            GMMMachine.amountList.set(GMMMachine.slotList.indexOf(product), GMMMachine.amountList.get(GMMMachine.slotList.indexOf(product)) - 1);
                            isItSoldFat = true; // The product has been sold.
                            break; // Exit from the loop.
                        }
                    }
                }

                if (!isItSoldFat) { // If the product is not sold.

                    Writer.writeToFile(path, "INFO: Product not found, your money will be returned.", true, true);
                    Writer.writeToFile(path, "RETURN: Returning your change: " + moneyAmount + " TL", true, true);
                    return -1;
                }
                return 1;
        }
        return 1;
    }

    /**
     * This method calculates the amount of money of the user from banknotes.
     *
     * @param money string containing the user's banknotes.
     * @return user money.
     */
    public static int calculateMoney(String money, String pathForWriter) {

        String[] moneyBanknotes = money.split(" "); // Parses the string into banknotes.
        int moneyAmount = 0;

        for (String banknote : moneyBanknotes) {    //It only uses banknotes accepted by the machine.

            if (!banknote.equals("1") && !banknote.equals("5") && !banknote.equals("10") && !banknote.equals("20") && !banknote.equals("50") && !banknote.equals("100") && !banknote.equals("200")) {

                Writer.writeToFile(pathForWriter, "INFO: GMM only accepts 1, 5, 10, 20, 50, 100 and 200 TL as money.", true, true);
                continue;
            }

            moneyAmount += Integer.parseInt(banknote); // Calculate the amount of money.
        }

        return moneyAmount;
    }
}
