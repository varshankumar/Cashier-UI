import java.util.*;
import java.io.*;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;



public class Inventory {
    private static Inventory instance = new Inventory();
    private Map<String, Product> products = new HashMap<>();
    private String storeName;
    private String phoneNumber;
    private String city;
    private String state;
    private double cityTax;
    private ProductListFrame productListFrame;

    private Inventory() {}

    public static Inventory getInstance() {
        return instance;
    }

    public void loadInventory() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("inventory.json")) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: inventory.json");
            }

            // Parse JSON using org.json
            JSONTokener tokener = new JSONTokener(is);
            JSONObject json = new JSONObject(tokener);

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
        if (productListFrame != null) {
            productListFrame.dispose();
        }
        
        productListFrame = new ProductListFrame();
        
        StringBuilder productList = new StringBuilder();
        productList.append(String.format("%-10s %-20s %-20s\n", "Code", "Name", "Description"));
        productList.append("------------------------------------------------------------\n");
        for (Product product : products.values()) {
            if (product.getCode().startsWith(partialCode)) {
                productList.append(String.format("%-10s %-20s %-20s\n", product.getCode(), product.getName(), product.getDescription()));
            }
        }
        
        productListFrame.setProducts(productList.toString());
        productListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                productListFrame.dispose();
                productListFrame = null;
            }
        });
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
