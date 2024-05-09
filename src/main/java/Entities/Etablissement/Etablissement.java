package Entities.Etablissement;

public class Etablissement {
    private  int id;
    private int user_id;
    private double x;
    private double y;
    private String nom,type,adresse,image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Etablissement(int user_id, float x, float y, String nom, String type, String adresse, String image) {
        this.user_id = user_id;
        this.x = x;
        this.y = y;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.image = image;
    }

    public Etablissement(int id, double x, double y, String nom, String type, String adresse) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
    }

    public Etablissement(int id, int user_id, double x, double y, String nom, String type, String adresse, String image) {
        this.id = id;
        this.user_id = user_id;
        this.x = x;
        this.y = y;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.image = image;
    }


    //ajout constr
    public Etablissement(double x, double y, String nom, String type, String adresse) {
        this.x = x;
        this.y = y;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;

    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", x=" + x +
                ", y=" + y +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", adresse='" + adresse + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
