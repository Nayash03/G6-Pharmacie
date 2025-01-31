import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

/**
 * The DeleteProduct class provides functionality to delete a product from the pharmacy stock.
 */
public class DeleteProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Initializes the DeleteProduct process by invoking the deleteProduct method.
     */
    public static void init() {
        DeleteProduct deleteProduct = new DeleteProduct();
        try {
            deleteProduct.deleteProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a product from the pharmacy stock based on user input.
     * 
     * @throws IOException if an I/O error occurs.
     */
    public void deleteProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID du produit à supprimer : ");
        int productId = scanner.nextInt();

        Pharmacy pharmacie = lireFichier();
        if (pharmacie == null) return;

        boolean removed = supprimerProduit(pharmacie, productId);

        if (removed) {
            ecrireFichier(pharmacie);
            System.out.println("Produit supprimé avec succès !");
        } else {
            System.out.println("Produit non trouvé.");
        }
    }

    /**
     * Reads the pharmacy stock from the JSON file.
     * 
     * @return the Pharmacy object containing the stock information, or null if an error occurs.
     */
    private Pharmacy lireFichier() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            if (wrapper != null) {
                return wrapper.getPharmacie();
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    /**
     * Writes the updated pharmacy stock to the JSON file.
     * 
     * @param pharmacie the Pharmacy object containing the updated stock information.
     */
    private void ecrireFichier(Pharmacy pharmacie) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            PharmacyWrapper wrapper = new PharmacyWrapper();
            wrapper.setPharmacie(pharmacie);
            gson.toJson(wrapper, writer);
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier.");
        }
    }

    /**
     * Removes a product from the pharmacy stock based on the product ID.
     * 
     * @param pharmacie the Pharmacy object containing the stock information.
     * @param productId the ID of the product to be removed.
     * @return true if the product was successfully removed, false otherwise.
     */
    private boolean supprimerProduit(Pharmacy pharmacie, int productId) {
        for (int i = 0; i < pharmacie.getProduits().size(); i++) {
            ProductCategory categorie = pharmacie.getProduits().get(i);

            for (int j = 0; j < categorie.getProduits().size(); j++) {
                Product produit = categorie.getProduits().get(j);

                if (produit.getId() == productId) {
                    categorie.getProduits().remove(j);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Unsupported operation for adding a product.
     * 
     * @throws UnsupportedOperationException always thrown as this class does not support adding products.
     */
    @Override
    public void addProduct() throws IOException {
        throw new UnsupportedOperationException("DeleteProduct does not support adding products.");
    }
}