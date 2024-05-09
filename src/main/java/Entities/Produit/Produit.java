package Entities.Produit;

public class Produit {
    private int id, utilisateur_id;
    private String nom, description, image ;
    private double prix;
    public Produit()
    {}

    public Produit( String nom, String description, String image, double prix) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public Produit(int id,int utilisateur_id, String nom, String description, String image, double prix) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "utilisateur_id=" + utilisateur_id +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                '}';
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }
}