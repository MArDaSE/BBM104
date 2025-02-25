import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is a parent class of other Bus classes.
 * It contains the properties of a bus.
 * It contains the necessary methods to create a new bus.
 * It contains the method that reads command lines.
 * It also contains other methods that perform the required action according to the command lines.
 */
public class Bus {

    private String busType; // type of the bus
    private String voyageID; // ID of the bus
    private String departure; // departure point of the bus
    private String destination; // destination point of the bus
    private int seatRows; // number of rows of seats in the bus
    private double seatFee; // general voyage fee of the bus
    private double revenue; // revenue of the bus
    protected ArrayList<Seat> seats = new ArrayList<>(); // seats of the bus

    /**
     * Constructor for the class.
     *
     * @param busType type of the bus
     * @param voyageID ID of the bus
     * @param departure departure point of the bus
     * @param destination destination point of the bus
     * @param seatRows number of rows of seats in the bus
     * @param seatFee general voyage fee of the bus
     */
    public Bus(String busType, String voyageID, String departure, String destination,
               int seatRows, double seatFee) {
        this.busType = busType;
        this.voyageID = voyageID;
        this.departure = departure;
        this.destination = destination;
        this.seatRows = seatRows;
        this.seatFee = seatFee;
    }

    /**
     * Constructor for initialize the entire system.
     *
     * @param commandRow an array of strings representing the booking commands
     * @param buses an ArrayList of Bus objects representing all buses
     * @param outputFilePath the path to the output file where the results of booking operations will be written
     */
    public Bus(String[] commandRow, ArrayList<Bus> buses, String outputFilePath) {
        // Calls the commandReader method to process booking commands.
        commandReader(commandRow, buses, outputFilePath);
    }

    /**
     * Initializes a new voyage based on the provided row of data.
     * It is overridden and filled in by subclasses.
     *
     * @param row a string representing the data row for initializing the voyage
     * @param outputFilePath the path to the output file where the results of the voyage initialization will be written
     */
    public void initializeVoyage(String row, String outputFilePath) {}

    /**
     * Generates and writes the seat layout to the specified output file based on the type of the bus.
     * It is overridden and filled in by subclasses.
     *
     * @param outputFilePath the path to the output file where the seat layout will be written
     */
    public void seatLayout(String outputFilePath) {}

    /**
     * Adds seats to the bus adds seats to the bus based on the type of the bus.
     * It is overridden and filled in by subclasses.
     */
    public void addSeats() {}

    /**
     * Creates a new Bus object based on the provided command row.
     *
     * @param commandRow An array of strings representing the data for initializing the bus.
     * @return A new Bus object based on the provided command row.
     */
    private Bus busFactory(String[] commandRow) {
        // Returns StandardBus if the bus type is "Standard".
        if (commandRow[1].contentEquals("Standard")) {

            return new StandardBus(commandRow[1], commandRow[2], commandRow[3], commandRow[4],
                    Integer.parseInt(commandRow[5]), Double.parseDouble(commandRow[6]),
                    Double.parseDouble(commandRow[7]));
            // Returns PremiumBus if the bus type is "Premium".
        } else if (commandRow[1].contentEquals("Premium")) {

            return new PremiumBus(commandRow[1], commandRow[2], commandRow[3], commandRow[4],
                    Integer.parseInt(commandRow[5]), Double.parseDouble(commandRow[6]),
                    Double.parseDouble(commandRow[7]), Double.parseDouble(commandRow[8]));
            // Returns Minibus if the bus type is not "Standard" or "Premium".
        } else {

            return new Minibus(commandRow[1], commandRow[2], commandRow[3], commandRow[4],
                    Integer.parseInt(commandRow[5]), Double.parseDouble(commandRow[6]));
        }
    }

