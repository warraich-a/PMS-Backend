package fontys.service.repository;


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
                int month = resultSet.getInt("month");
                int day = resultSet.getInt("day");
                String disease = resultSet.getString("disease");

                Patient patient = new Patient(id, firstName, lastName, email, year, month, day, disease, password);
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
                int month = resultSet.getInt("month");
                int day = resultSet.getInt("day");
                String disease = resultSet.getString("disease");

                patient = new Patient(id, firstName, lastName, email, year, month, day, disease, password);
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


        String sql = "INSERT INTO patient ( firstName, lastName, email, password, year, month, day, disease) VALUES (?,?,?,?,?,?,?,?) ";
        try {
            for (Patient p : getPatients()) {
                String exisitingFullName;
                exisitingFullName = p.getFirstName() + p.getLastName();
                if (exisitingFullName.equals(fullName)) {
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
                preparedStatement.setInt(6,  patient.getMonth());
                preparedStatement.setInt(7,  patient.getDay());
                preparedStatement.setString(8,  patient.getDisease());

                preparedStatement.executeUpdate();
//                connection.commit();
                //connection.close();
                // student_number is auto-increment, so get it now and set it in the student object:
                //ResultSet resultSet = preparedStatement.getGeneratedKeys();
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

//    public Student getStudent(int studentNumber) throws SchoolDatabaseException{
//      JDBCCountriesRepository countriesRepository = new JDBCCountriesRepository();
//      Connection connection = this.getDatabaseConnection();
//    String sql = "SELECT * FROM students WHERE student_number = ?";
//        try {
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, studentNumber); // set student_number parameter
//        ResultSet resultSet = statement.executeQuery();
//        if (!resultSet.next()){
//            connection.close();
//            throw new SchoolDatabaseException("Student with student number " + studentNumber + " cannot be found");
//        } else {
//            String name = resultSet.getString("full_name");
//            String countryCode = resultSet.getString("country_code");
//            connection.close();
//            Country country = countriesRepository.getCountry(countryCode);
//            return new Student(studentNumber, name, country);
//        }
//    } catch (SQLException throwable) {
//        throw new SchoolDatabaseException("Cannot read students from the database.",throwable);
//    }
//    }
//
//    /**
//     * This method deletes the student record from the DB for given studentNumber.
//     * @param studentNumber of the students who sould be deleted from the DB
//     * @throws SchoolDatabaseException
//     */
//    public void deleteStudent(int studentNumber) throws SchoolDatabaseException {
//       //@TODO Implement this method by deleting the student via JDBC.
//    }
//
//    public void create(Student student) throws SchoolDatabaseException {
//        Connection connection = this.getDatabaseConnection();
//
//        String sql = "INSERT INTO students ( full_name, country_code) VALUES (?,?) ";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, student.getName());
//            preparedStatement.setString(2, student.getCountry().getCode());
//            preparedStatement.executeUpdate();
//
//            // student_number is auto-increment, so get it now and set it in the student object:
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                int studentNumber = resultSet.getInt(1);
//                connection.commit();
//                connection.close();
//                student.setStudentNumber(studentNumber);
//            } else {
//                 connection.close();
//                throw  new SchoolDatabaseException("Cannot get the id of the new student.");
//            }
//
//        } catch (SQLException throwable) {
//            throw  new SchoolDatabaseException("Cannot create new student.", throwable);
//        }
//    }
//
//    /**
//     * This method updates the record in the DB for given student.
//     * @param student which should be updated
//     * @throws SchoolDatabaseException
//     */
//    public void update(Student student) throws SchoolDatabaseException {
//        //@TODO Implement this method by updating the student via JDBC.
//    }

}
