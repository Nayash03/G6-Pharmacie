/**
 * Represents a product in the pharmacy.
 */
public class Product {
    private int id;
    private String nom;
    private double prix;
    private int quantiteStock;
    private String description;

    /**
     * Gets the product ID.
     * @return the product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product ID.
     * @param id the product ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     * @return the product name.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the product name.
     * @param nom the product name.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets the product price.
     * @return the product price.
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Sets the product price.
     * @param prix the product price.
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * Gets the quantity of the product in stock.
     * @return the quantity of the product in stock.
     */
    public int getQuantiteStock() {
        return quantiteStock;
    }

    /**
     * Sets the quantity of the product in stock.
     * @param quantiteStock the quantity of the product in stock.
     */
    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    /**
     * Gets the product description.
     * @return the product description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     * @param description the product description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}