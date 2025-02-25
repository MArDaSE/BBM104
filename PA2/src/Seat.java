/**
 * Seat class represents a seat in a bus.
 */
public class Seat {

    private double seatFee; // The fee for the seat.
    private double seatRefundFee; // The refund fee for the seat.
    private int seatNumber; // The number of the seat.
    private char seatSituation = '*'; // The situation of the seat (reserved or available).

    /**
     * Constructs a new Seat object with the specified fee and seat number.
     *
     * @param seatFee    The fee for the seat.
     * @param seatNumber The number of the seat.
     */
    public Seat(double seatFee, int seatNumber) {
        this.seatFee = seatFee;
        this.seatNumber = seatNumber;
    }

    /**
     * Constructs a new Seat object with the specified fee, seat number, and refund rate.
     *
     * @param seatFee        The fee for the seat.
     * @param seatNumber     The number of the seat.
     * @param seatRefundRate The refund rate for the seat.
     */
    public Seat(double seatFee, int seatNumber, double seatRefundRate) {
        this.seatFee = seatFee;
        this.seatNumber = seatNumber;
        this.seatRefundFee = (seatFee * (100 - seatRefundRate)) / 100;
    }

    /**
     * Gets the fee for the seat.
     *
     * @return The fee for the seat.
     */
    public double getSeatFee() {
        return seatFee;
    }

    /**
     * Gets the situation of the seat.
     *
     * @return The situation of the seat.
     */
    public char getSeatSituation() {
        return seatSituation;
    }

    /**
     * Sets the situation of the seat.
     *
     * @param seatSituation The situation of the seat.
     */
    public void setSeatSituation(char seatSituation) {
        this.seatSituation = seatSituation;
    }

    /**
     * Gets the number of the seat.
     *
     * @return The number of the seat.
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Gets the refund fee for the seat.
     *
     * @return The refund fee for the seat.
     */
    public double getSeatRefundFee() {
        return seatRefundFee;
    }
}

/**
 * PremiumSeat class represents a premium seat in a bus.
 * It extends the Seat class and adds functionality for premium seats.
 */
class PremiumSeat extends Seat {

    /**
     * Constructs a new PremiumSeat object with the specified fee, seat number, and refund rate.
     *
     * @param seatFee        The fee for the premium seat.
     * @param seatNumber     The number of the premium seat.
     * @param seatRefundRate The refund rate for the premium seat.
     */
    public PremiumSeat(double seatFee, int seatNumber, double seatRefundRate) {
        super(seatFee, seatNumber, seatRefundRate);
    }
}