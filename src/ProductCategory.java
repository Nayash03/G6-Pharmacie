import java.util.ArrayList;
import java.util.List;

/**
 * Represents a category of products, including a main category and a subcategory.
 */
public class ProductCategory {
    private String categorie;
    private String sousCategorie;
    private List<Product> produits = new ArrayList<>();

    /**
     * Gets the main category.
     * 
     * @return the main category
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Sets the main category.
     * 
     * @param categorie the main category to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Gets the subcategory.
     * 
     * @return the subcategory
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * Sets the subcategory.
     * 
     * @param sousCategorie the subcategory to set
     */
    public void setSousCategorie(String sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    /**
     * Gets the list of products in this category.
     * 
     * @return the list of products
     */
    public List<Product> getProduits() {
        return produits;
    }

    /**
     * Sets the list of products in this category.
     * 
     * @param produits the list of products to set
     */
    public void setProduits(List<Product> produits) {
        this.produits = produits;
    }
}