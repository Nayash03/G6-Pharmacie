import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

/**
 * Classe permettant de visualiser les produits ayant un stock inférieur à 5.
 * Cette classe lit un fichier JSON contenant des données sur les produits, 
 * filtre ceux ayant un stock faible, les trie et les affiche dans la console.
 */
public class LowStockViewer {

    // Chemin du fichier JSON contenant les informations de la pharmacie
    private static final String FILE_PATH = "stocks_pharma.json";

    // Instance de Gson pour sérialiser et désérialiser les données JSON
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Méthode principale de la classe. Elle crée une instance de LowStockViewer
     * et appelle la méthode displayLowStockProducts pour afficher les produits
     * avec un stock inférieur à 5.
     */
    public static void main(String[] args) {
        LowStockViewer viewer = new LowStockViewer();
        viewer.displayLowStockProducts();
    }

    /**
     * Méthode pour afficher les produits ayant un stock inférieur à 5.
     * Elle filtre les produits avec un faible stock, les trie par quantité 
     * croissante et les affiche dans la console.
     */
    public void displayLowStockProducts() {
        // Lire le fichier JSON et récupérer les données de la pharmacie
        Pharmacy pharmacie = lireFichier();
        if (pharmacie == null) return;

        // Liste pour stocker les produits avec un stock faible (moins de 5 unités)
        List<Product> lowStockProducts = new ArrayList<>();

        // Parcourir toutes les catégories de produits et vérifier leur stock
        for (ProductCategory categorie : pharmacie.getProduits()) {
            for (Product produit : categorie.getProduits()) {
                // Si le stock est inférieur à 5, on l'ajoute à la liste
                if (produit.getQuantiteStock() < 5) {
                    lowStockProducts.add(produit);
                }
            }
        }

        // Si aucun produit n'a un stock faible, afficher un message
        if (lowStockProducts.isEmpty()) {
            System.out.println("Tous les produits ont un stock suffisant.");
            return;
        }

        // Trier la liste des produits en fonction de leur quantité en stock (tri à bulles)
        triABulles(lowStockProducts);

        // Afficher les produits triés dans la console
        System.out.println("Produits avec un stock inférieur à 5 (triés par quantité) :");
        for (Product produit : lowStockProducts) {
            System.out.println("ID: " + produit.getId() +
                    ", Nom: " + produit.getNom() +
                    ", Quantité: " + produit.getQuantiteStock() +
                    ", Prix: " + produit.getPrix() + "€");
        }
    }

    /**
     * Méthode pour lire le fichier JSON et récupérer les données de la pharmacie.
     * 
     * @return L'objet Pharmacy contenant toutes les informations de la pharmacie,
     *         ou null en cas d'erreur de lecture.
     */
    private Pharmacy lireFichier() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            // Désérialiser le fichier JSON en un objet PharmacyWrapper
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            // Si le fichier est correctement chargé, retourner l'objet Pharmacy
            return wrapper != null ? wrapper.getPharmacie() : null;
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    /**
     * Méthode de tri à bulles pour trier la liste des produits en fonction de la quantité en stock.
     * 
     * @param produits La liste des produits à trier.
     */
    private void triABulles(List<Product> produits) {
        int n = produits.size();
        boolean echangeEffectue;

        // Parcours de la liste pour trier les produits
        for (int i = 0; i < n - 1; i++) {
            echangeEffectue = false;

            for (int j = 0; j < n - i - 1; j++) {
                // Comparaison des quantités des produits consécutifs
                if (produits.get(j).getQuantiteStock() > produits.get(j + 1).getQuantiteStock()) {
                    // Échange des produits si la quantité du produit j est supérieure à celle du produit j + 1
                    Product temp = produits.get(j);
                    produits.set(j, produits.get(j + 1));
                    produits.set(j + 1, temp);
                    echangeEffectue = true;
                }
            }

            // Si aucun échange n'a été effectué, la liste est déjà triée
            if (!echangeEffectue) break;
        }
    }
}
