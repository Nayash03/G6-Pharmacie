import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

/**
 * The AddProduct class provides functionality to add a new product to the pharmacy stock.
 */
public class AddProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Initializes the AddProduct process by creating an instance and calling the addProduct method.
     */
    public static void init() {
        AddProduct addProduct = new AddProduct();
        try {
            addProduct.addProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user for product details and adds the product to the pharmacy stock.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void addProduct() throws IOException {
        Scanner scanner = new Scanner(System.in);

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

        System.out.print("Description : ");
        String description = scanner.next();

        Pharmacy pharmacie = FileHelper.lireFichier(FILE_PATH);
        if (pharmacie == null) return;

        Product produit = new Product();
        produit.setId(genererNouvelId(pharmacie));
        produit.setNom(nom);
        produit.setPrix(prix);
        produit.setQuantiteStock(quantite);
        produit.setDescription(description);

        ajouterProduit(pharmacie, produit, categorie, sousCategorie);

        ecrireFichier(pharmacie);
        System.out.println("Produit ajouté avec succès !");
    }

    /**
     * Writes the updated pharmacy stock to the JSON file.
     * @param pharmacie the Pharmacy object containing the updated stock.
     */
    public static void ecrireFichier(Pharmacy pharmacie) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            PharmacyWrapper wrapper = new PharmacyWrapper();
            wrapper.setPharmacie(pharmacie);
            gson.toJson(wrapper, writer);
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier.");
        }
    }

    /**
     * Generates a new unique ID for the product.
     * @param pharmacie the Pharmacy object containing the current stock.
     * @return the new unique product ID.
     */
    private int genererNouvelId(Pharmacy pharmacie) {
        return pharmacie.getProduits().stream()
                .flatMap(pc -> pc.getProduits().stream())
                .mapToInt(Product::getId)
                .max().orElse(0) + 1;
    }

    /**
     * Adds the product to the specified category and subcategory in the pharmacy stock.
     * @param pharmacie the Pharmacy object containing the current stock.
     * @param produit the Product object to be added.
     * @param categorie the category of the product.
     * @param sousCategorie the subcategory of the product.
     */
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