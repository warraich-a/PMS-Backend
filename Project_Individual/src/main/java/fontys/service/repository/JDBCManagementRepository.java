package fontys.service.repository;

import fontys.service.PersistenceController;
import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCManagementRepository extends JDBCRepository{
    PersistenceController persistenceController = new PersistenceController();

    List<Patient> patients = persistenceController.getPatients();
    List<Medicine> medicines = persistenceController.getMedicines();

    public boolean addMedicineToPatient(Management m) throws DatabaseException, SQLException, ParseException {

        DateFormat formatter;
        Date startDate;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        startDate = formatter.parse(m.getStartDate());

        Boolean exist;
        exist = false;

        for (Management mg : getManagements()) {
            if (mg.getMedicineId() == m.getMedicineId() &&  mg.getPatientId() == m.getPatientId()) {

//                Date tempExistingStartDate=new SimpleDateFormat("yyyy/MM/dd").parse(mg.getStartDate());
                Date tempExistingEndDate;
                tempExistingEndDate = formatter.parse(mg.getEndDate());
//
//                Date tempStartDate=new SimpleDateFormat("yyyy/MM/dd").parse(m.getStartDate());
//                Date tempEndDate=new SimpleDateFormat("yyyy/MM/dd").parse(m.getEndDate());


                if(tempExistingEndDate.compareTo(startDate) < 0) {
                   exist = false;
                }

//                if(!mg.isActive()){
//                    updateMedicinePatient(m.getPatientId(), mg.getMedicineId(), true);
//                    return  true;
//                }
                else{

                    return false;
                }
            }
        }
        Connection connection = this.getDatabaseConnection();
        if(!exist) {
            String sql = "INSERT INTO connectionTable ( patientId, medicineId, isActive, startDate, endDate) VALUES (?,?,?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            try {
                    preparedStatement.setInt(1, m.getPatientId());
                    preparedStatement.setInt(2, m.getMedicineId());
                    preparedStatement.setBoolean(3, true);
                    preparedStatement.setString(4, m.getStartDate());
                    preparedStatement.setString(5, m.getEndDate());


                    preparedStatement.executeUpdate();
                    connection.setAutoCommit(false);
                    connection.commit();
                    preparedStatement.close();
                    connection.close();
//                    setNotification(m.getPatientId(), "New medicine is added");
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
    public boolean setNotification(int patientId, String content) throws DatabaseException, SQLException, ParseException {

//        DateFormat formatter;
//        Date startDate;
//        formatter = new SimpleDateFormat("yyyy-mm-dd");
//        startDate = formatter.parse(date.toString());

        Boolean exist;
        exist = false;

        Connection connection = this.getDatabaseConnection();
        if(!exist) {
            String sql = "INSERT INTO notification ( content, patientId, Date) VALUES (?,?, CURDATE()) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            try {
                preparedStatement.setString(1, content);
                preparedStatement.setInt(2, patientId);




                preparedStatement.executeUpdate();
                connection.setAutoCommit(false);
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
                String startDate = resultSet.getString("startDate");
                String endDate = resultSet.getString("endDate");

                Management management = new Management(id, patientId, medicinId,isActive, startDate, endDate );
                managements.add(management);
            }
//            statement.close();
//            connection.close();

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
        List<Medicine> foundMedicines = new ArrayList<>();
        Medicine med = null;
         for(Management m: getManagements()){
            if(m.getPatientId() == patientId){
                //this is to give a new medicine id everytime e.g. one patient can have a same medicine different times
                medId = m.getMedicineId();
                for (Medicine medicine: medicines)
                {
                    if(medId == medicine.getId())
                    {
                        //creating a new medicine everytime so that it wont override the old one with the same medicine id.
                        //e.g if it was a globally initialized then it will override all the old medicines with the last one by setMethods of a medicine

                        med = new Medicine();

                        // setting the values for that new medicine.
                        med.setManagementId(m.getId());
                        med.setMedName(medicine.getMedName());
                        med.setPrice(medicine.getPrice());
                        med.setActive(m.isActive());
                        med.setStartDate(m.getStartDate());
                        med.setEndDate(m.getEndDate());

                        //adding in a list
                        foundMedicines.add(med);

                    }
                }
            }
        }

        return foundMedicines;
    }

    public boolean deleteMedicinePatient(int patientId, int managementId) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        String sql = "update connectionTable set isActive=? where patientId=? AND id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Management p : getManagements()) {
                if (p.getPatientId() == patientId && p.getId() == managementId) {
                    preparedStatement.setBoolean(1, false);
                    preparedStatement.setInt(2, patientId);
                    preparedStatement.setInt(3, managementId);
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
