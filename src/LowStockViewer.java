import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

/**
 * The LowStockViewer class is responsible for reading the pharmacy stock data from a JSON file,
 * identifying products with low stock, and displaying them.
 */
public class LowStockViewer {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Initializes the LowStockViewer and displays the low stock products.
     */
    public static void init() {
        LowStockViewer viewer = new LowStockViewer();
        viewer.displayLowStockProducts();
    }

    /**
     * Reads the pharmacy stock data, identifies products with stock less than 5,
     * sorts them by quantity, and displays them.
     */
    public void displayLowStockProducts() {
        Pharmacy pharmacie = lireFichier();
        if (pharmacie == null) return;

        List<Product> lowStockProducts = new ArrayList<>();

        for (ProductCategory categorie : pharmacie.getProduits()) {
            for (Product produit : categorie.getProduits()) {
                if (produit.getQuantiteStock() < 5) {
                    lowStockProducts.add(produit);
                }
            }
        }

        if (lowStockProducts.isEmpty()) {
            System.out.println("Tous les produits ont un stock suffisant.");
            return;
        }

        triABulles(lowStockProducts);

        System.out.println("Produits avec un stock inférieur à 5 (triés par quantité) :");
        for (Product produit : lowStockProducts) {
            System.out.println("ID: " + produit.getId() +
                    ", Nom: " + produit.getNom() +
                    ", Quantité: " + produit.getQuantiteStock() +
                    ", Prix: " + produit.getPrix() + "€");
        }
    }

    /**
     * Reads the pharmacy stock data from the JSON file.
     *
     * @return the Pharmacy object containing the stock data, or null if an error occurs.
     */
    private Pharmacy lireFichier() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            return wrapper != null ? wrapper.getPharmacie() : null;
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    /**
     * Sorts the list of products using the bubble sort algorithm based on their stock quantity.
     *
     * @param produits the list of products to be sorted.
     */
    private void triABulles(List<Product> produits) {
        int n = produits.size();
        boolean echangeEffectue;

        for (int i = 0; i < n - 1; i++) {
            echangeEffectue = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (produits.get(j).getQuantiteStock() > produits.get(j + 1).getQuantiteStock()) {
                    Product temp = produits.get(j);
                    produits.set(j, produits.get(j + 1));
                    produits.set(j + 1, temp);
                    echangeEffectue = true;
                }
            }

            if (!echangeEffectue) break;
        }
    }

}