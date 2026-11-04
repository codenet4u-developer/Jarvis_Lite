
 // Connects all command-related classes of Jarvis Lite.
public class CommandProcessor {

    // Objects of different feature classes.
    private final AppLauncher appLauncher;
    private final Calculator calculator;
    private final SearchHandler searchHandler;
    private final DateTimeHandler dateTimeHandler;

    // Constructor initializes all feature objects.
    public CommandProcessor() {

        appLauncher = new AppLauncher();
        calculator = new Calculator();
        searchHandler = new SearchHandler();
        dateTimeHandler = new DateTimeHandler();
    }

    // Processes commands related to opening applications and websites.
    public String processAutomationCommand(String input) {

        String command = input.toLowerCase().trim();

        // Checks whether the command starts with "open".
        if (!command.startsWith("open ")) {
            return "Invalid automation command.";
        }

        // Extracts the application or website name.
        String target = command.substring(5).trim();

        if (target.isEmpty()) {
            return "Please tell me what you want to open.";
        }

        // Checks whether the requested target is a supported website.
        if (target.equals("youtube")
                || target.equals("google")
                || target.equals("github")
                || target.equals("chatgpt")) {

            return appLauncher.openWebsite(target);
        }

        // If it is not a website, try opening a desktop application.
        return appLauncher.openApplication(target);
    }

    // Sends mathematical expressions to the Calculator class.
    public String processCalculation(String expression) {
        return calculator.calculate(expression);
    }

    // Sends search queries to the SearchHandler class.
    public String processSearch(String query) {
        return searchHandler.searchGoogle(query);
    }

    // Gets the current time from DateTimeHandler.
    public String getTime() {
        return dateTimeHandler.getCurrentTime();
    }

    // Gets today's date from DateTimeHandler.
    public String getDate() {
        return dateTimeHandler.getCurrentDate();
    }

    // Gets the current day from DateTimeHandler.
    public String getDay() {
        return dateTimeHandler.getCurrentDay();
    }
}
