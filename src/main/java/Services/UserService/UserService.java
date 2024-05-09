
package Services.UserService;

import Entities.User.User;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserService implements IService<User> {
    private Connection conn;

    public UserService() 
    {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(User o) {
       String requete="insert into user (cin,nom,prenom,mdp,email,adresse,numtel,role) values (?,?,?,?,?,?,?,?)";
        
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1,o.getCin());
            pst.setString(2,o.getNom());
            pst.setString(3,o.getPrenom());
            pst.setString(4,o.getMdp());
            pst.setString(5,o.getEmail());
            pst.setString(6,o.getAdresse());
            pst.setInt(7,o.getNumtel());
            pst.setString(8,o.getRole());
            
            pst.executeUpdate();

            System.out.print("user ajouté");
        
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean isVerificationCodeCorrect(String email, String verificationCode) {
        String query = "SELECT google_id FROM user WHERE email = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedVerificationCode = resultSet.getString("google_id");
                return verificationCode.equals(storedVerificationCode);
            } else {
                System.out.println("No user found with email: " + email);
                return false; // Email not found in the database
            }
        } catch (SQLException e) {
            System.out.println("Error checking verification code: " + e.getMessage());
            return false; // Error occurred while querying the database
        }
    }

    public void resetPassword(String email, String newPassword) {
        String query = "UPDATE user SET `mdp` = ? WHERE email = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, email);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password reset successfully.");
            } else {
                System.out.println("Failed to reset password. User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error resetting password: " + e.getMessage());
        }
    }

    public void updateVerificationCode(String email, String verificationCode) {
        String query = "UPDATE user SET google_id = ? WHERE email = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, verificationCode);
            statement.setString(2, email);
            statement.executeUpdate();
            System.out.println("Verification code updated for user with email: " + email);
        } catch (SQLException e) {
            System.out.println("Error updating verification code: " + e.getMessage());
        }
    }
    @Override
public void update(User o) {
    String requete = "UPDATE user SET nom=?, prenom=?, mdp=?, email=?, adresse=?, numtel=?, role=? ,cin=? WHERE id=?";
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setString(1, o.getNom());
        pst.setString(2, o.getPrenom());
        pst.setString(3, o.getMdp());
        pst.setString(4, o.getEmail());
        pst.setString(5, o.getAdresse());
        pst.setInt(6, o.getNumtel());
        pst.setString(7, o.getRole());
        pst.setString(8, o.getCin());
        pst.setInt(9, o.getId());
        pst.executeUpdate();
        System.out.println("utilisateur mis à jour avec succès !");
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mdp"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getInt("numtel"),
                        rs.getString("role")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // If no user found with the provided email
    }

    public String getUserRoleByEmail(String email) {
        String query = "SELECT role FROM user WHERE email = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // If no user found with the provided email
    }


    @Override
    public void delete(int id) {
       String requete="DELETE FROM user WHERE id ="+id;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(requete);
            System.out.println("utilisateur supprimé avec succées ");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }}

    @Override
public User readById(int id) {
    String requete = "SELECT * FROM user WHERE id=?";
    User u = null;
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            u = new User(
                rs.getInt("id"),
                rs.getString("cin"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("mdp"),
                rs.getString("email"),
                rs.getString("adresse"),
                rs.getInt("numtel"),
                rs.getString("role")
            );
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return u;
}


    @Override
public ArrayList<User> readAll() {
    String requete = "SELECT * FROM user";
    ArrayList<User> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            User u = new User(
                rs.getInt("id"),
                rs.getString("cin"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("mdp"),
                rs.getString("email"),
                rs.getString("adresse"),
                rs.getInt("numtel"),
                rs.getString("role")
            );
            list.add(u);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

    
}
