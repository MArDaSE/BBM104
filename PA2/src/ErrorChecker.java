import java.util.ArrayList;

/**
 * ErrorChecker class provides methods to check for errors in different commands related to bus operations.
 */
public class ErrorChecker {

    /**
     * Checks for errors in the initialization of a voyage.
     *
     * @param commandRow      The entire command row.
     * @param commandsRow     An array of individual command elements.
     * @param buses           The list of buses.
     * @param outputFilePath  The file path for the output.
     * @return true if there are no errors, false otherwise.
     */
    public boolean initVoyageChecker(String commandRow, String[] commandsRow, ArrayList<Bus> buses, String outputFilePath) {

        Writer.writeToFile(outputFilePath, "COMMAND: " + commandRow, true, true);

        if (commandsRow[1].contentEquals("Premium")) {

            return sortCheckerForInitVoyageChecker(commandsRow,buses,outputFilePath,"Premium");

        } else if (commandsRow[1].contentEquals("Standard")) {

            return sortCheckerForInitVoyageChecker(commandsRow,buses,outputFilePath,"Standard");

        } else if (commandsRow[1].contentEquals("Minibus")) {

            return sortCheckerForInitVoyageChecker(commandsRow,buses,outputFilePath,"Minibus");

        } else {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
            return false;
        }
    }

