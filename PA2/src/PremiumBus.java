/**
 * PremiumBus class represents a bus with premium (1+2) seating layout.
 * PremiumBus extends the Bus class and adds functionality for premium seats.
 */
public class PremiumBus extends Bus {

    private double refundCut; // Percentage of refund cut for premium seats.
    private double premiumSeatFee; // Fee for premium seats.

    /**
     * Constructs a new PremiumBus object with the specified parameters.
     *
     * @param busType             type of the bus
     * @param voyageID            iD of the voyage
     * @param departure           departure location of the voyage
     * @param destination         destination location of the voyage
     * @param seatRows            number of rows of seats in the bus
     * @param seatFee             fee for regular seats
     * @param refundCut           percentage of refund cut for premium seats
     * @param premiumSeatExtraPay extra pay percentage for premium seats
     */
    public PremiumBus(String busType, String voyageID, String departure, String destination,
                      int seatRows, double seatFee, double refundCut, double premiumSeatExtraPay) {
        super(busType, voyageID, departure, destination, seatRows, seatFee);
        this.refundCut = refundCut;
        this.premiumSeatFee = (seatFee * (100 + premiumSeatExtraPay)) / 100;
        addSeats();
    }

    /**
     * Adds seats to the bus, including premium and regular seats.
     * Premium seats are placed every three seats.
     */
    @Override
    public void addSeats() {

        int seatNumber = 1;
        for(int i = 0; i < this.getSeatRows() * 3; i++) {

            if(seatNumber % 3 == 1) {

                this.seats.add(new PremiumSeat(this.getPremiumSeatFee(), seatNumber, this.getRefundCut()));
                seatNumber++;
            } else if (seatNumber % 3 != 1) {

                seats.add(new Seat(this.getSeatFee(), seatNumber, this.getRefundCut()));
                seatNumber++;
            }
        }
    }

    /**
     * Generates the seat layout of the bus.
     *
     * @param outputFilePath the path to the output file where the seat layout will be written
     */
    @Override
    public void seatLayout(String outputFilePath) {
        // Write voyage details.
        Writer.writeToFile(outputFilePath, String.format("Voyage %s", this.getVoyageID()), true, true);
        Writer.writeToFile(outputFilePath, String.format("%s-%s", this.getDeparture(), this.getDestination()), true, true);
        // Write seat layout.
        for (Seat seat : this.getSeats()) {

            if (seat.getSeatNumber() % 3 == 0) {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + "", true, true);

            } else if (seat.getSeatNumber() % 3 == 1) {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + " | ", true, false);

            } else {

                Writer.writeToFile(outputFilePath, seat.getSeatSituation() + " ", true, false);
            }
        }
    }

    /**
     * Initializes the voyage of the bus with premium seats.
     *
     * @param row             the command row representing the voyage details
     * @param outputFilePath  the path to the output file where the initialization message will be written
     */
    @Override
    public void initializeVoyage(String row, String outputFilePath) {
        // Write initialization message.
        Writer.writeToFile(outputFilePath, String.format("Voyage %s was initialized as a premium (1+2) voyage from %s to %s with %.2f TL priced %d regular seats and %.2f TL priced %d premium seats. Note that refunds will be %d%s less than the paid amount.",
                this.getVoyageID(), this.getDeparture(), this.getDestination(), this.getSeatFee(), this.getSeatRows() * 2, this.getPremiumSeatFee(), this.getSeatRows(), (int)this.getRefundCut(), "%"), true, true);
    }

    /**
     * Gets the refund cut percentage for premium seats.
     *
     * @return the refund cut percentage for premium seats
     */
    public double getRefundCut() {
        return refundCut;
    }

    /**
     * Gets the fee for premium seats.
     *
     * @return the fee for premium seats
     */
    public double getPremiumSeatFee() {
        return premiumSeatFee;
    }
}