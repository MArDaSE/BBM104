public class Product {

    private String productName; // The name of the product.
    private double productPrice;    // The price of the product.
    private double productProtein;  // The protein quantity of the product.
    private double productCarbonhydrate;    // The carbohydrate quantity of the product.
    private double productFat;  // The fat quantity of the product.
    private int productCalorie; // The calorie quantity of the product.

    /**
     * The constructor method of Product class.
     * Calculates the calorific value of the product with the received values
     *
     * @param productName the name of the product.
     * @param productPrice the price of the product.
     * @param productProtein the protein quantity of the product.
     * @param productCarbonhydrate the carbohydrate quantity of the product.
     * @param productFat the fat quantity of the product.
     */
    public Product(String productName, double productPrice, double productProtein, double productCarbonhydrate, double productFat) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productProtein = productProtein;
        this.productCarbonhydrate = productCarbonhydrate;
        this.productFat = productFat;
        this.productCalorie = (int)(Math.round((productProtein * 4) + (productCarbonhydrate * 4) + (productFat * 9)));
    }

    // Getters and setters.
    public String getProductName() {
        return productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public double getProductPrice() {
        return productPrice;
    }


    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }


    public double getProductProtein() {
        return productProtein;
    }


    public void setProductProtein(double productProtein) {
        this.productProtein = productProtein;
    }


    public double getProductCarbonhydrate() {
        return productCarbonhydrate;
    }


    public void setProductCarbonhydrate(double productCarbonhydrate) {
        this.productCarbonhydrate = productCarbonhydrate;
    }


    public double getProductFat() {
        return productFat;
    }


    public void setProductFat(double productFat) {
        this.productFat = productFat;
    }


    public int getProductCalorie() {
        return productCalorie;
    }


    public void setProductCalorie(int productCalorie) {
        this.productCalorie = productCalorie;
    }
}
