/**
 * Represents a product parent class with a name, barcode, and price.
 */
public class Product {

    private final String name; // The name of the product.
    private final String barcode; // The barcode of the product.
    private final Double price; // The price of the product.

    /**
     * Constructs a new Product with the given name, barcode, and price.
     *
     * @param name    the name of the product
     * @param barcode the barcode of the product
     * @param price   the price of the product
     */
    public Product(String name, String barcode, Double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    /**
     * Getters.
     */
    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public Double getPrice() {
        return price;
    }
}

/**
 * Represents a book product, which is a type of Product, with an additional author field.
 *
 * @param <T> the type of the author's name
 */
class Book <T extends String> extends Product{

    private final T author; // The author of the book.

    /**
     * Constructs a new Book with the given name, author, barcode, and price.
     *
     * @param name    the name of the book
     * @param author  the author of the book
     * @param barcode the barcode of the book
     * @param price   the price of the book
     */
    public Book(String name, T author, String barcode, Double price) {
        super(name, barcode, price);
        this.author = author;
    }

    /**
     * Getter.
     */
    public T getAuthor() {
        return author;
    }
}

class Toy <T extends String> extends Product{

    private final T color;

    public Toy(String name, T color, String barcode, Double price) {
        super(name, barcode, price);
        this.color = color;
    }

    public T getColor() {
        return color;
    }
}

/**
 * Represents a stationery product, which is a type of Product, with an additional kind field.
 *
 * @param <T> the type of the stationery's kind
 */
class Stationery <T extends String> extends Product{

    private final T kind; // The kind of the stationery.

    /**
     * Constructs a new Stationery with the given name, kind, barcode, and price.
     *
     * @param name    the name of the stationery
     * @param kind    the kind of the stationery
     * @param barcode the barcode of the stationery
     * @param price   the price of the stationery
     */
    public Stationery(String name, T kind, String barcode, Double price) {
        super(name, barcode, price);
        this.kind = kind;
    }

    /**
     * Getter.
     */
    public T getKind() {
        return kind;
    }
}