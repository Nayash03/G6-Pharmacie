import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

public class DeleteProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        DeleteProduct deleteProduct = new DeleteProduct();
        try {
            deleteProduct.deleteProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    private void ecrireFichier(Pharmacy pharmacie) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            PharmacyWrapper wrapper = new PharmacyWrapper();
            wrapper.setPharmacie(pharmacie);
            gson.toJson(wrapper, writer);
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier.");
        }
    }

    private boolean supprimerProduit(Pharmacy pharmacie, int productId) {
        // Parcours de la liste des catégories de produits
        for (int i = 0; i < pharmacie.getProduits().size(); i++) {
            ProductCategory categorie = pharmacie.getProduits().get(i); // Récupération de la catégorie

            // Parcours de la liste des produits dans cette catégorie
            for (int j = 0; j < categorie.getProduits().size(); j++) {
                Product produit = categorie.getProduits().get(j); // Récupération du produit

                // Vérification si l'ID du produit correspond à celui à supprimer
                if (produit.getId() == productId) {
                    categorie.getProduits().remove(j); // Suppression du produit de la liste
                    return true; // Retourne vrai car le produit a été supprimé
                }
            }
        }
        return false; // Retourne faux si aucun produit avec l'ID donné n'a été trouvé
    }

    @Override
    public void addProduct() throws IOException {

        throw new UnsupportedOperationException("DeleteProduct does not support adding products.");
    }
}
