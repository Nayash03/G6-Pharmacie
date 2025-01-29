import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;

public class RequestProduct implements Stockable {
    private static final String FILE_PATH = "stocks_pharma.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {

        Map<String, Integer> commandeClient = new HashMap<>();
        commandeClient.put("Paracétamol", 30);
        commandeClient.put("Ibuprofène", 30);
        enregistrerCommande(commandeClient);
    }

    @Override
    public void addProduct() throws IOException {
        // TODO: Add implementation for adding a product
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public static boolean enregistrerCommande(Map<String, Integer> produitsDemandes) {
            // Charger les stocks depuis le fichier
            Pharmacy pharmacie = FileHelper.lireFichier(FILE_PATH);
            if (pharmacie == null) {
                System.out.println("Erreur lors de la lecture des stocks.");
                return false;
            }
            Map<Product, Integer> produitsValidés = new HashMap<>();

            for (Map.Entry<String, Integer> entry : produitsDemandes.entrySet()) {
                String nomProduit = entry.getKey();
                int quantiteDemandée = entry.getValue();

                Product produitTrouvé = pharmacie.getProduits().stream()
                        .flatMap(pc -> pc.getProduits().stream())
                        .filter(p -> p.getNom().equalsIgnoreCase(nomProduit))
                        .findFirst()
                        .orElse(null);

                if (produitTrouvé == null || produitTrouvé.getQuantiteStock() < quantiteDemandée) {
                    System.out.println("Stock insuffisant ou produit introuvable : " + nomProduit);
                    return false;
                }

                produitsValidés.put(produitTrouvé, quantiteDemandée);
            }


            for (Map.Entry<Product, Integer> entry : produitsValidés.entrySet()) {
                entry.getKey().setQuantiteStock(entry.getKey().getQuantiteStock() - entry.getValue());
            }

            ecrirFichier(pharmacie);
            ecrireCommandeFichier(produitsValidés);
            System.out.println("Commande enregistrée avec succès !");
            return true;
        }

    public static void ecrirFichier(Pharmacy pharmacie) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            PharmacyWrapper wrapper = new PharmacyWrapper();
            wrapper.setPharmacie(pharmacie);
            gson.toJson(wrapper, writer);
        } catch (IOException e) {
            System.out.println("Erreur d'écriture dans le fichier.");
        }
    }

    private static void ecrireCommandeFichier(Map<Product, Integer> produitsCommandes) {
        List<Map<String, Object>> commandes = new ArrayList<>();

        try (Reader reader = new FileReader("commands.json")) {
            commandes = gson.fromJson(reader, List.class);
            if (commandes == null) {
                commandes = new ArrayList<>();
            }
        } catch (IOException e) {}

        Map<String, Object> nouvelleCommande = new HashMap<>();
        nouvelleCommande.put("date", new Date().toString());

        List<Map<String, Object>> produits = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : produitsCommandes.entrySet()) {
            Map<String, Object> produitInfo = new HashMap<>();
            produitInfo.put("nom", entry.getKey().getNom());
            produitInfo.put("quantite", entry.getValue());
            produits.add(produitInfo);
        }
        nouvelleCommande.put("produits", produits);

        commandes.add(nouvelleCommande);

        try (Writer writer = new FileWriter("commands.json")) {
            gson.toJson(commandes, writer);
        } catch (IOException e) {}
    }


}