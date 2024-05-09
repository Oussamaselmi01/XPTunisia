package Services.ActiviteService;


import Entities.Activite.Categorie;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements IService<Categorie> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void addA(Categorie categorie) {

        String req = "INSERT INTO `categorie`(`nom`, `description`) VALUES ('"+categorie.getNom()+"','"+categorie.getDescription()+"');";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie added");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateA(Categorie categorie) {
        String req = "UPDATE categorie SET nom = ?, description = ? WHERE id =" + categorie.getId() + ";";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, categorie.getNom());
            pstmt.setString(2, categorie.getDescription());
            pstmt.executeUpdate();
            System.out.println("Categorie updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteA(Integer id) {
        String req = "DELETE FROM categorie WHERE id = " + id +";";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);

            pstmt.executeUpdate();
            System.out.println("Categorie deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Categorie> getAll() {
        List<Categorie> categories = new ArrayList<>();

        String req = "SELECT * FROM categorie";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setNom(rs.getString("nom"));
                categorie.setDescription(rs.getString("description"));

                categories.add(categorie);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return categories;
    }

    @Override
    public Categorie getOne(int id) {
        Categorie categorie = null;
        String req = "SELECT * FROM categorie WHERE id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                categorie = new Categorie();
                categorie.setNom(rs.getString("nom"));
                categorie.setDescription(rs.getString("description"));


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorie;
    }
}
