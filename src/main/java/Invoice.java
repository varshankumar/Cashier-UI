import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private static Invoice instance = new Invoice();
    private List<InvoiceItem> items;

    public static Invoice getInstance() {
        return instance;
    }

    public Invoice() {
        items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        for (InvoiceItem item : items) {
            if (item.getProduct().getCode().equals(product.getCode())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new InvoiceItem(product, quantity));
    }

    public void removeItem(String productCode) {
        items.removeIf(item -> item.getProduct().getCode().equals(productCode));
    }

    public boolean hasProduct(String productCode) {
        return items.stream()
                .anyMatch(item -> item.getProduct().getCode().equals(productCode));
    }

    public void decreaseQuantity(String productCode) {
        if (!hasProduct(productCode)) return;
        
        for (InvoiceItem item : items) {
            if (item.getProduct().getCode().equals(productCode)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    removeItem(productCode);
                }
                break;
            }
        }
    }

    public double calculateSubtotal() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public String getInvoiceDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s %-20s %-10s %-10s %-10s\n", 
            "Code", "Item", "Price", "Qty", "Total"));
        sb.append("---------------------------------------------------\n");
        
        for (InvoiceItem item : items) {
            Product product = item.getProduct();
            double total = product.getPrice() * item.getQuantity();
            sb.append(String.format("%-8s %-20s $%-9.2f %-10d $%-9.2f\n",
                    product.getCode(),
                    product.getName(),
                    product.getPrice(),
                    item.getQuantity(),
                    total));
        }
        
        return sb.toString();
    }

    public void clear() {
        items.clear();
    }

    public List<InvoiceItem> getItems() {
        return items;
    }
}
