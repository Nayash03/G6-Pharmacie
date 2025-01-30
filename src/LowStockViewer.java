import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

public class LowStockViewer {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        LowStockViewer viewer = new LowStockViewer();
        viewer.displayLowStockProducts();
    }

    // Méthode pour afficher les produits avec un stock inférieur à 
    public void displayLowStockProducts() {
        Pharmacy pharmacie = lireFichier();
        if (pharmacie == null) return;

        // Liste pour stocker les produits en faible quantité
        List<Product> lowStockProducts = new ArrayList<>();

        // Parcourir les catégories et récupérer les produits avec une quantité < 5
        for (ProductCategory categorie : pharmacie.getProduits()) {
            for (Product produit : categorie.getProduits()) {
                if (produit.getQuantiteStock() < 5) {
                    lowStockProducts.add(produit);
                }
            }
        }

        // Vérifier s'il y a des produits en faible stock
        if (lowStockProducts.isEmpty()) {
            System.out.println("Tous les produits ont un stock suffisant.");
            return;
        }

        // Trier la liste par quantité en utilisant le tri à bulles
        triABulles(lowStockProducts);

        // Affichage des produits triés
        System.out.println("Produits avec un stock inférieur à 5 (triés par quantité) :");
        for (Product produit : lowStockProducts) {
            System.out.println("ID: " + produit.getId() +
                    ", Nom: " + produit.getNom() +
                    ", Quantité: " + produit.getQuantiteStock() +
                    ", Prix: " + produit.getPrix() + "€");
        }
    }

    // Méthode pour lire le fichier JSON et récupérer les données sous forme d'objet Pharmacy
    private Pharmacy lireFichier() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            return wrapper != null ? wrapper.getPharmacie() : null;
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    // Implémentation du tri à bulles pour trier la liste des produits en fonction de la quantité
    private void triABulles(List<Product> produits) {
        int n = produits.size();
        boolean echangeEffectue;
        
        for (int i = 0; i < n - 1; i++) {
            echangeEffectue = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (produits.get(j).getQuantiteStock() > produits.get(j + 1).getQuantiteStock()) {
                    // Échange des éléments
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