import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CashierApp extends Frame {
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