    /**
     * Generates and writes a Z Report
     * to the specified output file based on the provided list of buses.
     * The Z Report includes seat layouts and revenue information for each bus voyage.
     *
     * @param buses an ArrayList of Bus objects representing all buses
     * @param outputFilePath the path to the output file where the Z Report will be written
     * @param isLastRow a boolean value indicating whether the Z Report is the last row of a report series
     *                      if true, additional formatting will be applied to the report
     *                      if false, no additional formatting will be applied.
     *
     */
    public void zReport(ArrayList<Bus> buses, String outputFilePath, boolean isLastRow) {
        // Write Z Report header.
        Writer.writeToFile(outputFilePath, "Z Report:", true, true);
        Writer.writeToFile(outputFilePath, "----------------", true, true);

        if (isLastRow) {
            // Check if there are any voyages available.
            if (buses.isEmpty()) {
                // Write message if no voyages are available.
                Writer.writeToFile(outputFilePath, "No Voyages Available!", true, true);
                Writer.writeToFile(outputFilePath, "----------------", true, false);

            } else if (buses.size() == 1) {
                // Write seat layout and revenue for a single voyage.
                buses.get(0).seatLayout(outputFilePath);

                Writer.writeToFile(outputFilePath, String.format("Revenue: %.2f", buses.get(0).getRevenue()), true, true);
                Writer.writeToFile(outputFilePath, "----------------", true, false);

            } else {
                // Sort voyages by ID to comply with the sample io files, it sorts the buses
                // according to their voyage id from smallest to largest.
                ArrayList<Integer> sortedVoyageIDS = new ArrayList<>();
                for (Bus bus : buses) {

                    sortedVoyageIDS.add(Integer.parseInt(bus.getVoyageID()));
                }
                Collections.sort(sortedVoyageIDS);
                // Write seat layout and revenue for each voyage.
                int counter = 0; // A counter to use the "Z_REPORT" command correctly.
                for (int i : sortedVoyageIDS) {

                    for (Bus bus : buses) {

                        if ( bus.getVoyageID().contentEquals(Integer.toString(i))) {

                            counter++;

                            if (buses.size() == counter) {

                                bus.seatLayout(outputFilePath);

                                Writer.writeToFile(outputFilePath, String.format("Revenue: %.2f", bus.getRevenue()), true, true);
                                Writer.writeToFile(outputFilePath, "----------------", true, false);

                            } else {

                                bus.seatLayout(outputFilePath);

                                Writer.writeToFile(outputFilePath, String.format("Revenue: %.2f", bus.getRevenue()), true, true);
                                Writer.writeToFile(outputFilePath, "----------------", true, true);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            // Write seat layout and revenue for each voyage without additional formatting.
            if (buses.isEmpty()) {

                Writer.writeToFile(outputFilePath, "No Voyages Available!", true, true);
                Writer.writeToFile(outputFilePath, "----------------", true, true);

            } else {

                ArrayList<Integer> sortedVoyageIDS = new ArrayList<>();
                for (Bus bus : buses) {

                    sortedVoyageIDS.add(Integer.parseInt(bus.getVoyageID()));
                }
                Collections.sort(sortedVoyageIDS);

                for (int i : sortedVoyageIDS) {

                    for (Bus bus : buses) {

                        if ( bus.getVoyageID().contentEquals(Integer.toString(i))) {

                            bus.seatLayout(outputFilePath);

                            Writer.writeToFile(outputFilePath, String.format("Revenue: %.2f", bus.getRevenue()), true, true);
                            Writer.writeToFile(outputFilePath, "----------------", true, true);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Sells tickets for a specific voyage and updates the revenue accordingly.
     *
     * @param voyageID the ID of the voyage for which tickets will be sold
     * @param seatNumbers a string representing the seat numbers to be sold, separated by underscores
     *                    example: "1_2_3" represents selling tickets for seats 1, 2, and 3
     * @param buses an ArrayList of Bus objects representing all buses and their voyages
     *              this parameter is used to find the bus associated with the provided voyage ID
     * @param outputFilePath the path to the output file where the result of the ticket selling will be written
     *                       his parameter is used for writing the result to the output file
     */
    public void sellTicket(String voyageID, String seatNumbers, ArrayList<Bus> buses, String outputFilePath) {
        // Loop through all buses to find the bus associated with the provided voyage ID.
        for (Bus bus : buses) {
            // Check if the current bus has the provided voyage ID.
            if (bus.getVoyageID().contentEquals(voyageID)) {
                // Variable to store the total price of sold tickets.
                double priceOfSoldTickets = 0;
                // Split the seat numbers string into individual seat numbers.
                String[] seatNumbersForSelling = seatNumbers.split("_");
                // Loop through each seat number for selling.
                for (String seatNumber : seatNumbersForSelling) {
                    // Loop through each seat of the current bus.
                    for (Seat seat : bus.getSeats()) {
                        // Check if the current seat matches the seat number for selling.
                        if (seat.getSeatNumber() == Integer.parseInt(seatNumber)) {
                            // Check if the seat is available (marked as '*').
                            if (seat.getSeatSituation() == '*') {
                                // Mark the seat as sold (marked as 'X').
                                seat.setSeatSituation('X');
                                // Add the price of the sold ticket to the total price.
                                priceOfSoldTickets += seat.getSeatFee();
                            }
                            break;
                        }
                    }
                }
                // Update the revenue of the bus.
                bus.revenue += priceOfSoldTickets;
                // Write the result of the ticket selling to the output file.
                Writer.writeToFile(outputFilePath, String.format("Seat %s of the Voyage %s from %s to %s was successfully sold for %.2f TL.",
                        seatNumbers.replaceAll("_", "-"), voyageID, bus.getDeparture(), bus.getDestination(), priceOfSoldTickets), true, true);
                // Stop searching for the bus associated with the provided voyage ID.
                break;
            }
        }
    }

    /**
     * Refunds tickets for a specific voyage and updates the revenue accordingly.
     *
     * @param voyageID the ID of the voyage for which tickets will be refunded
     * @param seatNumbers a string representing the seat numbers to be refunded, separated by underscores
     *                    example: "1_2_3" represents refunding tickets for seats 1, 2, and 3
     * @param buses an ArrayList of Bus objects representing all buses and their voyages
     *              this parameter is used to find the bus associated with the provided voyage ID
     * @param outputFilePath the path to the output file where the result of the ticket refunding will be written
     *                       this parameter is used for writing the result to the output file
     */
    public void refundTicket(String voyageID, String seatNumbers, ArrayList<Bus> buses, String outputFilePath) {
        // Loop through all buses to find the bus associated with the provided voyage ID.
        for (Bus bus : buses) {
            // Check if the current bus has the provided voyage ID.
            if (bus.getVoyageID().contentEquals(voyageID)) {
                // Variable to store the total price of refunded tickets.
                double priceOfRefundedTickets = 0;
                // Split the seat numbers string into individual seat numbers.
                String[] seatNumbersForSelling = seatNumbers.split("_");
                // Loop through each seat number for refunding.
                for (String seatNumber : seatNumbersForSelling) {
                    // Loop through each seat of the current bus.
                    for (Seat seat : bus.getSeats()) {
                        // Check if the current seat matches the seat number for refunding.
                        if (seat.getSeatNumber() == Integer.parseInt(seatNumber)) {
                            // Check if the seat is sold (marked as 'X').
                            if (seat.getSeatSituation() == 'X') {
                                // Mark the seat as available (marked as '*').
                                seat.setSeatSituation('*');
                                // Add the refund amount of the seat to the total refund amount.
                                priceOfRefundedTickets += seat.getSeatRefundFee();
                            }
                            break;
                        }
                    }
                }
                // Update the revenue of the bus.
                bus.revenue -= priceOfRefundedTickets;
                // Write the result of the ticket refunding to the output file.
                Writer.writeToFile(outputFilePath, String.format("Seat %s of the Voyage %s from %s to %s was successfully refunded for %.2f TL.",
                        seatNumbers.replaceAll("_", "-"), voyageID, bus.getDeparture(), bus.getDestination(), priceOfRefundedTickets), true, true);
                // Stop searching for the bus associated with the provided voyage ID.
                break;
            }
        }
    }

    /**
     * Prints the seat layout and revenue information for a specific voyage to the specified output file.
     *
     * @param commandRow a string representing the command row containing information about the voyage
     * @param buses an ArrayList of Bus objects representing all buses and their voyages
     * @param outputFilePath the path to the output file where the seat layout and revenue information will be written
     */
    public void printVoyage(String commandRow, ArrayList<Bus> buses, String outputFilePath) {
        // Split the command row into individual commands.
        String[] commands = commandRow.split("\t");
        // Loop through all buses to find the bus associated with the provided voyage ID.
        for (Bus bus : buses) {
            // Check if the current bus has the provided voyage ID.
            if (bus.getVoyageID().contentEquals(commands[1])) {
                // Print the seat layout of the bus to the output file.
                bus.seatLayout(outputFilePath);
                // Write the revenue information of the bus to the output file.
                Writer.writeToFile(outputFilePath, String.format("Revenue: %.2f", bus.getRevenue()), true, true);
                // Stop searching for the bus associated with the provided voyage ID.
                break;
            }
        }
    }

    /**
     * Cancels a specific voyage and refunds all sold tickets for that voyage.
     *
     * @param commandRow a string representing the command row containing information about the voyage
     * @param buses an ArrayList of Bus objects representing all buses and their voyages
     * @param outputFilePath the path to the output file where the seat layout and revenue information will be written
     */
    public void cancellingVoyage(String commandRow, ArrayList<Bus> buses, String outputFilePath) {
        // Split the command row into individual commands.
        String[] commands = commandRow.split("\t");
        // Loop through all buses to find the bus associated with the provided voyage ID.
        for (Bus bus : buses) {

            if (bus.getVoyageID().contentEquals(commands[1])) {
                // Write the cancellation result to the output file.
                Writer.writeToFile(outputFilePath, String.format("Voyage %S was successfully cancelled!", bus.getVoyageID()), true, true);
                Writer.writeToFile(outputFilePath, "Voyage details can be found below:", true, true);
                // Print the seat layout of the bus to the output file.
                bus.seatLayout(outputFilePath);
                // Refund all sold tickets for the cancelled voyage.
                bus.refundAllSeats(bus.getVoyageID(), buses);
                // Write the revenue information of the cancelled voyage to the output file.
                Writer.writeToFile(outputFilePath, String.format("Revenue: %.2f", bus.getRevenue()), true, true);
                // Remove the cancelled bus from the list of buses.
                buses.remove(bus);
                // Stop searching for the bus associated with the provided voyage ID.
                break;
            }
        }
    }

    /**
     * Refunds all sold tickets for a specific voyage.
     *
     * @param voyageID the ID of the voyage for which tickets will be refunded
     * @param buses an ArrayList of Bus objects representing all buses and their voyages
     */
    public void refundAllSeats(String voyageID, ArrayList<Bus> buses) {
        // Loop through all buses to find the bus associated with the provided voyage ID.
        for (Bus bus : buses) {
            // Check if the current bus has the provided voyage ID.
            if (bus.getVoyageID().contentEquals(voyageID)) {
                // Loop through all seats of the current bus.
                for (Seat seat : bus.getSeats()) {
                    // Check if the seat is sold (marked as 'X').
                    if (seat.getSeatSituation() == 'X') {
                        // Refund the ticket by deducting its price from the revenue of the bus.
                        bus.revenue -= seat.getSeatFee();
                    }
                }
                // Stop searching for the bus associated with the provided voyage ID.
                break;
            }
        }
    }

    /**
     * Reads and processes commands from the provided rows, executing corresponding actions for each command.
     *
     * @param rows an array of strings representing the command rows to be processed
     * @param buses an ArrayList of Bus objects representing all buses and their voyages
     * @param outputFilePath the path to the output file where the results of the command processing will be written
     */
    private void commandReader(String[] rows, ArrayList<Bus> buses, String outputFilePath) {
        // Create an instance of ErrorChecker to check for errors in commands.
        ErrorChecker errorChecker = new ErrorChecker();
        // Counter for keeping track of the row number.
        int counter = 0;
        // Loop through each row of commands.
        for (String row : rows) {

            counter++; // Increment the row counter.
            // Split the row into individual commands.
            String[] commands = row.split("\t");
            // Check the type of command and execute corresponding action.
            if (commands[0].contentEquals("INIT_VOYAGE") &&
                    errorChecker.initVoyageChecker(row, commands, buses, outputFilePath)) {
                // Initialize a new voyage and add it to the list of buses.
                Bus bus = busFactory(commands);
                buses.add(bus);
                bus.initializeVoyage(row, outputFilePath);

            } else if (commands[0].contentEquals("Z_REPORT") &&
                    errorChecker.zReportChecker(row, commands, outputFilePath)) {
                // Generate and print a Z Report.
                zReport(buses, outputFilePath, rows.length == counter);

            } else if (commands[0].contentEquals("SELL_TICKET") &&
                    errorChecker.sellTicketChecker(row, commands, buses, outputFilePath)) {
                // Sell tickets for a specific voyage.
                sellTicket(commands[1], commands[2], buses, outputFilePath);

            } else if (commands[0].contentEquals("REFUND_TICKET") &&
                    errorChecker.refundTicketChecker(row, commands, buses, outputFilePath)) {
                // Refund tickets for a specific voyage.
                refundTicket(commands[1], commands[2], buses, outputFilePath);

            } else if (commands[0].contentEquals("PRINT_VOYAGE") &&
                    errorChecker.printVoyageChecker(row, commands, buses, outputFilePath)) {
                // Print details of a specific voyage.
                printVoyage(row, buses, outputFilePath);

            } else if (commands[0].contentEquals("CANCEL_VOYAGE") &&
                    errorChecker.cancelVoyageChecker(row, commands, buses, outputFilePath)) {
                // Cancel a specific voyage.
                cancellingVoyage(row, buses, outputFilePath);
            } else if (!commands[0].contentEquals("INIT_VOYAGE") && !commands[0].contentEquals("Z_REPORT") &&
                    !commands[0].contentEquals("SELL_TICKET") &&
                    !commands[0].contentEquals("REFUND_TICKET") &&
                    !commands[0].contentEquals("PRINT_VOYAGE") &&
                    !commands[0].contentEquals("CANCEL_VOYAGE")) {
                // Handle unrecognized commands.
                Writer.writeToFile(outputFilePath, String.format("COMMAND: %s", row), true, true);
                Writer.writeToFile(outputFilePath, String.format("ERROR: There is no command namely %s!", commands[0]), true, true);
            }
        }
        // If there are no rows or the last row does not end with "Z_REPORT", generate a final Z Report.
        if (rows.length == 0 || !rows[rows.length - 1].endsWith("Z_REPORT")) {

            zReport(buses, outputFilePath, true);
        }
    }

    /**
     * Returns the type of the bus.
     *
     * @return the type of the bus as a String
     */
    public String getBusType() {
        return busType;
    }

    /**
     * Returns the ID of the voyage.
     *
     * @return the ID of the voyage as a String
     */
    public String getVoyageID() {
        return voyageID;
    }

    /**
     * Gets the departure location of the voyage.
     *
     * @return the departure location of the voyage
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * Gets the destination location of the voyage.
     *
     * @return the destination location of the voyage
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Gets the number of seat rows in the bus.
     *
     * @return the number of seat rows in the bus
     */
    public int getSeatRows() {
        return seatRows;
    }

    /**
     * Gets the fee of a seat.
     *
     * @return the fee of a seat
     */
    public double getSeatFee() {
        return seatFee;
    }

    /**
     * Gets the total revenue earned from the bus.
     *
     * @return the total revenue earned from the bus
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * Gets the list of seats in the bus.
     *
     * @return the list of seats in the bus
     */
    public ArrayList<Seat> getSeats() {
        return seats;
    }
}