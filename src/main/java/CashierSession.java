import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashierSession {
    private static CashierSession instance = new CashierSession();
    private String firstName;
    private String lastName;
    private String shiftStartTime;
    private String shiftEndTime;

    private CashierSession() {}

    public static CashierSession getInstance() {
        return instance;
    }

    public void startShift(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        shiftStartTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void endShift() {
        shiftEndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        firstName = null;
        lastName = null;
    }

    public String getShiftStartTime() { return shiftStartTime; }
    public String getShiftEndTime() { return shiftEndTime; }
    public String getCashierName() { return firstName + " " + lastName; }
}