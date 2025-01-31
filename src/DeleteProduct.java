import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

/**
 * Classe permettant de supprimer un produit d'une pharmacie en lisant et en modifiant le fichier JSON des stocks.
 * Elle implémente l'interface Stockable.
 */
public class DeleteProduct implements Stockable {

    // Chemin vers le fichier JSON contenant les données des produits
    private static final String FILE_PATH = "stocks_pharma.json";

    // Instance de Gson pour la sérialisation et la désérialisation des objets JSON
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Méthode principale pour exécuter la suppression d'un produit.
     * Elle demande l'ID du produit à supprimer et met à jour le fichier JSON.
     */
    public static void main(String[] args) {
        DeleteProduct deleteProduct = new DeleteProduct();
        try {
            // Appel de la méthode pour supprimer un produit
            deleteProduct.deleteProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Demande à l'utilisateur l'ID d'un produit à supprimer et met à jour le fichier JSON si le produit est trouvé.
     * 
     * @throws IOException Si une erreur de lecture ou d'écriture du fichier survient.
     */
    public void deleteProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Demande à l'utilisateur l'ID du produit à supprimer
        System.out.print("ID du produit à supprimer : ");
        int productId = scanner.nextInt();

        // Lecture du fichier JSON contenant les données de la pharmacie
        Pharmacy pharmacie = lireFichier();
        if (pharmacie == null) return;

        // Suppression du produit si trouvé
        boolean removed = supprimerProduit(pharmacie, productId);

        if (removed) {
            // Si le produit est supprimé, on met à jour le fichier JSON
            ecrireFichier(pharmacie);
            System.out.println("Produit supprimé avec succès !");
        } else {
            // Si aucun produit correspondant n'est trouvé
            System.out.println("Produit non trouvé.");
        }
    }

    /**
     * Lit le fichier JSON pour obtenir les informations sur la pharmacie.
     * 
     * @return L'objet Pharmacy contenant les données des produits et catégories, ou null si le fichier ne peut pas être lu.
     */
    private Pharmacy lireFichier() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            // Désérialisation du JSON dans un objet PharmacyWrapper
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            if (wrapper != null) {
                return wrapper.getPharmacie(); // Retourne l'objet Pharmacy si le fichier est valide
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    /**
     * Écrit les données mises à jour de la pharmacie dans le fichier JSON.
     * 
     * @param pharmacie L'objet Pharmacy contenant les données à enregistrer.
     */
    private void ecrireFichier(Pharmacy pharmacie) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            // Sérialisation de l'objet PharmacyWrapper en JSON
            PharmacyWrapper wrapper = new PharmacyWrapper();
            wrapper.setPharmacie(pharmacie);
            gson.toJson(wrapper, writer); // Écriture du JSON dans le fichier
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier.");
        }
    }

    /**
     * Supprime un produit d'une pharmacie en recherchant par ID dans les catégories de produits.
     * 
     * @param pharmacie L'objet Pharmacy contenant les catégories de produits.
     * @param productId L'ID du produit à supprimer.
     * @return true si le produit a été supprimé, sinon false.
     */
    private boolean supprimerProduit(Pharmacy pharmacie, int productId) {
        // Parcours de chaque catégorie de produits dans la pharmacie
        for (int i = 0; i < pharmacie.getProduits().size(); i++) {
            ProductCategory categorie = pharmacie.getProduits().get(i); // Récupération de la catégorie

            // Parcours de chaque produit dans la catégorie
            for (int j = 0; j < categorie.getProduits().size(); j++) {
                Product produit = categorie.getProduits().get(j); // Récupération du produit

                // Vérification si l'ID du produit correspond à celui demandé pour suppression
                if (produit.getId() == productId) {
                    // Suppression du produit de la liste de produits
                    categorie.getProduits().remove(j);
                    return true; // Retourne true pour indiquer que le produit a été supprimé
                }
            }
        }
        return false; // Retourne false si aucun produit n'a été trouvé avec l'ID donné
    }

    /**
     * Méthode de l'interface Stockable pour ajouter un produit.
     * Cette méthode n'est pas supportée dans cette classe, car elle est dédiée à la suppression de produits.
     * 
     * @throws UnsupportedOperationException Si la méthode est appelée.
     */
    @Override
    public void addProduct() throws IOException {
        throw new UnsupportedOperationException("DeleteProduct does not support adding products.");
    }
}
