import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptGenerator {
    public static void generateReceipt() {
        Inventory inventory = Inventory.getInstance();
        CashierSession cashier = CashierSession.getInstance();
        Invoice invoice = Invoice.getInstance();

        StringBuilder receipt = new StringBuilder();
        receipt.append("Store: ").append(inventory.getStoreName()).append("\n");
        receipt.append("Address: ").append(inventory.getCity()).append(", ").append(inventory.getState()).append("\n");
        receipt.append("Phone: ").append(inventory.getPhoneNumber()).append("\n\n");
        receipt.append("Date/Time: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");

        receipt.append("Items Purchased:\n");
        receipt.append("---------------------------------------------------------------\n");
        receipt.append(invoice.getInvoiceDetails());
        receipt.append("---------------------------------------------------------------\n");

        double subtotal = invoice.calculateSubtotal();
        double taxPercent = inventory.getCityTax();
        double taxAmount = subtotal * (taxPercent / 100);
        double totalWithTax = subtotal + taxAmount;

        double discountPercent = 0;
        if (FrameTwo.getInstance().applyDiscountCheckbox.getState()) {
            try {
                discountPercent = Double.parseDouble(FrameTwo.getInstance().discountField.getText());
            } catch (NumberFormatException e) {
                discountPercent = 0;
            }
        }
        double discountAmount = totalWithTax * (discountPercent / 100);
        double grandTotal = totalWithTax - discountAmount;

        receipt.append(String.format("Subtotal: $%.2f\n", subtotal));
        receipt.append(String.format("Tax (%.2f%%): $%.2f\n", taxPercent, taxAmount));
        receipt.append(String.format("Total with Tax: $%.2f\n", totalWithTax));
        receipt.append(String.format("Discount (%.2f%%): -$%.2f\n", discountPercent, discountAmount));
        receipt.append(String.format("Grand Total: $%.2f\n\n", grandTotal));

        receipt.append("Your cashier serving you today is ").append(cashier.getCashierName()).append("\n");
        receipt.append("Thank you for shopping with us!");

        Frame receiptFrame = new Frame("Receipt");
        TextArea textArea = new TextArea(receipt.toString(), 25, 50);
        textArea.setEditable(false);
        
        receiptFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                receiptFrame.dispose();
            }
        });
        
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> receiptFrame.dispose());
        
        Panel buttonPanel = new Panel();
        buttonPanel.add(closeButton);
        
        receiptFrame.setLayout(new BorderLayout());
        receiptFrame.add(textArea, BorderLayout.CENTER);
        receiptFrame.add(buttonPanel, BorderLayout.SOUTH);
        receiptFrame.setSize(600, 700);
        receiptFrame.setVisible(true);
    }
}
