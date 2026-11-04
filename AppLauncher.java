
import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class AppLauncher {

    private final Map<String, String> applications;

    public AppLauncher() {

        applications = new HashMap<>();

        // Windows applications
        applications.put("notepad", "notepad.exe");
        applications.put("calculator", "calc.exe");
        applications.put("file explorer", "explorer.exe");
        applications.put("paint", "mspaint.exe");
    }

    public String openApplication(String name) {

        String appName = name.toLowerCase().trim();

        if (!applications.containsKey(appName)) {
            return "Sorry, I don't recognize this application: " + name;
        }

        try {

            String command = applications.get(appName);

            new ProcessBuilder(command).start();

            return "Opening " + name + " for you.";

        } catch (Exception e) {

            return "Could not open " + name + ". Please check your system.";

        }
    }

    public String openWebsite(String website) {

        try {

            if (!Desktop.isDesktopSupported()) {
                return "Desktop browsing is not supported.";
            }

            String url = website.toLowerCase().trim();

            // Only allow predefined websites
            Map<String, String> websites = new HashMap<>();

            websites.put("youtube", "https://www.youtube.com");
            websites.put("google", "https://www.google.com");
            websites.put("github", "https://github.com");
            websites.put("chatgpt", "https://chatgpt.com");

            if (!websites.containsKey(url)) {
                return "This website is not in my allowed list.";
            }

            Desktop.getDesktop().browse(
                new URI(websites.get(url))
            );

            return "Opening " + url + " in your browser.";

        } catch (Exception e) {

            return "Could not open website. Please check your browser.";

        }
    }
}
