import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    private String nom;
    private String adresse;
    private List<ProductCategory> produits = new ArrayList<>();

    public List<ProductCategory> getProduits() {
        return produits;
    }

    public void setProduits(List<ProductCategory> produits) {
        this.produits = produits;
    }
}