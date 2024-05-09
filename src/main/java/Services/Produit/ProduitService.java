package Services.Produit;

import Entities.Produit.Produit;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProduitService implements Iservice<Produit> {

    private Connection con;

    //private Statement ste;
    public ProduitService() {
        con= DataSource.getInstance().getCnx();
    }

    public void add (Produit p)
    {
        String requete= "insert into produit (nom, description, prix, image,utilisateur_id) values (?,?,?,?,1)";
        try {
            PreparedStatement pst = con.prepareStatement(requete);

            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setDouble(3, p.getPrix());
            pst.setString(4, p.getImage());


            pst.executeUpdate();
            System.out.println("Produit ajouté avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Produit p)
    {
        String requete="DELETE FROM produit WHERE id=?";
        try {
            PreparedStatement pst= con.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Produit supprimé avec succes");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            System.out.println("Produit non supprimé!");
        }
    }

    @Override
    public void update(Produit p) {
        String req = "UPDATE `produit` SET nom=?, description=?, prix=?, image=?, utilisateur_id=? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setString(1, p.getNom());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrix());
            ps.setString(4, p.getImage());
            ps.setInt(5, p.getUtilisateur_id()); // Utilisez l'index 5 pour le paramètre utilisateur_id
            ps.setInt(6, p.getId()); // Utilisez l'index 6 pour le paramètre id

            ps.executeUpdate();
            System.out.println("Produit modifié avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }
    }

    @Override
    public List<Produit> readall() {
        String requete = "select * from produit";
        List<Produit> list = new ArrayList<>();
        try {
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Produit p = new Produit(rs.getInt("id"), rs.getInt("utilisateur_id"), rs.getString("nom"), rs.getString("description"), rs.getString("image"), rs.getDouble("prix"));
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.toString());
        return list;
    }

    @Override
    public Produit readById(int id) {
        Produit p = new Produit();
        String req = "SELECT * FROM produit WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                p.setId(rst.getInt("id"));
                p.setUtilisateur_id(rst.getInt("utilisateur_id"));
                p.setNom(rst.getString("nom"));
                p.setDescription(rst.getString("description"));
                p.setPrix(rst.getDouble("prix"));
                p.setImage(rst.getString("image"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}