import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public class CurrencyConverter {

        private static final String API_KEY = "4700161e63ffdaf095473355";
        private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    }

    public static void main(String[] args) {

        boolean continueProgram = true;
        while (continueProgram) {
            System.out.println("=== Currency Converter ===");
            System.out.println("1. Convert Currency");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            try {
                int option = Integer.parseInt(CurrencyConverter.reader.readLine());
                switch (option) {
                    case 1:
                        convertCurrency();
                        break;
                    case 2:
                        continueProgram = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Input error. Please try again.");
            }
        }
    }

    public static void convertCurrency() throws IOException {
        System.out.println("\n=== Currency Converter ===");
        System.out.print("Enter the source currency (e.g., USD, EUR): ");
        String fromCurrency = CurrencyConverter.reader.readLine().toUpperCase();

        System.out.print("Enter the target currency (e.g., USD, EUR): ");
        String toCurrency = CurrencyConverter.reader.readLine().toUpperCase();

        System.out.print("Enter the amount to convert: ");
        double amount = Double.parseDouble(CurrencyConverter.reader.readLine());

        double convertedAmount = performConversion(fromCurrency, toCurrency, amount);
        if (convertedAmount != -1) {
            System.out.println(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);
        } else {
            System.out.println("Error converting currency. Please check your inputs and try again.");
        }
    }

    public static double performConversion(String fromCurrency, String toCurrency, double amount) {
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + fromCurrency + "&symbols=" + toCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();

            JSONObject json = new JSONObject(response.toString());
            double rate = json.getJSONObject("rates").getDouble(toCurrency);
            return amount * rate;
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Return -1 in case of error
        }
    }
}

