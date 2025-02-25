import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This class represents a simple booking system for buses.
 * It reads booking commands from an input file, performs booking operations on buses,
 * and writes the results to an output file.
 */
public class BookingSystem {

    /**
     * The main method of the booking system.
     *
     * @param args Command line arguments. Expects two arguments:
     *             - The path to the input file containing booking commands.
     *             - The path to the output file where the results will be written.
     *             Sample usage: "java8 BookingSystem input.txt output.txt"
     */
    public static void main(String[] args) {
        // Set default locale to US to ensure proper number formatting.
        Locale.setDefault(Locale.US);
        // ArrayList to store Bus objects.
        ArrayList<Bus> buses = new ArrayList<>();

        try {
            // Check if the correct number of command line arguments is provided.
            if (args.length != 2) {
                // If not, print an error message and terminate the program.
                System.out.println("ERROR: This program works exactly with two command line arguments, " +
                        "the first one is the path to the input file whereas the second one is the path to the output file. " +
                        "Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". " +
                        "Program is going to terminate!");
            } else {
                // If file exists, delete file.
                File file = new File(args[1]);
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                }
                // Read booking commands from the input file.
                String[] commandsRows = Reader.readFile(args[0], true, true);
                // Create a Bus object and perform booking operations.
                Bus bus = new Bus(commandsRows, buses, args[1]);

            }
        } catch (Exception e) {
            // Catch any exception and do nothing.
            // When the read file does not exist or is faulty,
            // the "commandReader" method or others may return a nullPointException error.
            // It is used to avoid transmitting this error to the user.
        }
    }
}
