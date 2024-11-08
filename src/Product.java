public class Product {
    private String code;
    private String name;
    private double price;
    private String description;

    public Product(String code, String name, double price, String description) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
}
