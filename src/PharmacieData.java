/**
 * Classe représentant les données d'une pharmacie dans un format compatible avec la sérialisation JSON.
 * Cette classe contient un objet de type Pharmacy qui représente les informations sur la pharmacie.
 */
public class PharmacieData {

    // L'objet Pharmacy contenant les informations détaillées de la pharmacie
    private Pharmacy pharmacie;

    /**
     * Récupère l'objet Pharmacy associé à cette instance de PharmacieData.
     * 
     * @return L'objet Pharmacy contenant les données de la pharmacie.
     */
    public Pharmacy getPharmacie() {
        return pharmacie;
    }

    /**
     * Définit l'objet Pharmacy à associer à cette instance de PharmacieData.
     * 
     * @param pharmacie L'objet Pharmacy contenant les informations à associer.
     */
    public void setPharmacie(Pharmacy pharmacie) {
        this.pharmacie = pharmacie;
    }
}
