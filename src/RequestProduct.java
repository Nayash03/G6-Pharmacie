import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

/**
 * This class handles customer product requests in a pharmacy system.
 * It verifies stock availability, updates inventory, and records orders in a JSON file.
 *
 * <p><strong>Main Responsibilities:</strong></p>
 * <ul>
 *   <li>Register customer requests.</li>
 *   <li>Check stock availability and update inventory.</li>
 *   <li>Save order details in a JSON file.</li>
 * </ul>
 *
 * @author YourName
 * @version 1.0
 */
public class RequestProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Main method to test the order registration process.
     * It creates a sample order and registers it in the system.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Map<String, Integer> commandeClient = new HashMap<>();
        commandeClient.put("Paracetamol", 30);
        commandeClient.put("Ibuprofen", 30);
        registerRequest(commandeClient, "standard");
        registerRequest(commandeClient, "express");
    }

    /**
     * Adds a product to the stock (not yet implemented).
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void addProduct() throws IOException {
        // TODO: Implement method for adding a product
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Registers a customer's order request by verifying product availability,
     * updating stock, and saving the request.
     *
     * @param produitsDemandes A map containing product names as keys and requested quantities as values.
     * @param typeLivraison The type of delivery (e.g., "standard" or "express").
     * @return {@code true} if the order was successfully registered, {@code false} otherwise.
     */
    public static boolean registerRequest(Map<String, Integer> produitsDemandes, String typeLivraison) {
        // Read the pharmacy stock from a JSON file
        Pharmacy pharmacie = FileHelper.lireFichier(FILE_PATH);
        if (pharmacie == null) {
            System.out.println("Error reading stock data.");
            return false;
        }

        Map<Product, Integer> produitsValidés = new HashMap<>();

        // Verify product availability
        for (Map.Entry<String, Integer> entry : produitsDemandes.entrySet()) {
            String nomProduit = entry.getKey();
            int quantiteDemandée = entry.getValue();

            Product produitTrouvé = pharmacie.getProduits().stream()
                    .flatMap(pc -> pc.getProduits().stream())
                    .filter(p -> p.getNom().equalsIgnoreCase(nomProduit))
                    .findFirst()
                    .orElse(null);

            if (produitTrouvé == null || produitTrouvé.getQuantiteStock() < quantiteDemandée) {
                System.out.println("Insufficient stock or product not found: " + nomProduit);
                return false;
            }

            produitsValidés.put(produitTrouvé, quantiteDemandée);
        }

        // Deduct the ordered quantity from the stock
        for (Map.Entry<Product, Integer> entry : produitsValidés.entrySet()) {
            entry.getKey().setQuantiteStock(entry.getKey().getQuantiteStock() - entry.getValue());
        }

        // Update stock file and save the order request
        AddProduct.ecrireFichier(pharmacie);
        writeRequestFile(produitsValidés, typeLivraison);
        System.out.println("Order successfully registered!");
        return true;
    }

    /**
     * Writes the validated order details into the `commands.json` file.
     *
     * @param produitsCommandes A map of ordered products and their quantities.
     * @param typeLivraison The type of delivery.
     */
    private static void writeRequestFile(Map<Product, Integer> produitsCommandes, String typeLivraison) {
        List<Map<String, Object>> commandes = new ArrayList<>();

        // Read existing orders from file (if available)
        try (Reader reader = new FileReader("commands.json")) {
            commandes = gson.fromJson(reader, List.class);
            if (commandes == null) {
                commandes = new ArrayList<>();
            }
        } catch (IOException e) {}

        // Create a new order record
        Map<String, Object> nouvelleCommande = new LinkedHashMap<>();
        nouvelleCommande.put("livraison", typeLivraison);
        nouvelleCommande.put("date", new Date().toString());

        // Add ordered products
        List<Map<String, Object>> produits = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : produitsCommandes.entrySet()) {
            Map<String, Object> produitInfo = new HashMap<>();
            produitInfo.put("nom", entry.getKey().getNom());
            produitInfo.put("quantite", entry.getValue());
            produits.add(produitInfo);
        }
        nouvelleCommande.put("produits", produits);

        // Add new order to the list and update the file
        commandes.add(0, nouvelleCommande);

        try (Writer writer = new FileWriter("commands.json")) {
            gson.toJson(commandes, writer);
        } catch (IOException e) {}
    }
}
