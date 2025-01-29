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

    private Pharmacy lireFichier() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            return wrapper != null ? wrapper.getPharmacie() : null;
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    @Override
    public void listProducts() throws IOException {

        Pharmacy pharmacie = lireFichier();
        if (pharmacie == null) {
            System.out.println("Erreur: Impossible de charger les données de la pharmacie.");
            return;
        }

        List<Product> allProducts = new ArrayList<>();
        for (ProductCategory category : pharmacie.getProduits()) {
            allProducts.addAll(category.getProduits());
        }

        allProducts.sort(Comparator.comparing(Product::getNom));

        System.out.println("Liste des produits disponibles (triée par nom) :");
        for (Product product : allProducts) {
            System.out.println("Nom : " + product.getNom());
            System.out.println("Prix : " + product.getPrix() + " €");
            System.out.println("Quantité en stock : " + product.getQuantiteStock());
            System.out.println("Catégorie : " + getCategoryName(pharmacie, product));
            System.out.println("--------------------------------------------");
        }

    }

    private String getCategoryName(Pharmacy pharmacie, Product product) {
        for (ProductCategory category : pharmacie.getProduits()) {
            for (Product p : category.getProduits()) {
                if (p.getId() == product.getId()) {
                    return category.getCategorie() + " - " + category.getSousCategorie();
                }
            }
        }
        return "Non catégorisé";
    }


}