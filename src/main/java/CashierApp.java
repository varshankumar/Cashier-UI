import java.awt.*;
import java.awt.event.*;

public class CashierApp extends Frame {
    public static void main(String[] args) {
        new CashierApp();
    }

    public CashierApp() {
        setTitle("Cashier Application");
        setSize(800, 600);
        setLayout(new GridLayout(1, 2));

        FrameOne frameOne = new FrameOne();
        FrameTwo frameTwo = FrameTwo.getInstance();
        
        add(frameOne);
        add(frameTwo);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frameOne.dispose();
                frameTwo.dispose();
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);
    }
}
