import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

public class ListProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        ListProduct listProduct = new ListProduct();
        try {
            listProduct.listProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Implements the addProduct() method from Stockable interface
    @Override
    public void addProduct() throws IOException {
        // TODO: Add implementation for adding a product
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    // Removed @Override since listProducts() is not from the interface
    public void listProducts() throws IOException {
        Pharmacy pharmacie = FileHelper.lireFichier(FILE_PATH);
        if (pharmacie == null) {
            System.out.println("Erreur: Impossible de charger les données de la pharmacie.");
            return;
        }

        List<Product> allProducts = new ArrayList<>();
        for (ProductCategory category : pharmacie.getProduits()) {
            allProducts.addAll(category.getProduits());
        }

        // Sort by product name
        allProducts.sort(Comparator.comparing(Product::getNom));

        // Print products
        System.out.println("Liste des produits disponibles (triée par nom) :");
        for (Product product : allProducts) {
            System.out.println("Nom : " + product.getNom());
            System.out.println("Prix : " + product.getPrix() + " €");
            System.out.println("Quantité en stock : " + product.getQuantiteStock());
            System.out.println("Catégorie : " + getCategoryName(pharmacie, product));
            System.out.println("--------------------------------------------");
        }
    }

    // Helper method to find the category of a product
    private String getCategoryName(Pharmacy pharmacie, Product product) {
        for (ProductCategory category : pharmacie.getProduits()) {
            if (category.getProduits().contains(product)) {
                return category.getCategorie() + " - " + category.getSousCategorie();
            }
        }
        return "Non catégorisé";
    }
}