import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.awt.Frame;
import java.awt.TextArea;
import org.json.JSONObject;
import org.json.JSONArray;

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
        try {
            String content = new String(Files.readAllBytes(Paths.get("inventory.json")));
            JSONObject json = new JSONObject(content);

            JSONObject storeInfo = json.getJSONObject("store_info");
            storeName = storeInfo.getString("store_name");
            phoneNumber = storeInfo.getString("phone_number");
            city = storeInfo.getString("city");
            state = storeInfo.getString("state");
            cityTax = storeInfo.getDouble("city_tax");

            JSONArray productArray = json.getJSONArray("product_info");
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject prod = productArray.getJSONObject(i);
                String code = prod.getString("product_code");
                String name = prod.getString("product_name");
                double price = prod.getDouble("price");
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
