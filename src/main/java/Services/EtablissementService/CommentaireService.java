package Services.EtablissementService;

import Entities.Etablissement.Commentaire;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentaireService {
    private Connection conn;

    public CommentaireService() {
        conn = DataSource.getInstance().getCnx();
    }

    public void insert(Commentaire c) {
        String requete = "INSERT INTO commentaire (etablissement_id, message, note) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, c.getEtablissement_id());
            pst.setString(2, c.getMessage());
            pst.setString(3, c.getNote());
            pst.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        String requete = "DELETE FROM commentaire WHERE id=?";
        try (PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Commentaire supprimé avec succès");
            } else {
                System.out.println("Aucun commentaire trouvé avec cet ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Commentaire> readAll() {
        String requete = "SELECT * FROM commentaire";
        ArrayList<Commentaire> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(requete)) {
            while (rs.next()) {
                Commentaire c = new Commentaire(
                        rs.getInt("id"),
                        rs.getInt("utilisateur_id"),
                        rs.getInt("etablissement_id"),
                        rs.getString("message"),
                        rs.getString("note")
                );
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Commentaire> readByEtablissement(int etablissementId) {
        String requete = "SELECT * FROM commentaire WHERE etablissement_id = ?";
        ArrayList<Commentaire> list = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, etablissementId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Commentaire c = new Commentaire(
                            rs.getInt("id"),
                            rs.getInt("utilisateur_id"),
                            rs.getInt("etablissement_id"),
                            rs.getString("message"),
                            rs.getString("note")
                    );
                    list.add(c);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


}
