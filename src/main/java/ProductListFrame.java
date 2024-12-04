import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProductListFrame extends Frame {
    private TextArea productList;
    
    public ProductListFrame() {
        setTitle("Product List");
        setLayout(new BorderLayout(10, 10));
        
        productList = new TextArea(20, 50);
        productList.setEditable(false);
        add(productList, BorderLayout.CENTER);
        
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> {
            dispose();
            setVisible(false);
        });
        
        Panel buttonPanel = new Panel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add window closing handler
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                setVisible(false);
            }
        });
        
        setSize(500, 400);
    }
    
    public void setProducts(String formattedList) {
        productList.setText(formattedList);
    }
}
