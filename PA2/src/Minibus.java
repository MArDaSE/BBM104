/**
 * Minibus class represents a bus with 2-seat rows.
 * Minibus extends the Bus class and adds functionality for minibus seats.
 */
public class Minibus extends Bus{

    /**
     * Constructs a new Minibus object with the specified parameters.
     *
     * @param busType     Type of the bus.
     * @param voyageID    ID of the voyage.
     * @param departure   Departure location of the voyage.
     * @param destination Destination location of the voyage.
     * @param seatRows    Number of rows of seats in the bus.
     * @param seatFee     Fee for regular seats.
     */
    public Minibus(String busType, String voyageID, String departure,
                   String destination, int seatRows, double seatFee) {
        super(busType, voyageID, departure, destination, seatRows, seatFee);
        addSeats();
    }

    /**
     * Adds seats to the minibus, each row having two seats.
     */
    @Override
    public void addSeats() {
        int seatNumberCounter = 1;
        for(int i = 0; i < this.getSeatRows() * 2; i++) {
            seats.add(new Seat(this.getSeatFee(), seatNumberCounter));
            seatNumberCounter++;
        }
    }

    /**
     * Generates the seat layout of the minibus.
     *
     * @param outputFilePath The path to the output file where the seat layout will be written.
     */
    @Override
    public void seatLayout(String outputFilePath) {
        // Write voyage details.
        Writer.writeToFile(outputFilePath, String.format("Voyage %s", this.getVoyageID()), true, true);
        Writer.writeToFile(outputFilePath, String.format("%s-%s", this.getDeparture(), this.getDestination()), true, true);
        // Write seat layout.
        for (Seat seat : this.getSeats()) {

            if (seat.getSeatNumber() % 2 == 0) {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + "", true, true);

            } else {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + " ", true, false);
            }
        }
    }

    /**
     * Initializes the voyage of the minibus.
     *
     * @param row            The command row representing the voyage details.
     * @param outputFilePath The path to the output file where the initialization message will be written.
     */
    @Override
    public void initializeVoyage(String row, String outputFilePath) {
        // Write initialization message.
        Writer.writeToFile(outputFilePath, String.format("Voyage %s was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that minibus tickets are not refundable.",
                this.getVoyageID(), this.getDeparture(), this.getDestination(), this.getSeatFee(), this.getSeatRows() * 2), true, true);
    }
}