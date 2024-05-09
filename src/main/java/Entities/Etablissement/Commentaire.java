package Entities.Etablissement;

public class Commentaire {
    private int id,utilisateur_id,etablissement_id;
    private String message;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public int getEtablissement_id() {
        return etablissement_id;
    }

    public void setEtablissement_id(int etablissement_id) {
        this.etablissement_id = etablissement_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Commentaire(int etablissement_id, String message, String note) {
        this.etablissement_id = etablissement_id;
        this.message = message;
        this.note = note;
    }

    public Commentaire(int id, int utilisateur_id, int etablissement_id, String message, String note) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.etablissement_id = etablissement_id;
        this.message = message;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", utilisateur_id=" + utilisateur_id +
                ", etablissement_id=" + etablissement_id +
                ", message='" + message + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
