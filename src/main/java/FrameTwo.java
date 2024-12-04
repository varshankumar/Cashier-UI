import java.awt.*;
import javax.swing.border.TitledBorder;

public class FrameTwo extends Panel {
    private static FrameTwo instance;
    private TextArea invoiceArea;
    private TextField taxField, discountField, subtotalField, totalWithTaxField, grandTotalField;
    private Checkbox discountCheckbox;
    private Button printReceiptButton;
    
    private FrameTwo() {
        setLayout(new GridLayout(2, 1, 10, 10));
        
        // Invoice Display Panel
        Panel invoicePanel = new Panel(new BorderLayout());
        invoicePanel.setBorder(new TitledBorder("Current Invoice"));
        invoiceArea = new TextArea(20, 40);
        invoiceArea.setEditable(false);
        invoicePanel.add(invoiceArea, BorderLayout.CENTER);
        
        // Totals Panel
        Panel totalsPanel = new Panel(new GridBagLayout());
        totalsPanel.setBorder(new TitledBorder("Transaction Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Add tax info
        taxField = new TextField("Tax Rate: 10% (City: Example, State: ST)", 30);
        taxField.setEditable(false);
        
        // Add discount checkbox and field
        discountCheckbox = new Checkbox("Apply Discount");
        discountField = new TextField("0%", 10);
        
        // Add total fields
        subtotalField = new TextField("$0.00", 10);
        totalWithTaxField = new TextField("$0.00", 10);
        grandTotalField = new TextField("$0.00", 10);
        
        // Add print receipt button
        printReceiptButton = new Button("Print Receipt");
        
        // ... add components to totalsPanel with GridBagConstraints ...
        
        add(invoicePanel);
        add(totalsPanel);
        
        // Add listeners
        discountCheckbox.addItemListener(e -> updateTotals());
        printReceiptButton.addActionListener(e -> printReceipt());
    }
    
    private void printReceipt() {
        Frame receiptFrame = new Frame("Receipt");
        TextArea receiptArea = new TextArea();
        receiptArea.setEditable(false);
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("STORE NAME\n");
        receipt.append("123 Main St, Example City, ST\n");
        receipt.append("Tel: (555) 555-5555\n\n");
        receipt.append("Date: " + java.time.LocalDateTime.now() + "\n");
        receipt.append("----------------------------------------\n");
        // ... add invoice items ...
        receipt.append("\nYour cashier serving you today is: " + 
                      CashierSession.getInstance().getCashierName() + "\n");
        receipt.append("Thank you for shopping with us!\n");
        
        receiptArea.setText(receipt.toString());
        receiptFrame.add(receiptArea);
        receiptFrame.setSize(400, 600);
        receiptFrame.setVisible(true);
        
        // Add window closing listener
        receiptFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                receiptFrame.dispose();
            }
        });
    }
    
    public static FrameTwo getInstance() {
        return instance;
    }

    public void updateInvoiceDisplay() {
        invoiceArea.setText(Invoice.getInstance().getInvoiceDetails());
        updateTotals();
    }

    private void updateTotals() {
        double subtotal = Invoice.getInstance().calculateSubtotal();
        subtotalField.setText(String.format("%.2f", subtotal));

        double taxPercent = Double.parseDouble(taxField.getText());
        double taxAmount = subtotal * (taxPercent / 100);
        double totalWithTax = subtotal + taxAmount;
        totalWithTaxField.setText(String.format("%.2f", totalWithTax));

        double discountPercent = 0;
        if (discountCheckbox.getState()) {
            try {
                discountPercent = Double.parseDouble(discountField.getText());
            } catch (NumberFormatException e) {
                discountPercent = 0;
            }
        }
        double discountAmount = totalWithTax * (discountPercent / 100);
        double totalAfterDiscount = totalWithTax - discountAmount;
        grandTotalField.setText(String.format("%.2f", totalAfterDiscount));
    }
}
