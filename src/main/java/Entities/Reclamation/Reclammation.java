package Entities.Reclamation;

import Entities.User.User;

import java.util.Date;

public class Reclammation {
    private int id;
    private String 	titre;
    private String description;
    private String statut;
    private Date date_creation;
    private User utulisateur;
    private Reponse reponse;

    public Reclammation() {
    }

    public Reclammation(int id, String titre, String description, String statut, Date date_creation, User utulisateur, Reponse reponse) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.date_creation = date_creation;
        this.utulisateur = utulisateur;
        this.reponse = reponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public User getUtulisateur() {
        return utulisateur;
    }

    public void setUtulisateur(User utulisateur) {
        this.utulisateur = utulisateur;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }
}
