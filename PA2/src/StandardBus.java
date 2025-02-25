/**
 * StandardBus class represents a bus with standard (2+2) seating layout.
 * StandardBus extends the Bus class and adds functionality for standard seats.
 */
public class StandardBus extends Bus {

    private double refundCut; // Percentage of refund cut for standard seats.
    private double seatRefundFee; // Refund fee for standard seats.

    /**
     * Constructs a new StandardBus object with the specified parameters.
     *
     * @param busType     Type of the bus.
     * @param voyageID    ID of the voyage.
     * @param departure   Departure location of the voyage.
     * @param destination Destination location of the voyage.
     * @param seatRows    Number of rows of seats in the bus.
     * @param seatFee     Fee for regular seats.
     * @param refundCut   Percentage of refund cut for standard seats.
     */
    public StandardBus(String busType, String voyageID, String departure, String destination,
                       int seatRows, double seatFee, double refundCut) {
        super(busType, voyageID, departure, destination, seatRows, seatFee);
        this.refundCut = refundCut;
        this.seatRefundFee = (seatFee * (100 - refundCut)) / 100;
        addSeats();
    }

    /**
     * Adds seats to the standard bus, each row having two regular seats.
     */
    @Override
    public void addSeats() {

        int seatNumberCounter = 1;
        for(int i = 0; i < this.getSeatRows() * 4; i++) {
            seats.add(new Seat(this.getSeatFee(), seatNumberCounter, this.getRefundCut()));
            seatNumberCounter++;
        }
    }

    /**
     * Generates the seat layout of the standard bus.
     *
     * @param outputFilePath The path to the output file where the seat layout will be written.
     */
    @Override
    public void seatLayout(String outputFilePath) {
        // Write voyage details
        Writer.writeToFile(outputFilePath, String.format("Voyage %s", this.getVoyageID()), true, true);
        Writer.writeToFile(outputFilePath, String.format("%s-%s", this.getDeparture(), this.getDestination()), true, true);
        // Write seat layout
        for (Seat seat : this.getSeats()) {

            if (seat.getSeatNumber() % 4 == 0) {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + "", true, true);

            } else if (seat.getSeatNumber() % 2 == 0) {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + " | ", true, false);

            } else {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + " ", true, false);
            }
        }
    }

    /**
     * Initializes the voyage of the standard bus.
     *
     * @param row            The command row representing the voyage details.
     * @param outputFilePath The path to the output file where the initialization message will be written.
     */
    @Override
    public void initializeVoyage(String row, String outputFilePath) {
        // Write initialization message.
        Writer.writeToFile(outputFilePath, String.format("Voyage %S was initialized as a standard (2+2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that refunds will be %d%s less than the paid amount.",
                this.getVoyageID(), this.getDeparture(), this.getDestination(), this.getSeatFee(), this.getSeatRows() * 4, (int)this.getRefundCut(), "%"), true, true);
    }

    /**
     * Gets the refund cut percentage for standard seats.
     *
     * @return The refund cut percentage for standard seats.
     */
    public double getRefundCut() {
        return refundCut;
    }
}