package Services.Reclamation;



import Entities.Reclamation.Reponse;

import java.sql.SQLException;
import java.util.List;

public interface ReclamtionResponseServiceInterface<entity>{
    public int addreclamtion(entity t) throws SQLException;
    public List<entity> getAllReclamations() throws SQLException;
    public List<entity> getAllUserReclamations(int id) throws SQLException;
    public entity findById(int id) throws SQLException;
    public int ModifierReclamtion(entity t) throws SQLException;
    public int supprimerReclamtion(int id) throws SQLException;
    public int addResponse(Reponse r, int id) throws SQLException;
}
