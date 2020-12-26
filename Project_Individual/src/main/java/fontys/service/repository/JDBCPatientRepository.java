package fontys.service.repository;

import fontys.service.MyWebSocketApp;
import fontys.service.model.Patient;
import fontys.service.model.UserType;

import java.sql.*;
import java.util.*;

public class JDBCPatientRepository extends JDBCRepository  {

    public List<Patient> getPatients() throws DatabaseException, SQLException {
        List<Patient> patients = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM user";
        Statement statement = connection.createStatement();

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String dateOfBirth = resultSet.getString("dateOfBirth");
//                int year = resultSet.getInt("year");
//                String month = resultSet.getString("month");
//                int day = resultSet.getInt("day");
//                String disease = resultSet.getString("sickness");
                String streetName = resultSet.getString("streetName");
                int houseNr = resultSet.getInt("houseNr");
                String city = resultSet.getString("city");
                String zipcode = resultSet.getString("zipcode");
                String userType = resultSet.getString("userType");


                UserType r = UserType.Pharmacist;
                if (userType.equals("Pharmacist"))
                {
                    r = UserType.Pharmacist;
                }
                else if (userType.equals("Patient"))
                {
                    r = UserType.Patient;
                }
                Patient patient = new Patient(id, firstName, lastName, email, dateOfBirth, password, streetName, houseNr, city, zipcode, r);
                patients.add(patient);
            }
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return patients;
    }
    public Patient getPatientById(int patientId) throws DatabaseException, SQLException {
        Patient patient;
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        try {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                connection.close();
                throw new DatabaseException("patient with id " + patientId + " cannot be found");
            } else {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String dateOfBirth = resultSet.getString("dateOfBirth");

//                int year = resultSet.getInt("year");
//                String month = resultSet.getString("month");
//                int day = resultSet.getInt("day");
//                String disease = resultSet.getString("sickness");
                String streetName = resultSet.getString("streetName");
                int houseNr = resultSet.getInt("houseNr");
                String city = resultSet.getString("city");
                String zipcode = resultSet.getString("zipcode");
                String userType = resultSet.getString("userType");


                UserType r = UserType.Pharmacist;
                if (userType.equals("Pharmacist"))
                {
                    r = UserType.Pharmacist;
                }
                else if (userType.equals("Patient"))
                {
                    r = UserType.Patient;
                }
                patient = new Patient(id, firstName, lastName, email, dateOfBirth, password, streetName, houseNr, city, zipcode, r);
            }
            statement.close();
            connection.close();
            return  patient;
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read patient from the database.",throwable);
        }
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

    }
    public boolean createPatient(Patient patient) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();
        MyWebSocketApp myWebSocketApp = new MyWebSocketApp();
        Boolean exist;
        exist = false;
        String fullName;
        fullName = patient.getFirstName() + patient.getLastName();


        String sql = "INSERT INTO user ( firstName, lastName, email, password, dateOfBirth, streetName, houseNr, zipcode, city, userType) VALUES (?,?,?,?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        try {
            for (Patient p : getPatients()) {
                String exisitingFullName;
                exisitingFullName = p.getFirstName() + p.getLastName();
                if (exisitingFullName.equals(fullName)
                        && p.getStreetName().equals(patient.getStreetName())
                        && p.getHouseNr() == patient.getHouseNr()) {
                    exist = true;
                }
                else if(p.getEmail().equals(patient.getEmail())){
                    exist = true;
                }
            }
            if (!exist) {
                preparedStatement.setString(1, patient.getFirstName());
                preparedStatement.setString(2, patient.getLastName());
                preparedStatement.setString(3, patient.getEmail());
                preparedStatement.setString(4, patient.getPassword());
                preparedStatement.setString(5, patient.getDateOfBirth());

//                preparedStatement.setString(6,  patient.getDisease());
                preparedStatement.setString(6,  patient.getStreetName());
                preparedStatement.setInt(7,  patient.getHouseNr());
                preparedStatement.setString(8,  patient.getZipcode());
                preparedStatement.setString(9,  patient.getCity());
                preparedStatement.setString(10, String.valueOf(patient.getUserType()));

                preparedStatement.executeUpdate();
//                myWebSocketApp.AddOnConnect(patient.getWebSocket(), patient.getId());

                connection.setAutoCommit(false);

                connection.commit();
                connection.close();
                return true;
            } else {
                preparedStatement.close();
                connection.close();
                return false;
            }
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new patient.", throwable);
        }
        finally {

            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

    }

//    update patient
    public boolean updatePatient(Patient patient) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        String sql = "update user set streetName=?, houseNr=?, city=?, zipcode=?, email=?, password=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Patient p : getPatients()) {
                if (p.getId() == patient.getId()) {
                    preparedStatement.setString(1, patient.getStreetName());
                    preparedStatement.setInt(2, patient.getHouseNr());
                    preparedStatement.setString(3, patient.getCity());
                    preparedStatement.setString(4, patient.getZipcode());
                    preparedStatement.setString(5, patient.getEmail());
                    preparedStatement.setString(6, patient.getPassword());
                    preparedStatement.setInt(7, patient.getId());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.close();
                    return true;
                }
            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot update the student.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
        return false;
    }

//    delete a patient
    public boolean deletePatient(int patientid) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        String sql = "DELETE from user where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (Patient p : getPatients()) {
                if (p.getId() == patientid) {
                    preparedStatement.setInt(1, patientid);
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

}
