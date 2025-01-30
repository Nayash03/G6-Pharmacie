import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class AddProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        AddProduct addProduct = new AddProduct();
        try {
            addProduct.addProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Saisie des informations
        System.out.print("Nom du produit : ");
        String nom = scanner.nextLine();

        System.out.print("Catégorie (format: Catégorie:SousCatégorie) : ");
        String[] categories = scanner.nextLine().split(":");
        if (categories.length != 2) {
            System.out.println("Format invalide. Utilisez Catégorie:SousCatégorie.");
            return;
        }
        String categorie = categories[0];
        String sousCategorie = categories[1];

        System.out.print("Prix : ");
        double prix = scanner.nextDouble();
        if (prix <= 0) {
            System.out.println("Le prix doit être positif.");
            return;
        }

        System.out.print("Quantité : ");
        int quantite = scanner.nextInt();
        if (quantite <= 0) {
            System.out.println("La quantité doit être positive.");
            return;
        }

        // Lecture du fichier JSON
        Pharmacy pharmacie = FileHelper.lireFichier(FILE_PATH);
        if (pharmacie == null) return;

        // Création du produit
        Product produit = new Product();
        produit.setId(genererNouvelId(pharmacie));
        produit.setNom(nom);
        produit.setPrix(prix);
        produit.setQuantiteStock(quantite);
        produit.setDescription(""); // Description non demandée

        // Ajout du produit
        ajouterProduit(pharmacie, produit, categorie, sousCategorie);

        // Écriture dans le fichier
        ecrireFichier(pharmacie);
        System.out.println("Produit ajouté avec succès !");
    }

    public static void ecrireFichier(Pharmacy pharmacie) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            PharmacyWrapper wrapper = new PharmacyWrapper();
            wrapper.setPharmacie(pharmacie);
            gson.toJson(wrapper, writer);
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier.");
        }
    }

    private int genererNouvelId(Pharmacy pharmacie) {
        return pharmacie.getProduits().stream()
                .flatMap(pc -> pc.getProduits().stream())
                .mapToInt(Product::getId)
                .max().orElse(0) + 1;
    }

    private void ajouterProduit(Pharmacy pharmacie, Product produit, String categorie, String sousCategorie) {
        Optional<ProductCategory> categorieExistante = pharmacie.getProduits().stream()
                .filter(pc -> pc.getCategorie().equals(categorie) && pc.getSousCategorie().equals(sousCategorie))
                .findFirst();

        if (categorieExistante.isPresent()) {
            categorieExistante.get().getProduits().add(produit);
        } else {
            ProductCategory nouvelleCategorie = new ProductCategory();
            nouvelleCategorie.setCategorie(categorie);
            nouvelleCategorie.setSousCategorie(sousCategorie);
            nouvelleCategorie.setProduits(new ArrayList<>(List.of(produit)));
            pharmacie.getProduits().add(nouvelleCategorie);
        }
    }
}