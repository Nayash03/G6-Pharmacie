import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private String categorie;
    private String sousCategorie;
    private List<Product> produits = new ArrayList<>();

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getSousCategorie() { return sousCategorie; }
    public void setSousCategorie(String sousCategorie) { this.sousCategorie = sousCategorie; }
    public List<Product> getProduits() { return produits; }
    public void setProduits(List<Product> produits) { this.produits = produits; }
}