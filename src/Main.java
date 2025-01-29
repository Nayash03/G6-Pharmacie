import java.io.Serializable;
import java.util.*;

interface Stockable {}

abstract class Produit implements Stockable, Comparable<Produit>, Serializable {
    protected String nom;
    protected double prix;
    protected int quantite;
    protected Categorie categorie;

    public Produit(String nom, double prix, int quantite, Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public int compareTo(Produit autre) {
        return this.nom.compareToIgnoreCase(autre.nom);
    }

    public abstract void afficherInfos();
}

class Categorie {
    private String nom;

    public Categorie(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}

class Medicament extends Produit {
    public Medicament(String nom, double prix, int quantite, Categorie categorie) {
        super(nom, prix, quantite, categorie);
    }

    @Override
    public void afficherInfos() {
        System.out.println(nom + " - " + categorie.getNom() + " - " + prix + "€ - Stock: " + quantite);
    }
}

abstract class Utilisateur {
    protected String nom;

    public Utilisateur(String nom) {
        this.nom = nom;
    }

    public abstract void consulterStock(Stock stock);
}

class Pharmacien extends Utilisateur {
    public Pharmacien(String nom) {
        super(nom);
    }

    @Override
    public void consulterStock(Stock stock) {
        stock.afficherProduits();
    }
}

class Stock {
    private List<Produit> produits = new ArrayList<>();

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public void afficherProduits() {
        Collections.sort(produits);
        for (Produit p : produits) {
            p.afficherInfos();
        }
    }

    public Produit rechercherProduit(String nom) {
        int index = Collections.binarySearch(produits, new Medicament(nom, 0, 0, new Categorie("Temp")));
        return index >= 0 ? produits.get(index) : null;
    }
}

public class Main {
    public static void main(String[] args) {
        Categorie medicament = new Categorie("Médicament");
        Stock stock = new Stock();
        stock.ajouterProduit(new Medicament("Paracétamol", 3.99, 50, medicament));
        stock.ajouterProduit(new Medicament("Ibuprofène", 4.99, 30, medicament));
        Pharmacien pharmacien = new Pharmacien("Dr. Dupont");
        pharmacien.consulterStock(stock);
        Produit produitTrouve = stock.rechercherProduit("Ibuprofène");
        if (produitTrouve != null) produitTrouve.afficherInfos();
    }
}
