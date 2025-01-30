import java.io.*;
import java.util.*;

/**
 * This class handles listing products available in a pharmacy's inventory.
 * It reads stock data from a JSON file and displays all products, sorted by name.
 *
 * <p><strong>Main Responsibilities:</strong></p>
 * <ul>
 *   <li>Retrieve and display all products.</li>
 *   <li>Sort products alphabetically by name.</li>
 *   <li>Show product details including price, stock quantity, and category.</li>
 * </ul>
 *
 * @author YourName
 * @version 1.0
 */
public class ListProduct implements Listable {
    private static final String FILE_PATH = "stocks_pharma.json";

    /**
     * Initializes the product listing process by creating an instance of {@code ListProduct}
     * and invoking the {@code listProducts} method.
     */
    public static void init() {
        ListProduct listProduct = new ListProduct();
        try {
            listProduct.listProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Lists all available products in the pharmacy's inventory.
     *
     * <p>This method:</p>
     * <ul>
     *   <li>Loads product data from the pharmacy's JSON file.</li>
     *   <li>Sorts products by name.</li>
     *   <li>Displays product details (name, price, stock, category).</li>
     * </ul>
     *
     * @throws IOException If an error occurs while reading the stock file.
     */
    @Override
    public void listProducts() throws IOException {
        Pharmacy pharmacie = FileHelper.lireFichier(FILE_PATH);
        if (pharmacie == null) {
            System.out.println("Error: Unable to load pharmacy data.");
            return;
        }

        List<Product> allProducts = new ArrayList<>();
        for (ProductCategory category : pharmacie.getProduits()) {
            allProducts.addAll(category.getProduits());
        }

        // Sort by product name
        allProducts.sort(Comparator.comparing(Product::getNom));

        // Print products
        System.out.println("List of available products (sorted by name):");
        for (Product product : allProducts) {
            System.out.println("Name: " + product.getNom());
            System.out.println("Price: " + product.getPrix() + " €");
            System.out.println("Stock quantity: " + product.getQuantiteStock());
            System.out.println("Category: " + getCategoryName(pharmacie, product));
            System.out.println("--------------------------------------------");
        }
    }

    /**
     * Retrieves the category name of a given product.
     *
     * @param pharmacie The pharmacy object containing product categories.
     * @param product The product for which the category is being retrieved.
     * @return The category and subcategory name of the product, or "Uncategorized" if not found.
     */
    private String getCategoryName(Pharmacy pharmacie, Product product) {
        for (ProductCategory category : pharmacie.getProduits()) {
            if (category.getProduits().contains(product)) {
                return category.getCategorie() + " - " + category.getSousCategorie();
            }
        }
        return "Uncategorized";
    }
}