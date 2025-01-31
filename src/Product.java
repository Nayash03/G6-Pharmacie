public class Product {
    private int id;
    private String nom;
    private double prix;
    private int quantiteStock;
    private String description;

    public Product(int getId, String getNom, double getPrix, int getQuantiteStock, String getDescription) {
        
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

 
}