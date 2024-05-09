package Entities.Produit;



public class Produit_commande {
    private int produit_id, commande_id ,qte;

    public Produit_commande(int id, int commande_id , int qte) {
        this.produit_id = id;
        this.commande_id  = commande_id ;
        this.qte = qte;
    }

    public Produit_commande() {
    }

    public int getId() {
        return produit_id;
    }

    public void setId(int id) {
        this.produit_id = id;
    }

    public int getcommande_id () {
        return commande_id ;
    }

    public void setcommande_id (int commande_id ) {
        this.commande_id  = commande_id ;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
