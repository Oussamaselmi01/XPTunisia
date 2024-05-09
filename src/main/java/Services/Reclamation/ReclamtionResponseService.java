package Services.Reclamation;



import Entities.Reclamation.Reclammation;
import Entities.Reclamation.Reponse;
import Entities.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DataSource;

public class ReclamtionResponseService implements ReclamtionResponseServiceInterface<Reclammation> {
    private final Connection cnx;

    public ReclamtionResponseService() {
        this.cnx= DataSource.getInstance().getCnx();
    }

    @Override
    public int addreclamtion(Reclammation t) throws SQLException {
        java.util.Date utilDate = t.getDate_creation();
        Date sqlDate = new Date(utilDate.getTime());
        String req = "INSERT INTO reclammation (utilisateur_id, description, titre, date_creation, statut,user_id) " +
                "VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, t.getDescription());
        preparedStatement.setString(3, t.getTitre());
        preparedStatement.setDate(4, sqlDate);
        preparedStatement.setString(5, t.getStatut());
        preparedStatement.setInt(6,t.getUtulisateur().getId());
        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<Reclammation> getAllReclamations() throws SQLException {
        List<Reclammation> Reclammations = new ArrayList<>();
        String req = "SELECT * FROM reclammation";
        PreparedStatement statement = cnx.prepareStatement(req);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Reclammation Reclammation = new Reclammation();
            Reclammation.setId(resultSet.getInt("id"));
            Reclammation.setDescription(resultSet.getString("description"));
            Reclammation.setTitre(resultSet.getString("titre"));
            Reclammation.setDate_creation(resultSet.getDate("date_creation"));
            Reclammation.setStatut(resultSet.getString("statut"));
            int utilisateur_id = resultSet.getInt("utilisateur_id");
            int reponse_id = resultSet.getInt("reponse_id");
            User utilisateur = getUtilisateurById(utilisateur_id);
            Reclammation.setUtulisateur(utilisateur);
            Reponse reponse = getReponseById(reponse_id);
            Reclammation.setReponse(reponse);

            Reclammations.add(Reclammation);
        }
        return Reclammations;
    }

