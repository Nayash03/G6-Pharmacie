import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une pharmacie, contenant des informations sur le nom, l'adresse 
 * et la liste des catégories de produits disponibles.
 */
public class Pharmacy {

    // Nom de la pharmacie
    private String nom;

    // Adresse de la pharmacie
    private String adresse;

    // Liste des catégories de produits disponibles dans la pharmacie
    private List<ProductCategory> produits = new ArrayList<>();

    /**
     * Obtient la liste des catégories de produits disponibles dans la pharmacie.
     * 
     * @return La liste des catégories de produits.
     */
    public List<ProductCategory> getProduits() {
        return produits;
    }

    /**
     * Définit la liste des catégories de produits pour la pharmacie.
     *
     * @param produits Liste des catégories de produits à assigner.
     */
    public void setProduits(List<ProductCategory> produits) {
        this.produits = produits;
    }

    /**
     * Obtient le nom de la pharmacie.
     *
     * @return Le nom de la pharmacie.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de la pharmacie.
     *
     * @param nom Le nom de la pharmacie à assigner.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'adresse de la pharmacie.
     *
     * @return L'adresse de la pharmacie.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse de la pharmacie.
     *
     * @param adresse L'adresse de la pharmacie à assigner.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