    /**
     * Initializes a voyage based on the given parameters and checks various conditions to ensure data validity.
     * Writes error messages to a specified output file if any checks fail.
     *
     * @param commandsRow An array of strings representing command parameters
     * @param buses An ArrayList of Bus objects to check for existing voyages with the same ID
     * @param outputFilePath The file path where error messages or logs are written
     * @param busType The type of bus, which can affect validation rules (e.g., "Premium" or "Standard")
     * @return true if the voyage is successfully initialized without any validation errors, otherwise false
     */
    public boolean sortCheckerForInitVoyageChecker(String[] commandsRow, ArrayList<Bus> buses, String outputFilePath, String busType) {

        try {
            try {

                int voyageId = Integer.parseInt(commandsRow[2]);
                if (voyageId <= 0) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[2]), true, true);
                    return false;
                }
            } catch (Exception e) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[2]), true, true);
                return false;
            }

            for (Bus bus : buses) {

                if (bus.getVoyageID().contentEquals(commandsRow[2])) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: There is already a voyage with ID of %s!", commandsRow[2]), true, true);
                    return false;
                }
            }

            try {

                int seatRows = Integer.parseInt(commandsRow[5]);
                if (seatRows <= 0) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!", commandsRow[5]), true, true);
                    return false;
                }
            } catch (Exception e) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!", commandsRow[5]), true, true);
                return false;
            }

            try {

                double seatPrice = Double.parseDouble(commandsRow[6]);
                if (seatPrice <= 0) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive number, price must be a positive number!", commandsRow[6]), true, true);
                    return false;
                }
            } catch (Exception e) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive number, price must be a positive number!", commandsRow[6]), true, true);
                return false;
            }

            if (busType.contentEquals("Premium")) {

                try {

                    int refundCut = Integer.parseInt(commandsRow[7]);
                    if (refundCut < 0 || 100 < refundCut) {

                        Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", commandsRow[7]), true, true);
                        return false;
                    }
                } catch (Exception e) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", commandsRow[7]), true, true);
                    return false;
                }

                try {

                    int premiumFee = Integer.parseInt(commandsRow[8]);
                    if (premiumFee < 0) {

                        Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!", commandsRow[8]), true, true);
                        return false;
                    }

                    if (commandsRow.length != 9) {

                        Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                        return false;
                    }
                } catch (Exception e) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!", commandsRow[8]), true, true);
                    return false;
                }

            } else if (busType.contentEquals("Standard")) {

                try {

                    int refundCut = Integer.parseInt(commandsRow[7]);
                    if (refundCut < 0 || 100 < refundCut) {

                        Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", commandsRow[7]), true, true);
                        return false;
                    }

                    if (commandsRow.length != 8) {

                        Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                        return false;
                    }
                } catch (Exception e) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", commandsRow[7]), true, true);
                    return false;
                }
            } else {

                if (commandsRow.length != 7) {

                    Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
            return false;
        }
        return true;
    }

    /**
     * Checks for errors in generating a Z report.
     *
     * @param commandRow      The entire command row.
     * @param commandsRow     An array of individual command elements.
     * @param outputFilePath  The file path for the output.
     * @return true if there are no errors, false otherwise.
     */
    public boolean zReportChecker(String commandRow, String[] commandsRow, String outputFilePath) {

        Writer.writeToFile(outputFilePath, "COMMAND: " + commandRow, true, true);

        if (commandsRow.length != 1) {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"Z_REPORT\" command!", true, true);
            return false;
        }

        return true;
    }

    /**
     * Checks for errors in selling a ticket.
     *
     * @param commandRow      The entire command row.
     * @param commandsRow     An array of individual command elements.
     * @param buses           The list of buses.
     * @param outputFilePath  The file path for the output.
     * @return true if there are no errors, false otherwise.
     */
    public boolean sellTicketChecker(String commandRow, String[] commandsRow, ArrayList<Bus> buses, String outputFilePath) {

        Writer.writeToFile(outputFilePath, "COMMAND: " + commandRow, true, true);

        if (commandsRow.length != 3) {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"SELL_TICKET\" command!", true, true);
            return false;
        }

        try {

            int voyageId = Integer.parseInt(commandsRow[1]);
            if (voyageId <= 0) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
                return false;
            }

            boolean hasABusWithId = false;
            for (Bus bus : buses) {

                if (bus.getVoyageID().contentEquals(commandsRow[1])) {

                    hasABusWithId = true;
                    break;
                }
            }

            if (!hasABusWithId) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: There is no voyage with ID of %s!", commandsRow[1]), true, true);
                return false;
            }
        } catch (Exception e) {

            Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
            return false;
        }

        String[] seatNumbers = commandsRow[2].split("_");
        for (String seatNumber : seatNumbers) {

            try {

                int numberOfSeat = Integer.parseInt(seatNumber);
                if (numberOfSeat <= 0) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seatNumber), true, true);
                    return false;
                }

                boolean isThereSeat = false;
                for (Bus bus : buses) {

                    if (bus.getVoyageID().contentEquals(commandsRow[1])) {

                        for (Seat seat : bus.getSeats()) {

                            if (seat.getSeatNumber() == numberOfSeat) {

                                if (seat.getSeatSituation() == 'X') {

                                    Writer.writeToFile(outputFilePath, "ERROR: One or more seats already sold!", true, true);
                                    return false;

                                }
                                isThereSeat = true;
                                break;
                            }
                        }
                        break;
                    }
                }

                if (!isThereSeat) {

                    Writer.writeToFile(outputFilePath, "ERROR: There is no such a seat!", true, true);
                    return false;
                }
            } catch (Exception e) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seatNumber), true, true);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for errors in refunding a ticket.
     *
     * @param commandRow      The entire command row.
     * @param commandsRow     An array of individual command elements.
     * @param buses           The list of buses.
     * @param outputFilePath  The file path for the output.
     * @return true if there are no errors, false otherwise.
     */
    public boolean refundTicketChecker(String commandRow, String[] commandsRow, ArrayList<Bus> buses, String outputFilePath) {

        Writer.writeToFile(outputFilePath, "COMMAND: " + commandRow, true, true);

        if (commandsRow.length != 3) {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"REFUND_TICKET\" command!", true, true);
            return false;
        }

        try {

            int voyageId = Integer.parseInt(commandsRow[1]);
            if (voyageId <= 0) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
                return false;
            }

            boolean hasABusWithId = false;
            for (Bus bus : buses) {

                if (bus.getVoyageID().contentEquals(commandsRow[1])) {

                    if (bus.getBusType().contentEquals("Minibus")) {

                        Writer.writeToFile(outputFilePath, "ERROR: Minibus tickets are not refundable!", true, true);
                        return false;
                    }
                    hasABusWithId = true;
                    break;
                }
            }

            if (!hasABusWithId) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: There is no voyage with ID of %s!", commandsRow[1]), true, true);
                return false;
            }
        } catch (Exception e) {

            Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
            return false;
        }

        String[] seatNumbers = commandsRow[2].split("_");
        for (String seatNumber : seatNumbers) {

            try {

                int numberOfSeat = Integer.parseInt(seatNumber);
                if (numberOfSeat <= 0) {

                    Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seatNumber), true, true);
                    return false;
                }

                boolean isThereSeat = false;
                for (Bus bus : buses) {

                    if (bus.getVoyageID().contentEquals(commandsRow[1])) {

                        for (Seat seat : bus.getSeats()) {

                            if (seat.getSeatNumber() == numberOfSeat) {

                                if (seat.getSeatSituation() == '*') {

                                    Writer.writeToFile(outputFilePath, "ERROR: One or more seats are already empty!", true, true);
                                    return false;

                                }
                                isThereSeat = true;
                                break;
                            }
                        }
                        break;
                    }
                }

                if (!isThereSeat) {

                    Writer.writeToFile(outputFilePath, "ERROR: There is no such a seat!", true, true);
                    return false;
                }
            } catch (Exception e) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seatNumber), true, true);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for errors in cancelling a voyage.
     *
     * @param commandRow      The entire command row.
     * @param commandsRow     An array of individual command elements.
     * @param buses           The list of buses.
     * @param outputFilePath  The file path for the output.
     * @return true if there are no errors, false otherwise.
     */
    public boolean cancelVoyageChecker(String commandRow, String[] commandsRow, ArrayList<Bus> buses, String outputFilePath) {

        Writer.writeToFile(outputFilePath, "COMMAND: " + commandRow, true, true);

        if (commandsRow.length != 2) {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!", true, true);
            return false;
        }

        try {

            int voyageId = Integer.parseInt(commandsRow[1]);
            if (voyageId <= 0) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
                return false;
            }

            boolean hasABusWithId = false;
            for (Bus bus : buses) {

                if (bus.getVoyageID().contentEquals(commandsRow[1])) {

                    hasABusWithId = true;
                    break;
                }
            }

            if (!hasABusWithId) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: There is no voyage with ID of %s!", commandsRow[1]), true, true);
                return false;
            }
        } catch (Exception e) {

            Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
            return false;
        }
        return true;

    }

    /**
     * Checks for errors in printing voyage information.
     *
     * @param commandRow      The entire command row.
     * @param commandsRow     An array of individual command elements.
     * @param buses           The list of buses.
     * @param outputFilePath  The file path for the output.
     * @return true if there are no errors, false otherwise.
     */
    public boolean printVoyageChecker(String commandRow, String[] commandsRow, ArrayList<Bus> buses, String outputFilePath) {

        Writer.writeToFile(outputFilePath, "COMMAND: " + commandRow, true, true);

        if (commandsRow.length != 2) {

            Writer.writeToFile(outputFilePath, "ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!", true, true);
            return false;
        }

        try {

            int voyageId = Integer.parseInt(commandsRow[1]);
            if (voyageId <= 0) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
                return false;
            }

            boolean hasABusWithId = false;
            for (Bus bus : buses) {

                if (bus.getVoyageID().contentEquals(commandsRow[1])) {

                    hasABusWithId = true;
                    break;
                }
            }

            if (!hasABusWithId) {

                Writer.writeToFile(outputFilePath, String.format("ERROR: There is no voyage with ID of %s!", commandsRow[1]), true, true);
                return false;
            }
        } catch (Exception e) {

            Writer.writeToFile(outputFilePath, String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", commandsRow[1]), true, true);
            return false;
        }
        return true;
    }
}