import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

/**
 * This class provides functionality to search for products in a pharmacy's stock.
 */
public class SearchProduct {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Wrapper class for Pharmacy object to match the JSON structure.
     */
    public static class PharmacyWrapper {
        private Pharmacy pharmacie;

        /**
         * Gets the pharmacy object.
         * @return the pharmacy object.
         */
        public Pharmacy getPharmacie() {
            return pharmacie;
        }

        /**
         * Sets the pharmacy object.
         * @param pharmacie the pharmacy object to set.
         */
        public void setPharmacie(Pharmacy pharmacie) {
            this.pharmacie = pharmacie;
        }
    }

    /**
     * Initializes the search process by reading the pharmacy stock from a JSON file,
     * sorting the products, and performing a binary search based on user input.
     */
    public static void init() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            Pharmacy pharmacie = wrapper.getPharmacie();

            if (pharmacie == null) {
                System.out.println("Erreur: Structure JSON invalide.");
                return;
            }

            List<Product> allProducts = new ArrayList<>();
            for (ProductCategory category : pharmacie.getProduits()) {
                allProducts.addAll(category.getProduits());
            }

            allProducts.sort(Comparator.comparing(p -> p.getNom().toLowerCase(Locale.ROOT))
            );

            Scanner scanner = new Scanner(System.in, "UTF-8");
            System.out.print("Entrez le nom du produit à rechercher : ");
            String searchName = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            Product found = binarySearch(allProducts, searchName);
            if (found != null) {
                System.out.println("Le produit " + found.getNom() + " est disponible en quantité: " + found.getQuantiteStock());
            } else {
                System.out.println("Le produit n'existe pas.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a binary search on the list of products to find a product by name.
     * @param products the list of products to search.
     * @param target the name of the product to search for.
     * @return the product if found, otherwise null.
     */
    private static Product binarySearch(List<Product> products, String target) {
        int left = 0, right = products.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            String midName = products.get(mid).getNom().toLowerCase(Locale.ROOT);
            int cmp = midName.compareTo(target);
            if (cmp == 0) {
                return products.get(mid);
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}