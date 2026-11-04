
import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchHandler {

    public String searchGoogle(String query) {

        try {
            if (!Desktop.isDesktopSupported()) {
                return "Browser is not supported on this system.";
            }

            String encodedQuery = URLEncoder.encode(
                    query,
                    StandardCharsets.UTF_8
            );

            String url = "https://www.google.com/search?q=" + encodedQuery;

            Desktop.getDesktop().browse(new URI(url));

            return "Searching Google for: " + query;

        } catch (Exception e) {
            return "Sorry, I could not open Google search.";
        }
    }
}
