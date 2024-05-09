package Services.UserService;

import Entities.User.VerificationRequest;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerificationRequestService {
    private Connection conn;

    public VerificationRequestService() {
        conn = DataSource.getInstance().getCnx();
    }

    public void insert(VerificationRequest request) {
        String query = "INSERT INTO verification_requests (user_id, confirmed) VALUES (?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, request.getUser().getId());
            pst.setBoolean(2, request.isConfirmed());
            pst.executeUpdate();
            System.out.println("Verification request added successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(VerificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(VerificationRequest request) {
        String query = "UPDATE verification_requests SET confirmed = ? WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setBoolean(1, request.isConfirmed());
            pst.setInt(2, request.getId());
            pst.executeUpdate();
            System.out.println("Verification request updated successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(VerificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM verification_requests WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Verification request deleted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(VerificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public VerificationRequest getById(int id) {
        String query = "SELECT * FROM verification_requests WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new VerificationRequest(
                        rs.getInt("id"),
                        new UserService().readById(rs.getInt("user_id")),
                        rs.getBoolean("confirmed")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public VerificationRequest getByUserId(int userId) {
        String query = "SELECT * FROM verification_requests WHERE user_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new VerificationRequest(
                        rs.getInt("id"),
                        new UserService().readById(rs.getInt("user_id")),
                        rs.getBoolean("confirmed")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<VerificationRequest> getAll() {
        String query = "SELECT * FROM verification_requests";
        ArrayList<VerificationRequest> requests = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                VerificationRequest request = new VerificationRequest(
                        rs.getInt("id"),
                        new UserService().readById(rs.getInt("user_id")),
                        rs.getBoolean("confirmed")
                );
                requests.add(request);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerificationRequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requests;
    }
}
