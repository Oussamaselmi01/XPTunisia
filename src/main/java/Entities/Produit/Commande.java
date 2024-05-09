package Entities.Produit;

import java.sql.Date;

public class Commande {
    private int id, utilisateur_id;
    private String mode_paiement, adresse_livraison, phone_number, client_name ;
    private double total;
    private Date date_livraison;

    public Commande() {
    }

    public Commande(int id, int utilisateur_id, String mode_paiement, String adresse_livraison, String phone_number, String client_name, double total, Date date_livraison) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.mode_paiement = mode_paiement;
        this.adresse_livraison = adresse_livraison;
        this.phone_number = phone_number;
        this.client_name = client_name;
        this.total = total;
        this.date_livraison = date_livraison;
    }

    public Commande(int id, int utilisateur_id, String mode_paiement, String adresse_livraison, String phone_number, String client_name, double total) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.mode_paiement = mode_paiement;
        this.adresse_livraison = adresse_livraison;
        this.phone_number = phone_number;
        this.client_name = client_name;
        this.total = total;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }
    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public Commande(String mode_paiement, String adresse_livraison, String phone_number, String client_name, double total, Date date_livraison) {
        this.mode_paiement = mode_paiement;
        this.adresse_livraison = adresse_livraison;
        this.phone_number = phone_number;
        this.client_name = client_name;
        this.total = total;
        this.date_livraison = date_livraison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public String getAdresse_livraison() {
        return adresse_livraison;
    }

    public void setAdresse_livraison(String adresse_livraison) {
        this.adresse_livraison = adresse_livraison;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate_livraison() {
        return date_livraison;
    }

    public void setDate_livraison(Date date_livraison) {
        this.date_livraison = date_livraison;
    }
}
