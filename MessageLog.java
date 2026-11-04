import java.util.ArrayList;
import java.util.List;

public class MessageLog {
    private final List<String> history = new ArrayList<>();

    // Add a formatted message to history
    public void addMessage(String sender, String text) {
        String entry = sender + ": " + text;
        history.add(entry);
    }

    // Get all past history as a single block of text
    public String getFullHistory() {
        StringBuilder builder = new StringBuilder();
        for (String msg : history) {
            builder.append(msg).append("\n\n");
        }
        return builder.toString();
    }
}