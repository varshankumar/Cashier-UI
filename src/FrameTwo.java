import java.awt.*;
import java.awt.event.*;

public class FrameTwo extends Panel {
    private static FrameTwo instance = new FrameTwo();
    private TextArea invoiceDisplay;
    TextField taxField, discountField, subtotalField, totalField, discountTotalField, grandTotalField;
    Checkbox applyDiscountCheckbox;
    private Button printReceiptButton;

    private FrameTwo() {
        setLayout(new BorderLayout());

        Panel invoicePanelContainer = new Panel(new BorderLayout());
        Panel invoicePanel = new Panel(new BorderLayout());
        Label invoicePanelTitle = new Label("Invoice", Label.CENTER);
        invoicePanelContainer.add(invoicePanelTitle, BorderLayout.NORTH);
        invoicePanelContainer.add(invoicePanel, BorderLayout.CENTER);

        invoiceDisplay = new TextArea(15, 50);
        invoiceDisplay.setEditable(false);
        invoicePanel.add(invoiceDisplay, BorderLayout.CENTER);

        Panel totalsPanelContainer = new Panel(new BorderLayout());
        Panel totalsPanel = new Panel(new GridLayout(8, 2));
        Label totalsPanelTitle = new Label("Totals", Label.CENTER);
        totalsPanelContainer.add(totalsPanelTitle, BorderLayout.NORTH);
        totalsPanelContainer.add(totalsPanel, BorderLayout.CENTER);

        taxField = new TextField(10);
        taxField.setEditable(false);
        discountField = new TextField(10);
        subtotalField = new TextField(10);
        subtotalField.setEditable(false);
        totalField = new TextField(10);
        totalField.setEditable(false);
        discountTotalField = new TextField(10);
        discountTotalField.setEditable(false);
        grandTotalField = new TextField(10);
        grandTotalField.setEditable(false);
        applyDiscountCheckbox = new Checkbox("Apply Discount");
        printReceiptButton = new Button("Print Receipt");

        totalsPanel.add(new Label("City Tax (%):"));
        taxField.setText(String.valueOf(Inventory.getInstance().getCityTax()));
        totalsPanel.add(taxField);
        totalsPanel.add(new Label("Discount (%):"));
        totalsPanel.add(discountField);
        totalsPanel.add(applyDiscountCheckbox);
        totalsPanel.add(new Label(""));
        totalsPanel.add(new Label("Subtotal:"));
        totalsPanel.add(subtotalField);
        totalsPanel.add(new Label("Total with Tax:"));
        totalsPanel.add(totalField);
        totalsPanel.add(new Label("Total after Discount:"));
        totalsPanel.add(discountTotalField);
        totalsPanel.add(new Label("Grand Total:"));
        totalsPanel.add(grandTotalField);
        totalsPanel.add(printReceiptButton);

        add(invoicePanelContainer, BorderLayout.CENTER);
        add(totalsPanelContainer, BorderLayout.SOUTH);

        applyDiscountCheckbox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateTotals();
            }
        });

        printReceiptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReceiptGenerator.generateReceipt();
            }
        });
    }

    public static FrameTwo getInstance() {
        return instance;
    }

    public void updateInvoiceDisplay() {
        invoiceDisplay.setText(Invoice.getInstance().getInvoiceDetails());
        updateTotals();
    }

    private void updateTotals() {
        double subtotal = Invoice.getInstance().calculateSubtotal();
        subtotalField.setText(String.format("%.2f", subtotal));

        double taxPercent = Double.parseDouble(taxField.getText());
        double taxAmount = subtotal * (taxPercent / 100);
        double totalWithTax = subtotal + taxAmount;
        totalField.setText(String.format("%.2f", totalWithTax));

        double discountPercent = 0;
        if (applyDiscountCheckbox.getState()) {
            try {
                discountPercent = Double.parseDouble(discountField.getText());
            } catch (NumberFormatException e) {
                discountPercent = 0;
            }
        }
        double discountAmount = totalWithTax * (discountPercent / 100);
        double totalAfterDiscount = totalWithTax - discountAmount;
        discountTotalField.setText(String.format("%.2f", totalAfterDiscount));

        grandTotalField.setText(String.format("%.2f", totalAfterDiscount));
    }
}
