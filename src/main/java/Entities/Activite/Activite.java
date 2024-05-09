package Entities.Activite;

import java.util.Date;

public class Activite {

    private int id ;

    private String nom,description,image;


    private Date date_debut , date_fin;
    private Integer id_categorie;



    public Activite(String nom, String description, Date date_debut, Date date_fin, Integer id_categorie,String image) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_categorie = id_categorie;
        this.image = image;
    }

    public Activite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Integer getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(Integer id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", id_categorie=" + id_categorie +
                '}';
    }

    public Activite(int id, String nom, String description, Date date_debut, Date date_fin, Integer id_categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_categorie = id_categorie;



    }
}
