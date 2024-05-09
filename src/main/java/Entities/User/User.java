/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.User;


public class User {
    //attributs
    private int id;
    private int reponse_id;
    private String cin;
    private String nom;
    private String prenom;
    private String mdp;
    private String email;
    private String adresse;
    private int numtel;
    private String image;
    private String role;
    private String google_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReponse_id() {
        return reponse_id;
    }

    public void setReponse_id(int reponse_id) {
        this.reponse_id = reponse_id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public User(String cin, String nom, String prenom, String mdp, String email, String adresse, int numtel, String role) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.email = email;
        this.adresse = adresse;
        this.numtel = numtel;
        this.role = role;
    }

    public User(int id, String cin, String nom, String prenom, String mdp, String email, String adresse, int numtel, String role) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.email = email;
        this.adresse = adresse;
        this.numtel = numtel;
        this.role = role;
    }

    public User(){

    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", mdp=" + mdp + ", email=" + email + ", adresse=" + adresse + ", numtel=" + numtel + ", role=" + role + '}';
    }
    
    
    
}
