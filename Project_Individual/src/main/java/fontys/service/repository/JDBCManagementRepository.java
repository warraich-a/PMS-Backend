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

    public boolean addMedicineToPatient(Management m) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;


        for (Management mg : getManagements()) {
            if (mg.getMedicineId() == m.getMedicineId() &&  mg.getPatientId() == m.getPatientId()) {
                exist = true;
                return false;
            }
        }
        if(!exist) {
            String sql = "INSERT INTO connectionTable ( patientId, medicineId, isActive) VALUES (?,?,?) ";
            try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, m.getPatientId());
                    preparedStatement.setInt(2, m.getMedicineId());
                    preparedStatement.setBoolean(3, true);

                    preparedStatement.executeUpdate();

                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, "value");
                    connection.setAutoCommit(false);
                    connection.commit();
                    connection.close();
                    return true;

            } catch (SQLException throwable) {
                throw new DatabaseException("Cannot add new medicine.", throwable);
            }
        } else {
            return false;
        }
    }
    public List<Management> getManagements() throws DatabaseException {
        List<Management> managements = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM connectionTable";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int patientId = resultSet.getInt("patientId");
                int medicinId = resultSet.getInt("medicineId");
                boolean isActive = resultSet.getBoolean("isActive");

                Management management = new Management(id, patientId, medicinId,isActive );
                managements.add(management);
            }
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        return managements;
    }

    public  List<Medicine> getMedicinesByPatientId(int patientId) throws DatabaseException {
        int medId;
        List<Medicine> foundMedicines = new ArrayList<>();

        for(Management m: getManagements()){
            if(m.getPatientId() == patientId){
                medId = m.getMedicineId();
                for (Medicine medicine: medicines){
                    if(medId == medicine.getId()){
                        foundMedicines.add(medicine);
                    }
                }
            }
        }
        return foundMedicines;
    }

}
