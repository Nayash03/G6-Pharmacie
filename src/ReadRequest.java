import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadRequest {

    private static final String FILE_PATH = "commands.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        List<Map<String, Object>> commandes = lireCommandes();

        if (commandes != null) {
            // Trier les commandes par date décroissante
            commandes.sort((c1, c2) -> {
                String dateStr1 = (String) c1.get("date");
                String dateStr2 = (String) c2.get("date");

                return parseDate(dateStr2).compareTo(parseDate(dateStr1));
            });

            afficherCommandes(commandes);
        } else {
            System.out.println("Erreur lors de la lecture des commandes.");
        }
    }

    public static List<Map<String, Object>> lireCommandes() {

        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<List<Map<String, Object>>>() {}.getType());
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier.");
            return null;
        }
    }

    public static void afficherCommandes(List<Map<String, Object>> commandes) {

        System.out.println("Historique des commandes (" + commandes.size() + " commandes) :\n");
        for (Map<String, Object> commande : commandes) {
            System.out.println("Date : " + commande.get("date"));
            System.out.println("Type de livraison : " + commande.get("livraison"));
            System.out.println("Produits :");

            List<Map<String, Object>> produits = (List<Map<String, Object>>) commande.get("produits");
            for (Map<String, Object> produit : produits) {
                System.out.println("   - " + produit.get("nom") + " (Quantité: " + produit.get("quantite") + ")");
            }
            System.out.println("------------------------------------");
        }
    }

    private static Date parseDate(String dateStr) {

        try {
            return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(dateStr);
        } catch (Exception e) {
            return new Date(0);
        }
    }
}
