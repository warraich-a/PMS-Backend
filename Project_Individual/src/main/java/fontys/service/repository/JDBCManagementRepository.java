package fontys.service.repository;

import fontys.service.PersistenceController;
import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCManagementRepository extends JDBCRepository{
    PersistenceController persistenceController = new PersistenceController();

    List<Patient> patients = persistenceController.getPatients();
    List<Medicine> medicines = persistenceController.getMedicines();

    public boolean addMedicineToPatient(Management m) throws DatabaseException, SQLException {


        Boolean exist;
        exist = false;


        for (Management mg : getManagements()) {
            if (mg.getMedicineId() == m.getMedicineId() &&  mg.getPatientId() == m.getPatientId()) {
                if(!mg.isActive()){
                    updateMedicinePatient(m.getPatientId(), mg.getMedicineId(), true);
                    return  true;
                }
                else{

                    return false;
                }
            }
        }
        Connection connection = this.getDatabaseConnection();
        if(!exist) {
            String sql = "INSERT INTO connectionTable ( patientId, medicineId, isActive) VALUES (?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            try {
                    preparedStatement.setInt(1, m.getPatientId());
                    preparedStatement.setInt(2, m.getMedicineId());
                    preparedStatement.setBoolean(3, true);

                    preparedStatement.executeUpdate();

//                    PreparedStatement ps = connection.prepareStatement(sql, );
//                    ps.setString(1, "value");
                    connection.setAutoCommit(false);
//                    ps.close();
                    connection.commit();
                    preparedStatement.close();
                    connection.close();
                    return true;

            } catch (SQLException throwable) {
                throw new DatabaseException("Cannot add new medicine.", throwable);
            }
            finally {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }
        } else {
            return false;
        }
    }
    public List<Management> getManagements() throws DatabaseException, SQLException {
        List<Management> managements = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM connectionTable";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        try {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int patientId = resultSet.getInt("patientId");
                int medicinId = resultSet.getInt("medicineId");
                boolean isActive = resultSet.getBoolean("isActive");

                Management management = new Management(id, patientId, medicinId,isActive );
                managements.add(management);
            }
            statement.close();
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return managements;
    }

    public  List<Medicine> getMedicinesByPatientId(int patientId) throws DatabaseException, SQLException {
        int medId;
        Management management = new Management();
        for(Management m: getManagements()){
            if(m.getPatientId() == patientId){
                medId = m.getMedicineId();
                for (Medicine medicine: medicines){
                    if(medId == medicine.getId()){
                    management.ListOfMedicinesByPatient(medicine, m.isActive());
                    }
                }
            }
        }
        return management.getMedicines();
    }

    public boolean deleteMedicinePatient(int patientId, int medicineId) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        String sql = "update connectionTable set isActive=? where patientId=? AND medicineId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Management p : getManagements()) {
                if (p.getPatientId() == patientId && p.getMedicineId() == medicineId) {
                    preparedStatement.setBoolean(1, false);
                    preparedStatement.setInt(2, patientId);
                    preparedStatement.setInt(3, medicineId);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.close();
                    return true;
                }
            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new student.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
        return false;
    }


    public boolean updateMedicinePatient(int patientId, int medicineId, boolean status) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        String sql = "update connectionTable set isActive=? where patientId=? AND medicineId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Management p : getManagements()) {
                if (p.getPatientId() == patientId && p.getMedicineId() == medicineId) {
                    preparedStatement.setBoolean(1, status);
                    preparedStatement.setInt(2, patientId);
                    preparedStatement.setInt(3, medicineId);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.close();
                    return true;
                }
            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot add new medicine.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
        return false;
    }

}
