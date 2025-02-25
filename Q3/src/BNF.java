import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a BNF (Backus-Naur Form) generator.
 */
public class BNF {

    /**
     * Main method to generate BNF from input file and write to output file.
     *
     * @param args Command line arguments: [0] input file path, [1] output file path
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("ERROR: This program works exactly with two command line arguments, " +
                    "the first one is the path to the input file whereas the second one is the path to the output file. " +
                    "Sample usage can be as follows: \"java8 BNF input.txt output.txt\". " +
                    "Program is going to terminate!");
        } else {
            // Read input file.
            String[] items = Reader.readFile(args[0], true, true);
            if (items != null) {
                // Generate BNF and write to output file.
                Writer.writeToFile(args[1], bNFGenerator(items, "S"), false, false);
            }
        }
    }

    /**
     * Generates BNF (Backus-Naur Form) recursively.
     *
     * @param items Array of strings representing production rules
     * @param mayBeNonTerminal String representing a non-terminal symbol
     * @return String representing BNF for the given non-terminal
     */
    public static String bNFGenerator(String[] items,String mayBeNonTerminal) {
        // Map to store non-terminals and their BNF representations.
        // I wanted to use Map because I could keep non-terminals and their values at the same time.
        Map<String, String> nonTerminals = new HashMap<>();
        // Check if the given symbol is a non-terminal.
        if (Character.isUpperCase(mayBeNonTerminal.charAt(0))) {
            // If BNF for this non-terminal is already calculated, return it.
            if (nonTerminals.containsKey(mayBeNonTerminal)) {
                return nonTerminals.get(mayBeNonTerminal);
            }
            // Iterate through production rules to find BNF for given non-terminal.
            for (String expression : items) {
                // If production rule starts with the given non-terminal.
                if (expression.startsWith(mayBeNonTerminal)) {
                    // Create a NonTerminal object.
                    NonTerminal bNF = new NonTerminal(mayBeNonTerminal, expression.substring(3));
                    bNF.items.add("("); // Add opening parenthesis.
                    // Recursively generate BNF for each symbol in the production rule.
                    for (int i = 0; i < bNF.getValue().length(); i++) {
                        bNF.items.add(bNFGenerator(items, bNF.getValue().substring(i, i + 1)));
                    }
                    bNF.items.add(")"); // Add closing parenthesis.
                    // Concatenate BNF items to form BNF for this non-terminal.
                    String value = "";
                    for (String item : bNF.items) {
                        value += item;
                    }
                    // Set BNF for this non-terminal in the map
                    bNF.setValue(value);
                    // Adds non-terminals and their representations to the Map for use.
                    nonTerminals.put(bNF.getName(), bNF.getValue());
                    // Return the generated BNF.
                    return nonTerminals.get(mayBeNonTerminal);
                }
            }
        }
        // If the given symbol is not a non-terminal, return as is.
        return mayBeNonTerminal;
    }
}