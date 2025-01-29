import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            // Charger le fichier JSON
            Gson gson = new Gson();
            FileReader reader = new FileReader("pharmacie.json"); // Remplacez par le chemin de votre fichier JSON

            // Convertir le JSON en objet Pharmacie
            Pharmacie pharmacie = gson.fromJson(reader, Pharmacie.class);
            reader.close();

            // Afficher les produits triés par nom pour chaque catégorie
            for (Categorie categorie : pharmacie.produits) {
                System.out.println("Catégorie: " + categorie.categorie + " - " + categorie.sousCategorie);

                // Trier les produits par nom
                Collections.sort(categorie.produits, Comparator.comparing(Produit::getNom));

                for (Produit produit : categorie.produits) {
                    System.out.println("Produit: " + produit.nom + ", Prix: " + produit.prix + "€, Quantité en stock: " + produit.quantiteStock);
                }

                System.out.println("--------------------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class Pharmacie {
    String nom;
    String adresse;
    List<Categorie> produits;
}

class Categorie {
    String categorie;
    String sousCategorie;
    List<Produit> produits;
}

class Produit {
    int id;
    String nom;
    double prix;
    int quantiteStock;
    String description;
}
