package Services.EtablissementService;

import Entities.Etablissement.Etablissement;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtablissementService implements IService<Etablissement> {
    private Connection conn;
    public EtablissementService()
    {
        conn = DataSource.getInstance().getCnx();
    }
    @Override
    public void insert(Etablissement e) {
        String requete = "INSERT INTO etablissement (x, y, nom, type, adresse,user_id) VALUES (?, ?, ?, ?, ?,?)";

        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(requete);
            pst.setDouble(1, e.getX());
            pst.setDouble(2, e.getY());
            pst.setString(3, e.getNom());
            pst.setString(4, e.getType());
            pst.setString(5, e.getAdresse());
            pst.setInt(6, e.getUser_id());

            pst.executeUpdate();
            System.out.print("Établissement ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(EtablissementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @Override
    public void update(Etablissement e) {
        String requete = "UPDATE etablissement SET x=?, y=?, nom=?, type=?, adresse=? WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setDouble(1, e.getX());
            pst.setDouble(2, e.getY());
            pst.setString(3, e.getNom());
            pst.setString(4, e.getType());
            pst.setString(5, e.getAdresse());
            pst.setInt(6, e.getId()); // Utilisation de l'ID pour identifier l'enregistrement à mettre à jour

            pst.executeUpdate();
            System.out.println("Établissement mis à jour avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(EtablissementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void delete(int id) {
        String requete="DELETE FROM etablissement WHERE id ="+id;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(requete);
            System.out.println("Établissement supprimé avec succées ");
        } catch (SQLException ex) {
            Logger.getLogger(EtablissementService.class.getName()).log(Level.SEVERE, null, ex);
        }}

    @Override

    public Etablissement readById(int id) {
        String requete = "SELECT * FROM etablissement WHERE id=?";
        Etablissement e = null;
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                e = new Etablissement(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getString("adresse"),
                        rs.getString("image")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtablissementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }


    @Override
    public ArrayList<Etablissement> readAll() {
        String requete = "SELECT * FROM etablissement";
        ArrayList<Etablissement> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Etablissement e = new Etablissement(
                        rs.getInt("id"),
                        rs.getInt("utilisateur_id"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getString("adresse"),
                        rs.getString("image")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EtablissementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
