import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.awt.Frame;
import java.awt.TextArea;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonArray;


public class Inventory {
    private static Inventory instance = new Inventory();
    private Map<String, Product> products = new HashMap<>();
    private String storeName;
    private String phoneNumber;
    private String city;
    private String state;
    private double cityTax;

    private Inventory() {}

    public static Inventory getInstance() {
        return instance;
    }

    public void loadInventory() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("inventory.json");
             JsonReader reader = Json.createReader(is)) {

            if (is == null) {
                throw new FileNotFoundException("Resource not found: inventory.json");
            }

            JsonObject json = reader.readObject();

            JsonObject storeInfo = json.getJsonObject("store_info");
            storeName = storeInfo.getString("store_name");
            phoneNumber = storeInfo.getString("phone_number");
            city = storeInfo.getString("city");
            state = storeInfo.getString("state");
            cityTax = storeInfo.getJsonNumber("city_tax").doubleValue();

            JsonArray productArray = json.getJsonArray("product_info");
            for (JsonObject prod : productArray.getValuesAs(JsonObject.class)) {
                String code = prod.getString("product_code");
                String name = prod.getString("product_name");
                double price = prod.getJsonNumber("price").doubleValue();
                String description = prod.getString("description");

                products.put(code, new Product(code, name, price, description));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProducts(String partialCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-20s %-20s\n", "Code", "Name", "Description"));
        sb.append("------------------------------------------------------------\n");
        for (Product product : products.values()) {
            if (product.getCode().startsWith(partialCode)) {
                sb.append(String.format("%-10s %-20s %-20s\n", product.getCode(), product.getName(), product.getDescription()));
            }
        }
        Frame productListFrame = new Frame("Product List");
        TextArea textArea = new TextArea(sb.toString(), 20, 50);
        productListFrame.add(textArea);
        productListFrame.setSize(600, 400);
        productListFrame.setVisible(true);
    }

    public Product getProduct(String code) {
        return products.get(code);
    }

    public String getStoreName() { return storeName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public double getCityTax() { return cityTax; }
}
