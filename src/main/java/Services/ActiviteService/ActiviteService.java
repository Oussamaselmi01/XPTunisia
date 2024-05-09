package Services.ActiviteService;

import Entities.Activite.Activite;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteService implements IService<Activite> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void addA(Activite activite) {

        System.out.println(activite);
        String req = "INSERT INTO activite(nom, description , date_debut, date_fin, categorie_id,image) VALUES ('"+activite.getNom()+"','"+activite.getDescription()+"','"+activite.getDate_debut()+"','"+activite.getDate_fin()+"','"+activite.getId_categorie()+"','"+activite.getImage()+"');";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Activite ajouté");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateA(Activite activite) {

        System.out.println(activite);
        String req = "UPDATE activite SET nom = ?, description = ?,date_debut = ?, date_fin = ? ,categorie_id = ? WHERE id =" + activite.getId() + ";";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, activite.getNom());
            pstmt.setString(2, activite.getDescription());

            java.util.Date utilDateDebut = activite.getDate_debut();
            java.util.Date utilDateFin = activite.getDate_fin();

            Date sqlDateDebut = new Date(utilDateDebut.getTime());
            Date sqlDateFin = new Date(utilDateFin.getTime());

            pstmt.setDate(3, sqlDateDebut);
            pstmt.setDate(4, sqlDateFin);
            pstmt.setInt(5, activite.getId_categorie());

            pstmt.executeUpdate();
            System.out.println("Activite updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteA(Integer id) {
        String req = "DELETE FROM activite WHERE id = " +id +";";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.executeUpdate();
            System.out.println("Activite deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Activite> getAll() {
        List<Activite> activites = new ArrayList<>();

        String req = "SELECT * FROM activite";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Activite activite = new Activite();
                activite.setId(rs.getInt("id"));
                activite.setNom(rs.getString("nom"));
                activite.setDescription(rs.getString("description"));
                activite.setDate_debut(rs.getDate("date_debut"));
                activite.setDate_fin(rs.getDate("date_fin"));
                activite.setId_categorie(rs.getInt("categorie_id"));
                activite.setImage(rs.getString("image"));
                activites.add(activite);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return activites;
    }

    @Override
    public Activite getOne(int id) {
        Activite activite =null;
        String req = "SELECT * FROM activite WHERE id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                activite = new Activite();
                activite.setNom(rs.getString("nom"));
                activite.setDescription(rs.getString("description"));
                activite.setDate_fin(rs.getDate("date_fin"));
                activite.setDate_debut(rs.getDate("date_debut"));
                activite.setId_categorie(rs.getInt("categorie_id"));
                activite.setImage(rs.getString("image"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return activite;
    }
}
