import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Classe principale pour générer des statistiques sur les ventes de la pharmacie.
 * <p>
 * Elle charge les données des produits depuis un fichier JSON, génère des ventes fictives,
 * et exporte les résultats dans un fichier CSV.
 */
public class StatistiquesPharmacie {

    public static void main(String[] args) {
        // Fichier JSON contenant les produits en stock
        String cheminFichierJson = "stocks_pharma.json";

        // Fichier CSV où les statistiques seront exportées
        String cheminFichierCsv = "statistiques_ventes.csv";

        try {
            // 1️⃣ Charger les produits depuis le fichier JSON
            List<Product> produits = chargerProduitsDepuisJson(cheminFichierJson);

            // 2️⃣ Générer des ventes fictives
            List<Vente> ventes = genererVentesFictives(produits);

            // 3️⃣ Exporter les statistiques en CSV
            exporterStatistiques(ventes, cheminFichierCsv);

        } catch (IOException e) {
            System.out.println("Erreur lors du traitement du fichier : " + e.getMessage());
        }
    }

    /**
     * Charge la liste des produits depuis un fichier JSON.
     *
     * @param fichier Chemin du fichier JSON à lire.
     * @return Liste des produits disponibles en stock.
     */
    public static List<Product> chargerProduitsDepuisJson(String fichier) {
        List<Product> produits = new ArrayList<>();
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(fichier);

            // Conversion du fichier JSON en objet Java
            PharmacieData data = gson.fromJson(reader, PharmacieData.class);
            reader.close();

            // Vérification que la pharmacie et ses produits existent
            if (data != null && data.getPharmacie() != null) {
                List<ProductCategory> categories = data.getPharmacie().getProduits();

                // Parcours des catégories et sous-catégories pour extraire les produits
                for (int i = 0; i < categories.size(); i++) {  
                    ProductCategory cat = categories.get(i);
                    for (int j = 0; j < cat.getProduits().size(); j++) {
                        produits.add(cat.getProduits().get(j));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }
        return produits;
    }

    /**
     * Génère des ventes fictives en attribuant un nombre aléatoire d'unités vendues à chaque produit.
     *
     * @param produits Liste des produits disponibles.
     * @return Liste des ventes générées.
     */
    public static List<Vente> genererVentesFictives(List<Product> produits) {
        List<Vente> ventes = new ArrayList<>();
        Random random = new Random();

        // Parcours de tous les produits et assignation d'une quantité vendue aléatoire
        for (int i = 0; i < produits.size(); i++) {
            Product produit = produits.get(i);
            int quantiteVendue = random.nextInt(50) + 1; // Génère un nombre aléatoire entre 1 et 50
            ventes.add(new Vente(produit, quantiteVendue));

            // Affichage des informations du produit dans la console
            System.out.println(produit.getNom() + " - " + produit.getPrix());
        }

        return ventes;
    }

    /**
     * Exporte les statistiques des ventes dans un fichier CSV.
     *
     * @param ventes Liste des ventes réalisées.
     * @param cheminFichier Chemin du fichier où enregistrer les statistiques.
     * @throws IOException En cas d'erreur lors de l'écriture dans le fichier.
     */
    public static void exporterStatistiques(List<Vente> ventes, String cheminFichier) throws IOException {
        // Dictionnaire pour stocker la quantité totale vendue par produit
        Map<Product, Integer> ventesParProduit = new HashMap<>();
        double totalChiffreAffaires = 0.0;

        // Calcul du nombre total de ventes par produit
        for (int i = 0; i < ventes.size(); i++) {
            Vente vente = ventes.get(i);
            Product produit = vente.getProduit();
            int quantiteVendue = vente.getQuantiteVendue();

            // Ajout de la quantité vendue au total par produit
            ventesParProduit.put(produit, ventesParProduit.getOrDefault(produit, 0) + quantiteVendue);

            // Ajout du chiffre d'affaires généré
            totalChiffreAffaires += vente.getTotalVente();
        }

        // Tri des produits en fonction de la quantité vendue (ordre décroissant) - Bubble Sort
        List<Map.Entry<Product, Integer>> produitsTries = new ArrayList<>(ventesParProduit.entrySet());
        int n = produitsTries.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (produitsTries.get(j).getValue() < produitsTries.get(j + 1).getValue()) {
                    // Échange des éléments si nécessaire
                    Map.Entry<Product, Integer> temp = produitsTries.get(j);
                    produitsTries.set(j, produitsTries.get(j + 1));
                    produitsTries.set(j + 1, temp);
                }
            }
        }

        // Identification du produit le plus vendu
        Product produitLePlusVendu = produitsTries.get(0).getKey();
        int quantiteProduitLePlusVendu = produitsTries.get(0).getValue();

        // Écriture des statistiques dans un fichier CSV
        FileWriter writer = new FileWriter(cheminFichier);
        writer.write("Produit,Quantité Vendue,Total des Ventes (€)\n");

        // Écriture des données de ventes produit par produit
        for (int i = 0; i < produitsTries.size(); i++) {
            Product produit = produitsTries.get(i).getKey();
            int quantite = produitsTries.get(i).getValue();
            double totalVentesProduit = produit.getPrix() * quantite;

            writer.write(produit.getNom() + "," + quantite + "," + totalVentesProduit + "\n");
        }

        // Écriture du produit le plus vendu et du chiffre d'affaires total
        writer.write("\nProduit le plus vendu: " + produitLePlusVendu.getNom() + "\n");
        writer.write("Quantité vendue du produit le plus vendu: " + quantiteProduitLePlusVendu + "\n");
        writer.write("Total des ventes: " + totalChiffreAffaires + "€\n");

        writer.close();
        System.out.println("📂 Statistiques exportées vers " + cheminFichier);
    }
}
