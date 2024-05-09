package Entities.Reclamation;

import java.util.Date;

public class Reponse {
    private int id;
    private String contenu;
    private Date date_creation;

    public Reponse(String contenu) {
        this.id = id;
        this.contenu = contenu;
        this.date_creation = date_creation;
    }

    public Reponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }
}
