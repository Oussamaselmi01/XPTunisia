package Services.Produit;

import Entities.Produit.Produit_commande;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements Iservice<Produit_commande> {
    private Connection con;
    public PanierService() {
        con= DataSource.getInstance().getCnx();
    }


    @Override
    public void add(Produit_commande PC) {
        String requete= "insert into panier (Id,commande_id,qte,) values (?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(requete);

            pst.setInt(1, PC.getId());
            pst.setInt(2, PC.getcommande_id());
            pst.setFloat(3, PC.getQte());


            pst.executeUpdate();
            System.out.println("Produit ajouté au panier avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Produit_commande PC) {
        String requete="DELETE FROM panier WHERE produit_id=?";
        try {
            PreparedStatement pst= con.prepareStatement(requete);
            pst.setInt(1, PC.getId());
            pst.executeUpdate();
            System.out.println("Produit supprimé avec succes");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            System.out.println("Produit non supprimé!");
        }

    }

    @Override
    public void update(Produit_commande PC) {
        String req = "UPDATE `panier` SET produit_id=?, qte=? WHERE commande_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setInt(1, PC.getId());
            ps.setInt(2, PC.getQte());
            ps.setInt(3, PC.getcommande_id());


            ps.executeUpdate();
            System.out.println("Panier modifié avec succes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }

    }

    @Override
    public List<Produit_commande> readall() {
        String requete = "select * from panier";
        List<Produit_commande> list = new ArrayList<>();
        try {
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Produit_commande pa = new Produit_commande(rs.getInt("commande_id"), rs.getInt("produit_id"), rs.getInt("qte"));
                list.add(pa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.toString());
        return list;
    }

    @Override
    public Produit_commande readById(int commande_id) {
        Produit_commande pa = new Produit_commande();
        String req = "SELECT * FROM produit_commande WHERE commande_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, commande_id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                pa.setId(rst.getInt("produit_id"));
                pa.setQte(rst.getInt("qte"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pa;    }




   /* public String textFacture()
    {

        return;
    }*/

}