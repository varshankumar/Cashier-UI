import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

public class FrameOne extends Panel {
    private TextField firstNameField, lastNameField, shiftStartField, shiftEndField;
    private Button startShiftButton, endShiftButton;
    private Button loadInventoryButton, showProductsButton;
    private Inventory inventory;
    private TextField productCodeField, quantityField;
    private Button addButton, removeButton;
    private Label taxLabel;
    private TextField taxField;
    private Label discountLabel; 
    private TextField discountField;

    public FrameOne() {
        setLayout(new GridLayout(3, 1, 10, 10));

        // Cashier Panel
        Panel cashierPanel = new Panel(new GridBagLayout());
        cashierPanel.setBorder(new TitledBorder("Cashier Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        // ...setup GridBagConstraints...

        gbc.gridx = 0; gbc.gridy = 0;
        cashierPanel.add(new Label("First Name:"), gbc);
        gbc.gridx = 1;
        cashierPanel.add(firstNameField, gbc);
        // ...add other cashier components with GridBagConstraints...

        // Inventory Panel
        Panel inventoryPanel = new Panel(new GridBagLayout());
        inventoryPanel.setBorder(new TitledBorder("Inventory Management"));
        // ...add inventory components...

        // Product Entry Panel
        Panel productPanel = new Panel(new GridBagLayout());
        productPanel.setBorder(new TitledBorder("Product Entry"));
        // ...add product entry components...

        add(cashierPanel);
        add(inventoryPanel);
        add(productPanel);
    }

    private void calculateTotal() {
        double subtotal = /* your subtotal calculation */;
        double tax = subtotal * 0.10; // 10% tax
        double discount = /* your discount calculation */;
        double total = subtotal + tax - discount;

        // Update display fields
        taxField.setText(String.format("$%.2f", tax));
        discountField.setText(String.format("$%.2f", discount));
        totalField.setText(String.format("$%.2f", total));
    }

    private void showMessage(String message) {
        Dialog dialog = new Dialog((Frame) this.getParent(), "Message", true);
        dialog.setLayout(new FlowLayout());
        dialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.add(okButton);
        dialog.setSize(300, 100);
        dialog.setVisible(true);
    }
}
