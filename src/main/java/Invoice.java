import java.util.*;

public class Invoice {
    private static Invoice instance = new Invoice();
    private List<InvoiceItem> items = new ArrayList<>();

    private Invoice() {}

    public static Invoice getInstance() {
        return instance;
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public void clearInvoice() {
        items.clear();
    }

    public String getInvoiceDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s %-10s %-20s %-10s %-10s\n", "Line", "Code", "Name", "Qty", "Price"));
        sb.append("---------------------------------------------------------------\n");
        int line = 1;
        for (InvoiceItem item : items) {
            sb.append(String.format("%-5d %-10s %-20s %-10d %-10.2f\n", line++, item.getProduct().getCode(),
                    item.getProduct().getName(), item.getQuantity(),
                    item.getProduct().getPrice() * item.getQuantity()));
        }
        return sb.toString();
    }

    public double calculateSubtotal() {
        double subtotal = 0;
        for (InvoiceItem item : items) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        return subtotal;
    }
}
