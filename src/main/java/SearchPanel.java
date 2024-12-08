import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class SearchPanel extends Panel {
    private TextField searchField;
    private java.awt.List resultList; 
    private FrameTwo parentFrame;

    public SearchPanel(FrameTwo parent) {
        this.parentFrame = parent;
        setLayout(new BorderLayout());
        
        Panel searchArea = new Panel();
        searchField = new TextField(20);
        Button searchButton = new Button("Search");
        searchArea.add(new Label("Search Items:"));
        searchArea.add(searchField);
        searchArea.add(searchButton);
        resultList = new java.awt.List(5);
        
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
        
        resultList.addActionListener(e -> {
            String selected = resultList.getSelectedItem();
            if (selected != null) {
                parentFrame.addItemToInvoice(selected);
            }
        });
        
        add(searchArea, BorderLayout.NORTH);
        add(resultList, BorderLayout.CENTER);
    }
    
    private void performSearch() {
        String query = searchField.getText().toLowerCase();
        resultList.removeAll();
        
        Inventory.getInstance().getProducts()
            .stream()
            .filter(p -> p.getName().toLowerCase().contains(query))
            .forEach(p -> resultList.add(String.format("[%s] %s - $%.2f", 
                p.getCode(), p.getName(), p.getPrice())));
    }
}
