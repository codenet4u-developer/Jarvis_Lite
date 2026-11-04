
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHandler {

    public String getCurrentTime() {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a");

        return "Current time is " + now.format(formatter);
    }

    public String getCurrentDate() {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMMM yyyy");

        return "Today's date is " + now.format(formatter);
    }

    public String getCurrentDay() {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEEE");

        return "Today is " + now.format(formatter);
    }
}
