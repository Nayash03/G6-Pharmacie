import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is responsible for reading and displaying the pharmacy's order history.
 * It retrieves the order list from a JSON file, sorts them in descending chronological order,
 * and prints the details of each order.
 *
 * <p><strong>Acceptance Criteria:</strong></p>
 * <ul>
 *   <li>The history must include the date, time, and details of each order.</li>
 *   <li>Orders must be sorted in descending chronological order.</li>
 *   <li>Uses collections (List/ArrayList) to store orders.</li>
 * </ul>
 *
 * @author YourName
 * @version 1.0
 */
public class ReadRequest {

    private static final String FILE_PATH = "commands.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Initializes the process of reading, sorting, and displaying order history.
     * It retrieves orders from the JSON file, sorts them by date in descending order,
     * and then displays them.
     */
    public static void init() {
        List<Map<String, Object>> request = readRequest();

        if (request != null) {
            // Sort orders in descending order based on their date
            request.sort((c1, c2) -> {
                String dateStr1 = (String) c1.get("date");
                String dateStr2 = (String) c2.get("date");

                return parseDate(dateStr2).compareTo(parseDate(dateStr1));
            });

            afficherCommandes(request);
        } else {
            System.out.println("Error reading orders.");
        }
    }

    /**
     * Reads the JSON file containing order history and converts it into a list of maps.
     * Each map represents an order with its details (date, delivery type, products, etc.).
     *
     * @return A list of orders as `List<Map<String, Object>>`, or `null` if an error occurs during reading.
     */
    public static List<Map<String, Object>> readRequest() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<List<Map<String, Object>>>() {}.getType());
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            return null;
        }
    }

    /**
     * Displays the order history in a readable format.
     * It prints each order's date, delivery type, and product list.
     *
     * @param commandes The list of orders to display.
     */
    public static void afficherCommandes(List<Map<String, Object>> commandes) {
        System.out.println("Order History (" + commandes.size() + " orders):\n");
        for (Map<String, Object> commande : commandes) {
            System.out.println("Date: " + commande.get("date"));
            System.out.println("Delivery Type: " + commande.get("livraison"));
            System.out.println("Products:");

            // Retrieve and print each product in the order
            List<Map<String, Object>> produits = (List<Map<String, Object>>) commande.get("produits");
            for (Map<String, Object> produit : produits) {
                System.out.println("   - " + produit.get("nom") + " (Quantity: " + produit.get("quantite") + ")");
            }
            System.out.println("------------------------------------");
        }
    }

    /**
     * Parses a date string into a `Date` object.
     * If parsing fails, it returns a default date (January 1, 1970).
     *
     * @param dateStr The date string to parse.
     * @return A `Date` object representing the parsed date.
     */
    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(dateStr);
        } catch (Exception e) {
            return new Date(0);
        }
    }
}
