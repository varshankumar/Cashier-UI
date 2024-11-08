import java.awt.*;
import java.awt.event.*;

public class FrameOne extends Panel {
    private TextField firstNameField, lastNameField, shiftStartField, shiftEndField;
    private Button startShiftButton, endShiftButton;
    private Button loadInventoryButton, showProductsButton;
    private Inventory inventory;
    private TextField productCodeField, quantityField;
    private Button addButton, removeButton;

    public FrameOne() {
        setLayout(new GridLayout(3, 1));

        Panel panel1Container = new Panel(new BorderLayout());
        Panel panel1 = new Panel(new FlowLayout());
        Label panel1Title = new Label("Cashier Shift", Label.CENTER);
        panel1Container.add(panel1Title, BorderLayout.NORTH);
        panel1Container.add(panel1, BorderLayout.CENTER);

        Panel panel2Container = new Panel(new BorderLayout());
        Panel panel2 = new Panel(new FlowLayout());
        Label panel2Title = new Label("Inventory", Label.CENTER);
        panel2Container.add(panel2Title, BorderLayout.NORTH);
        panel2Container.add(panel2, BorderLayout.CENTER);

        Panel panel3Container = new Panel(new BorderLayout());
        Panel panel3 = new Panel(new FlowLayout());
        Label panel3Title = new Label("Product Entry", Label.CENTER);
        panel3Container.add(panel3Title, BorderLayout.NORTH);
        panel3Container.add(panel3, BorderLayout.CENTER);

        // Panel 1 setup
        firstNameField = new TextField(10);
        lastNameField = new TextField(10);
        shiftStartField = new TextField(20);
        shiftStartField.setEditable(false);
        shiftEndField = new TextField(20);
        shiftEndField.setEditable(false);
        startShiftButton = new Button("Start Shift");
        endShiftButton = new Button("End Shift");

        panel1.add(new Label("First Name:"));
        panel1.add(firstNameField);
        panel1.add(new Label("Last Name:"));
        panel1.add(lastNameField);
        panel1.add(startShiftButton);
        panel1.add(new Label("Shift Start Time:"));
        panel1.add(shiftStartField);
        panel1.add(endShiftButton);
        panel1.add(new Label("Shift End Time:"));
        panel1.add(shiftEndField);

        // Panel 2 setup
        loadInventoryButton = new Button("Load Inventory");
        showProductsButton = new Button("Show Product List");

        panel2.add(loadInventoryButton);
        panel2.add(showProductsButton);

        // Panel 3 setup
        productCodeField = new TextField(10);
        quantityField = new TextField(5);
        addButton = new Button("Add");
        removeButton = new Button("Remove");

        panel3.add(new Label("Product Code:"));
        panel3.add(productCodeField);
        panel3.add(new Label("Quantity:"));
        panel3.add(quantityField);
        panel3.add(addButton);
        panel3.add(removeButton);

        // Add panels to FrameOne
        add(panel1Container);
        add(panel2Container);
        add(panel3Container);

        inventory = Inventory.getInstance();

        startShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                if (firstName.isEmpty() || lastName.isEmpty()) {
                    showMessage("Please enter your first and last name.");
                } else {
                    CashierSession.getInstance().startShift(firstName, lastName);
                    shiftStartField.setText(CashierSession.getInstance().getShiftStartTime());
                }
            }
        });

        endShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CashierSession.getInstance().endShift();
                shiftEndField.setText(CashierSession.getInstance().getShiftEndTime());
                Invoice.getInstance().clearInvoice();
                FrameTwo.getInstance().updateInvoiceDisplay();
            }
        });

        loadInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventory.loadInventory();
                showMessage("Inventory loaded successfully.");
            }
        });

        showProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String productCode = productCodeField.getText().trim();
                if (productCode.endsWith("*")) {
                    String partialCode = productCode.substring(0, productCode.length() - 1);
                    inventory.showProducts(partialCode);
                } else {
                    inventory.showProducts("");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String code = productCodeField.getText().trim();
                String qtyStr = quantityField.getText().trim();
                try {
                    int quantity = Integer.parseInt(qtyStr);
                    Product product = inventory.getProduct(code);
                    if (product != null) {
                        Invoice.getInstance().addItem(new InvoiceItem(product, quantity));
                        FrameTwo.getInstance().updateInvoiceDisplay();
                    } else {
                        showMessage("The product code entered does not exist.");
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Please enter a valid quantity.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lineStr = productCodeField.getText().trim();
                try {
                    int lineNumber = Integer.parseInt(lineStr);
                    Invoice.getInstance().removeItem(lineNumber - 1);
                    FrameTwo.getInstance().updateInvoiceDisplay();
                } catch (NumberFormatException ex) {
                    showMessage("Please enter a valid line number.");
                }
            }
        });
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
