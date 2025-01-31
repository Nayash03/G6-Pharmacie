import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pharmacy with a name, address, and a list of product categories.
 */
public class Pharmacy {
    private String nom;
    private String adresse;
    private List<ProductCategory> produits = new ArrayList<>();

    /**
     * Gets the list of product categories in the pharmacy.
     *
     * @return the list of product categories
     */
    public List<ProductCategory> getProduits() {
        return produits;
    }

    /**
     * Sets the list of product categories in the pharmacy.
     *
     * @param produits the new list of product categories
     */
    public void setProduits(List<ProductCategory> produits) {
        this.produits = produits;
    }
}