    private User getUtilisateurById(int id) throws SQLException {
        String req = "SELECT * FROM utilisateur WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User utilisateur = new User();
            utilisateur.setId(resultSet.getInt("id"));
            utilisateur.setNom(resultSet.getString("nom"));
            utilisateur.setPrenom(resultSet.getString("prenom"));
            utilisateur.setEmail(resultSet.getString("email"));
            return utilisateur;
        }
        return null;
    }

    private Reponse getReponseById(int id) throws SQLException {
        String req = "SELECT * FROM reponse WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Reponse reponse = new Reponse();
            reponse.setId(resultSet.getInt("id"));
            reponse.setContenu(resultSet.getString("contenu"));
            return reponse;
        }
        return null;
    }

    @Override
    public List<Reclammation> getAllUserReclamations(int id) throws SQLException {
        List<Reclammation> Reclammations = new ArrayList<>();
        String req = "SELECT * FROM reclammation WHERE user_id = ?";
        PreparedStatement statement = cnx.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Reclammation Reclammation = new Reclammation();
            Reclammation.setId(resultSet.getInt("id"));
            Reclammation.setDescription(resultSet.getString("description"));
            Reclammation.setTitre(resultSet.getString("titre"));
            Reclammation.setDate_creation(resultSet.getDate("date_creation"));
            Reclammation.setStatut(resultSet.getString("statut"));
            int utilisateur_id = resultSet.getInt("utilisateur_id");
            int reponse_id = resultSet.getInt("reponse_id");
            User utilisateur = getUtilisateurById(utilisateur_id);
            Reclammation.setUtulisateur(utilisateur);
            Reponse reponse = getReponseById(reponse_id);
            Reclammation.setReponse(reponse);

            Reclammations.add(Reclammation);
        }
        return Reclammations;
    }

    @Override
    public Reclammation findById(int id) throws SQLException {
        String req = "SELECT * FROM reclammation WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Reclammation Reclammation = new Reclammation();
            Reclammation.setId(resultSet.getInt("id"));
            Reclammation.setDescription(resultSet.getString("description"));
            Reclammation.setTitre(resultSet.getString("titre"));
            Reclammation.setDate_creation(resultSet.getDate("date_creation"));
            Reclammation.setStatut(resultSet.getString("statut"));
            int utilisateur_id = resultSet.getInt("utilisateur_id");
            int reponse_id = resultSet.getInt("reponse_id");
            User utilisateur = getUtilisateurById(utilisateur_id);
            Reclammation.setUtulisateur(utilisateur);
            Reponse reponse = getReponseById(reponse_id);
            Reclammation.setReponse(reponse);
            return Reclammation;
        }
        return null;
    }

    @Override
    public int ModifierReclamtion(Reclammation t) throws SQLException {
        java.util.Date utilDate = t.getDate_creation();
        Date sqlDate = new Date(utilDate.getTime());
        Reclammation optional=findById(t.getId());
        if(optional!=null){
            String req = "UPDATE reclammation SET " +
                    "titre = ?, " +
                    "description = ?, " +
                    "date_creation = ?, " +
                    "statut = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, t.getTitre());
            preparedStatement.setString(2, t.getDescription());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, t.getStatut());
            preparedStatement.setInt(5, t.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int supprimerReclamtion(int id) throws SQLException {
        Reclammation reclammation = findById(id);
        if (reclammation == null)
            return 0;

        int rowsDeleted;
        String reqDelete = "DELETE FROM reclammation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(reqDelete)) {
            statement.setInt(1, id);
            rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
               if(reclammation.getReponse()!=null){
                   String reqDeleteReponses = "DELETE FROM reponse WHERE id = ?";
                   try (PreparedStatement statementReponses = cnx.prepareStatement(reqDeleteReponses)) {
                       statementReponses.setInt(1, reclammation.getReponse().getId());
                       int rowsDeletedReponses = statementReponses.executeUpdate();
                       return rowsDeletedReponses;
                   } catch (SQLException e) {
                       e.printStackTrace();
                       return 0;
                   }
               }
               else return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int addResponse(Reponse r, int id) throws SQLException {
        java.util.Date utilDate = r.getDate_creation();
        Date sqlDate = new Date(utilDate.getTime());
        String req = "INSERT INTO reponse (contenu, date_creation) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, r.getContenu());
            preparedStatement.setDate(2, sqlDate);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        String req2 = "UPDATE reclammation SET reponse_id = ? ,statut = ? WHERE id = ?";
                        try (PreparedStatement preparedStatement2 = cnx.prepareStatement(req2)) {
                            preparedStatement2.setInt(1, generatedId);
                            preparedStatement2.setString(2, "Traité");
                            preparedStatement2.setInt(3, id);
                            int rowsUpdated = preparedStatement2.executeUpdate();
                            return rowsUpdated > 0 ? 1 : 0;
                        }
                    }
                }
            }
        }
        return 0;
    }
    public Reclammation getComplaintDetails(int complaintId) throws SQLException {
        Reclammation complaint = null;
        String complaintQuery = "SELECT * FROM reclammation WHERE id = ?";
        String responseQuery = "SELECT * FROM reponse WHERE id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(complaintQuery)) {
            statement.setInt(1, complaintId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve complaint details from the result set
                    complaint = new Reclammation();
                    complaint.setId(resultSet.getInt("id"));
                    complaint.setTitre(resultSet.getString("titre"));
                    complaint.setDescription(resultSet.getString("description"));
                    complaint.setDate_creation(resultSet.getDate("date_creation"));

                    // Retrieve response ID associated with the complaint
                    int responseId = resultSet.getInt("reponse_id");

                    // Retrieve response details from the response table
                    try (PreparedStatement responseStatement = cnx.prepareStatement(responseQuery)) {
                        responseStatement.setInt(1, responseId);
                        try (ResultSet responseResultSet = responseStatement.executeQuery()) {
                            if (responseResultSet.next()) {
                                // Retrieve response details from the result set
                                // Assuming 'reponse' is a column in 'reponse' table
                                String reponseContenu = responseResultSet.getString("contenu");
                                // Set the response to the complaint
                                complaint.setReponse(new Reponse(reponseContenu)); // Adjust constructor as needed
                            }
                        }
                    }
                }
            }
        }

        return complaint;
    }


}
