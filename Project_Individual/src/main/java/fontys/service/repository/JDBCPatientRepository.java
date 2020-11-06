package fontys.service.repository;


import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class JDBCPatientRepository extends JDBCRepository  {

//    public Collection<Medicine> getMedicines() throws databaseException {
//        Map<String, Medicine> countries = this.getUsedCountries();
//        List<Student> students = new ArrayList<>();
//
//        Connection connection = this.getDatabaseConnection();
//        String sql = "SELECT * from students";
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                int studentNumber = resultSet.getInt("student_number");
//                String name = resultSet.getString("full_name");
//                String countryCode = resultSet.getString("country_code");
//                Country country = countries.get(countryCode);
//                Student student = new Student(studentNumber,name, country);
//                students.add(student);
//            }
//            connection.commit();
//            connection.close();
//
//        } catch (SQLException throwable) {
//            throw new SchoolDatabaseException("Cannot read students from the database.",throwable);
//        }
//        return students;
//    }
//
//    private Map<String, Medicine> getUsedCountries() throws databaseException {
//        Map<String, Medicine> countries = new HashMap<>();
//        Connection connection = this.getDatabaseConnection();
//
//        String sql = "SELECT * FROM medicine WHERE country_code in (select country_code from students)";
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String code = resultSet.getString("country_code");
//                Country country = new Country(code, name);
//                countries.put(code, country);
//            }
//            connection.commit();
//            connection.close();
//
//        } catch (SQLException throwable) {
//            throw new SchoolDatabaseException("Cannot read countries from the database.",throwable);
//        }
//        return  countries;
//    }

    public List<Patient> getPatients() throws DatabaseException {
        List<Patient> patients = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM patient";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int year = resultSet.getInt("year");
                String month = resultSet.getString("month");
                int day = resultSet.getInt("day");
                String disease = resultSet.getString("sickness");
                String streetName = resultSet.getString("streetName");
                int houseNr = resultSet.getInt("houseNr");
                String city = resultSet.getString("city");
                String zipcode = resultSet.getString("zipcode");

                Patient patient = new Patient(id, firstName, lastName, email, year, month, day, disease, password, streetName, houseNr, city, zipcode);
                patients.add(patient);
            }
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        return patients;
    }
    public Patient getPatientById(int patientId) throws DatabaseException {
//        JDBCCountriesRepository countriesRepository = new JDBCCountriesRepository();
        Patient patient;
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM patient WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
                int year = resultSet.getInt("year");
                String month = resultSet.getString("month");
                int day = resultSet.getInt("day");
                String disease = resultSet.getString("sickness");
                String streetName = resultSet.getString("streetName");
                int houseNr = resultSet.getInt("houseNr");
                String city = resultSet.getString("city");
                String zipcode = resultSet.getString("zipcode");
                patient = new Patient(id, firstName, lastName, email, year, month, day, disease, password, streetName, houseNr, city, zipcode);
            }
            connection.close();
            return  patient;
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read patient from the database.",throwable);
        }

    }
    public boolean createPatient(Patient patient) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;
        String fullName;
        fullName = patient.getFirstName() + patient.getLastName();


        String sql = "INSERT INTO patient ( firstName, lastName, email, password, year, month, day, sickness, streetName, houseNr, zipcode, city) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            for (Patient p : getPatients()) {
                String exisitingFullName;
                exisitingFullName = p.getFirstName() + p.getLastName();
                if (exisitingFullName.equals(fullName) && p.getStreetName().equals(patient.getStreetName()) && p.getHouseNr() == patient.getHouseNr()) {
                    exist = true;
                }
            }
            if (!exist) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, patient.getFirstName());
                preparedStatement.setString(2, patient.getLastName());
                preparedStatement.setString(3, patient.getEmail());
                preparedStatement.setString(4, patient.getPassword());
                preparedStatement.setInt(5,  patient.getYear());
                preparedStatement.setString(6,  patient.getMonth());
                preparedStatement.setInt(7,  patient.getDay());
                preparedStatement.setString(8,  patient.getDisease());
                preparedStatement.setString(9,  patient.getStreetName());
                preparedStatement.setInt(10,  patient.getHouseNr());
                preparedStatement.setString(11,  patient.getZipcode());
                preparedStatement.setString(12,  patient.getDisease());
                preparedStatement.executeUpdate();

                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "value");
                connection.setAutoCommit(false);
                connection.commit();
                connection.close();
                return true;
            } else {
                connection.close();
                //throw new DatabaseException("Medicine with the same name already exist.");
                return false;
            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new patient.", throwable);
        }

    }
    public boolean updatePatient(Patient patient) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

//        String sql = "UPDATE medicine set medName=? where id=?";
        String sql = "update patient set streetName=?, houseNr=?, city=?, zipcode=?, email=?, password=? where id=?";

//        String sql = "INSERT INTO medicine ( medName, price, sellingPrice) VALUES (?,?,?) ";
        try {
            for (Patient p : getPatients()) {
                if (p.getId() == patient.getId()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, patient.getStreetName());
                    preparedStatement.setInt(2, patient.getHouseNr());
                    preparedStatement.setString(3, patient.getCity());
                    preparedStatement.setString(4, patient.getZipcode());
                    preparedStatement.setString(5, patient.getEmail());
                    preparedStatement.setString(6, patient.getPassword());
                    preparedStatement.setInt(7, patient.getId());
                    preparedStatement.executeUpdate();
                    connection.close();
                    return true;
                }
            }
            return false;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new student.", throwable);
        }
    }

    public boolean deletePatient(int patientid) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

        String sql = "DELETE from patient where id=?";

        try {
            for (Patient p : getPatients()) {
                if (p.getId() == patientid) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, patientid);
                    preparedStatement.executeUpdate();
                    connection.close();
                    return true;
                }
            }
            return false;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new student.", throwable);
        }
    }

}
