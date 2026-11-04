import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Main engine class that processes user commands.
public class BotEngin {

    // Connects all command-related features.
    private final CommandProcessor commandProcessor =
            new CommandProcessor();

    // Stores the Weather API key securely in an environment variable.
    private static final String WEATHER_API_KEY =
            System.getenv("OPENWEATHER_API_KEY");

    // HTTP client used for weather API requests.
    private final HttpClient httpClient;

    // Constructor initializes the HTTP client.
    public BotEngin() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // Main method that decides how to respond to user input.
    public String getAnswer(String userInput) {

        String cleanInput = userInput.toLowerCase().trim();

        // 1. DESKTOP AUTOMATION
        if (cleanInput.startsWith("open ")) {
            return commandProcessor.processAutomationCommand(userInput);
        }

        // 2. CALCULATOR
        if (cleanInput.matches(".*\\d+.*[+*/-].*\\d+.*")) {
            return commandProcessor.processCalculation(userInput);
        }

        // 3. INTERNET SEARCH
        if (cleanInput.startsWith("search for ")) {

            String query = userInput.substring(11).trim();

            if (query.isEmpty()) {
                return "Please tell me what you want to search.";
            }

            return commandProcessor.processSearch(query);
        }

        // 4. CURRENT TIME
        if (cleanInput.equals("time")
                || cleanInput.contains("current time")) {
            return commandProcessor.getTime();
        }

        // 5. CURRENT DATE
        if (cleanInput.equals("date")
                || cleanInput.contains("today's date")) {
            return commandProcessor.getDate();
        }

        // 6. CURRENT DAY
        if (cleanInput.equals("day")
                || cleanInput.contains("which day")) {
            return commandProcessor.getDay();
        }

        // 7. LOCAL GREETINGS
        if (cleanInput.equals("hi")
                || cleanInput.equals("hello")
                || cleanInput.startsWith("hello ")
                || cleanInput.startsWith("hi ")) {
            return "Hello! How can I help you today?";
        }

        // Responds to questions about the assistant's identity.
        if (cleanInput.contains("your name")
                || cleanInput.contains("who are you")) {
            return "I am Jarvis Lite, a Java Swing desktop assistant.";
        }

        // Handles goodbye commands.
        if (cleanInput.equals("bye")
                || cleanInput.equals("exit")) {
            return "Goodbye! Best of luck with your project presentation.";
        }

        // 8. REAL-TIME WEATHER
        if (cleanInput.contains("weather")
                || cleanInput.contains("temperature")
                || cleanInput.contains("forecast")) {

            String city = extractCity(cleanInput);
            String weatherResult = fetchRealTimeWeather(city);

            // If weather cannot be fetched, search Google instead.
            if (weatherResult.startsWith("Could not")) {
                return commandProcessor.processSearch(
                        "weather in " + city
                );
            }

            return weatherResult;
        }

        // 9. GOOGLE SEARCH FALLBACK
        // All other questions and unknown commands are searched on Google.
        return commandProcessor.processSearch(userInput);
    }

    // Extracts the city name from a weather-related question.
    private String extractCity(String input) {

        String cleanedInput = input.replaceAll("[?!.]+$", "").trim();

        Matcher matcher = Pattern.compile(
                "(?i)(?:weather|temperature|forecast).*?(?:in|of)\\s+([A-Za-z][A-Za-z0-9' .-]{0,40})"
        ).matcher(cleanedInput);

        if (matcher.find()) {
            String city = matcher.group(1).trim();
            city = city.replaceAll("\\s+(today|tomorrow|now|please)$", "").trim();
            city = city.replaceAll("[?!.]+$", "").trim();

            if (!city.isEmpty()) {
                return city;
            }
        }

        // Uses Karachi as the default city.
        return "Karachi";
    }

    // Sends a request to OpenWeatherMap to get current weather.
    private String fetchRealTimeWeather(String city) {

        try {

            // Checks whether the Weather API key is configured.
            if (WEATHER_API_KEY == null || WEATHER_API_KEY.isBlank()) {
                return "Could not fetch weather: API key is not configured.";
            }

            String url =
                    "https://api.openweathermap.org/data/2.5/weather?q="
                    + java.net.URLEncoder.encode(
                            city,
                            java.nio.charset.StandardCharsets.UTF_8
                    )
                    + "&appid=" + WEATHER_API_KEY
                    + "&units=metric";

            // Creates an HTTP GET request.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Sends the request and receives the API response.
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            // Checks whether the request was successful.
            if (response.statusCode() == 200) {
                return parseWeatherJSON(response.body(), city);
            } else {
                return "Could not fetch weather for '" + city + "'.";
            }

        } catch (Exception e) {
            return "Could not fetch weather due to a network error.";
        }
    }

    // Extracts temperature and weather description from JSON response.
    private String parseWeatherJSON(String json, String city) {

        try {
            Matcher tempMatcher = Pattern.compile(
                    "\"temp\"\\s*:\\s*([-+]?\\d+(?:\\.\\d+)?)"
            ).matcher(json);

            if (!tempMatcher.find()) {
                return "Could not parse weather data.";
            }

            double tempCelsius = Double.parseDouble(tempMatcher.group(1));

            Matcher descriptionMatcher = Pattern.compile(
                    "\"description\"\\s*:\\s*\"([^\"\\]+)\""
            ).matcher(json);

            String description = "clear sky";
            if (descriptionMatcher.find()) {
                description = descriptionMatcher.group(1);
            }

            description = description.substring(0, 1).toUpperCase(Locale.ROOT)
                    + description.substring(1);

            // Returns formatted weather information.
            return String.format(
                    "Current weather in %s: %.1f°C with %s.",
                    toTitleCase(city),
                    tempCelsius,
                    description
            );

        } catch (Exception e) {
            return "Could not parse weather data.";
        }
    }

    private String toTitleCase(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "Karachi";
        }

        String[] words = value.trim().split("\\s+");
        StringBuilder titleCase = new StringBuilder();

        for (String word : words) {
            if (titleCase.length() > 0) {
                titleCase.append(" ");
            }

            String cleaned = word.replaceAll("[^A-Za-z0-9'-]", "");
            if (cleaned.isEmpty()) {
                continue;
            }

            titleCase.append(Character.toUpperCase(cleaned.charAt(0)))
                    .append(cleaned.substring(1).toLowerCase(Locale.ROOT));
        }

        return titleCase.toString();
    }
}
