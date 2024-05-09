package Services.Produit;

import Entities.Produit.Commande;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements Iservice<Commande> {

    private Connection con;

    //private Statement ste;
    public CommandeService() {
        con = DataSource.getInstance().getCnx();
    }

    public void add(Commande p) {
        String requete = "insert into Commande (id, utilisateur_id, mode_paiement, adresse_livraison, total, client_name, phone_number, date_livraison) values (?,1,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(   1, p.getId());
            pst.setString(2, p.getMode_paiement());
            pst.setString(3, p.getAdresse_livraison());
            pst.setDouble(4, p.getTotal());
            pst.setString(5, p.getClient_name());
            pst.setString(6, p.getPhone_number());
            pst.setDate(  7, p.getDate_livraison());


            pst.executeUpdate();
            System.out.println("Commande ajouté avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Commande p) {
        String requete = "DELETE FROM Commande WHERE id=?";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Commande supprimé avec succes");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("Commande non supprimé!");
        }
    }

    @Override
    public void update(Commande p) {
        String req = "UPDATE `Commande` SET mode_paiement=?, adresse_livraison=?, total=?, client_name=?, phone_number=?, date_livraison=?, utilisateur_id=1 WHERE id = ?";
        try {
            PreparedStatement pst = con.prepareStatement(req);

            pst.setString(1, p.getMode_paiement());
            pst.setString(2, p.getAdresse_livraison());
            pst.setDouble(3, p.getTotal());
            pst.setString(4, p.getClient_name());
            pst.setString(5, p.getPhone_number());
            pst.setDate(6, p.getDate_livraison());

            pst.setInt(7, p.getId());

            pst.executeUpdate();
            System.out.println("Commande modifié avec succes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }
    }

    @Override
    public List<Commande> readall() {
        String requete = "select * from Commande";
        List<Commande> list = new ArrayList<>();
        try {
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Commande p = new Commande(rs.getInt("id"), rs.getInt("utilisateur_id"), rs.getString("mode_paiement"), rs.getString("adresse_livraison"), rs.getString("phone_number"), rs.getString("client_name") ,rs.getDouble("total"));
                    list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.toString());
        return list;
    }

    @Override
    public Commande readById(int id) {
      Commande p = new Commande();
        String req = "SELECT * FROM Commande WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                p.setId(rst.getInt("id"));
                p.setUtilisateur_id(rst.getInt("utilisateur_id"));
                p.setClient_name(rst.getString("Client_name"));
                p.setPhone_number(rst.getString("Phone_number"));
                p.setTotal(rst.getDouble("Total"));
                p.setDate_livraison(Date.valueOf(rst.getString("Date_livraison")));
                p.setAdresse_livraison(rst.getString("Adresse_livraison"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;

    }
}