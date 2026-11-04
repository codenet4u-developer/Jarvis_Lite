
import java.math.BigDecimal;
import java.math.RoundingMode;

// Calculator class handles mathematical expressions.
public class Calculator {

    // Main method called by CommandProcessor.
    public String calculate(String expression) {

        try {

            // Create a parser for the user's expression.
            ExpressionParser parser = new ExpressionParser(expression);

            // Calculate the final result.
            double result = parser.parse();

            // Round the result to avoid unnecessary decimal digits.
            BigDecimal answer = BigDecimal.valueOf(result)
                    .setScale(10, RoundingMode.HALF_UP)
                    .stripTrailingZeros();

            // Return a clean answer.
            return "Answer: " + answer.toPlainString();

        } catch (ArithmeticException e) {

            return "Error: Cannot divide by zero.";

        } catch (Exception e) {

            return "Invalid calculation. Please enter a valid expression.";
        }
    }

    // Inner class responsible for reading and calculating expressions.
    private static class ExpressionParser {

        private final String input;
        private int position = 0;

        // Constructor receives the mathematical expression.
        public ExpressionParser(String input) {
            this.input = input;
        }

        // Starts calculation and checks for extra invalid characters.
        public double parse() {

            double result = parseExpression();

            skipSpaces();

            if (position != input.length()) {
                throw new IllegalArgumentException();
            }

            return result;
        }

        // Handles addition and subtraction.
        private double parseExpression() {

            double result = parseTerm();

            while (true) {

                skipSpaces();

                if (match('+')) {
                    result += parseTerm();
                }
                else if (match('-')) {
                    result -= parseTerm();
                }
                else {
                    return result;
                }
            }
        }

        // Handles multiplication and division.
        private double parseTerm() {

            double result = parseFactor();

            while (true) {

                skipSpaces();

                if (match('*')) {
                    result *= parseFactor();
                }
                else if (match('/')) {

                    double divisor = parseFactor();

                    if (divisor == 0) {
                        throw new ArithmeticException();
                    }

                    result /= divisor;
                }
                else {
                    return result;
                }
            }
        }

        // Handles numbers, negative values and brackets.
        private double parseFactor() {

            skipSpaces();

            // Handles positive and negative numbers.
            if (match('+')) {
                return parseFactor();
            }

            if (match('-')) {
                return -parseFactor();
            }

            // Handles expressions inside brackets.
            if (match('(')) {

                double result = parseExpression();

                skipSpaces();

                if (!match(')')) {
                    throw new IllegalArgumentException();
                }

                return result;
            }

            // Reads a numeric value.
            return parseNumber();
        }

        // Reads decimal and whole numbers.
        private double parseNumber() {

            skipSpaces();

            int start = position;
            boolean decimalFound = false;

            while (position < input.length()) {

                char current = input.charAt(position);

                if (Character.isDigit(current)) {
                    position++;
                }
                else if (current == '.' && !decimalFound) {
                    decimalFound = true;
                    position++;
                }
                else {
                    break;
                }
            }

            if (start == position) {
                throw new IllegalArgumentException();
            }

            String number = input.substring(start, position);

            return Double.parseDouble(number);
        }

        // Checks whether the current character matches the operator.
        private boolean match(char expected) {

            if (position < input.length()
                    && input.charAt(position) == expected) {

                position++;
                return true;
            }

            return false;
        }

        // Skips unnecessary spaces in the expression.
        private void skipSpaces() {

            while (position < input.length()
                    && Character.isWhitespace(input.charAt(position))) {

                position++;
            }
        }
    }
}
