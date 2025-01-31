import java.util.List;

/**
 * Classe représentant une catégorie de produits dans la pharmacie.
 * Une catégorie peut avoir une sous-catégorie et contenir une liste de produits.
 */
class CategorieProduit {
    private String categorie;      // Nom de la catégorie (ex: Médicaments, Cosmétiques, etc.)
    private String sousCategorie;  // Nom de la sous-catégorie (ex: Antibiotiques, Soins de la peau, etc.)
    private List<Product> produits; // Liste des produits appartenant à cette catégorie

    /**
     * Obtient la liste des produits appartenant à cette catégorie.
     * 
     * @return Liste des produits.
     */
    public List<Product> getProduits() {
        return produits;
    }

    /**
     * Définit la liste des produits pour cette catégorie.
     * 
     * @param produits Liste des produits à associer à la catégorie.
     */
    public void setProduits(List<Product> produits) {
        this.produits = produits;
    }
}
