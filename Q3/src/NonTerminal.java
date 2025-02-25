import java.util.ArrayList;

/**
 * This class represents a non-terminal symbol in a BNF (Backus-Naur Form) grammar.
 */
public class NonTerminal {

    private String name; // Name of the non-terminal symbol.
    private String value; // Representation of the non-terminal symbol.
    // This arraylist was used to hold the items of the non-terminals one by one.
    public ArrayList<String> items = new ArrayList<>(); // ArrayList to store BNF items.

    /**
     * Constructs a NonTerminal object with the given name and value.
     *
     * @param name  the name of the non-terminal symbol
     * @param value the value of the non-terminal symbol
     */
    public NonTerminal(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of the non-terminal symbol.
     *
     * @return the name of the non-terminal symbol
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the non-terminal symbol.
     *
     * @return the value of the non-terminal symbol
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the non-terminal symbol.
     *
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}