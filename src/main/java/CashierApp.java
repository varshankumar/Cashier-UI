import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CashierApp extends Frame {
    /*
     * To run the program, go to root directory of the project and then run:
     * mvn compile
     * mvn exec:java
     */
    public static void main(String[] args) {
        new CashierApp();
    }

    public CashierApp() {
        setTitle("Cashier Application");
        setSize(800, 600);
        setLayout(new GridLayout(1, 2));

        add(new FrameOne());
        add(FrameTwo.getInstance());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }
}