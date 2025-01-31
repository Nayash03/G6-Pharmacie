public class Vente {
    private Product produit;
    private int quantiteVendue;

    public Vente(Product produit, int quantiteVendue) {
        this.produit = produit;
        this.quantiteVendue = quantiteVendue;
    }

    public Product getProduit() { return produit; }
    public int getQuantiteVendue() { return quantiteVendue; }
    public double getTotalVente() { return produit.getPrix() * quantiteVendue; }
}
