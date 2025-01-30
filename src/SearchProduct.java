import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

public class SearchProduct {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Classe pour mapper la structure racine du JSON
    public static class PharmacyWrapper {
        private Pharmacy pharmacie;

        public Pharmacy getPharmacie() {
            return pharmacie;
        }

        public void setPharmacie(Pharmacy pharmacie) {
            this.pharmacie = pharmacie;
        }
    }

    public static void main(String[] args) {
        try (Reader reader = new FileReader(FILE_PATH)) {
            // Désérialiser le wrapper
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            Pharmacy pharmacie = wrapper.getPharmacie();

            // Vérifier si la pharmacie est chargée
            if (pharmacie == null) {
                System.out.println("Erreur: Structure JSON invalide.");
                return;
            }

            // Extraire tous les produits
            List<Product> allProducts = new ArrayList<>();
            for (ProductCategory category : pharmacie.getProduits()) {
                allProducts.addAll(category.getProduits());
            }

            // Trier en minuscules (avec Locale.ROOT)
            allProducts.sort(Comparator.comparing(p -> p.getNom().toLowerCase(Locale.ROOT))
            );

            // Saisie utilisateur
            Scanner scanner = new Scanner(System.in, "UTF-8");
            System.out.print("Entrez le nom du produit à rechercher : ");
            String searchName = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            // Recherche binaire
            Product found = binarySearch(allProducts, searchName);
            if (found != null) {
                System.out.println("Le produit " + found.getNom() + " est disponible en quantité: " + found.getQuantiteStock());
            } else {
                System.out.println("Le produit n'existe pas.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Product binarySearch(List<Product> products, String target) {
        int left = 0, right = products.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            String midName = products.get(mid).getNom().toLowerCase(Locale.ROOT);
            int cmp = midName.compareTo(target);
            if (cmp == 0) {
                return products.get(mid);
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}