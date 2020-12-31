package service.repository;

import service.MyWebSocketApp;
import service.model.User;
import service.model.UserType;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository extends JDBCRepository {
    JDBCRepository jdbcRepository;

    public PatientRepository() {
        this.jdbcRepository = new JDBCRepository();
    }
    public List<User> getPatients() throws DatabaseException, SQLException, URISyntaxException {
        List<User> users = new ArrayList<>();

        Connection connection = jdbcRepository.getDatabaseConnection();
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
                User user = new User(id, firstName, lastName, email, dateOfBirth, password, streetName, houseNr, city, zipcode, r);
                users.add(user);
            }
            connection.setAutoCommit(false);

            connection.commit();
            statement.close();
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return users;
    }
    public User getPatientById(int patientId) throws DatabaseException, SQLException, URISyntaxException {
        User user;
        Connection connection = jdbcRepository.getDatabaseConnection();
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
                user = new User(id, firstName, lastName, email, dateOfBirth, password, streetName, houseNr, city, zipcode, r);
            }
            statement.close();
            connection.close();
            return user;
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read patient from the database.",throwable);
        }
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

    }

    public boolean createPatient(User user) throws DatabaseException, SQLException, URISyntaxException {
        Connection connection = jdbcRepository.getDatabaseConnection();
        MyWebSocketApp myWebSocketApp = new MyWebSocketApp();
        Boolean exist;
        exist = false;
        String fullName;
        fullName = user.getFirstName() + user.getLastName();

//
//        String sql = "INSERT INTO contacts (userId, friendId) " +
//                "SELECT ?, ? WHERE NOT EXISTS (SELECT userId, friendId FROM contacts " +
//                "WHERE (userId = ? AND friendId = ?) OR (userId = ? AND friendId = ?))";

//        String sql = "INSERT INTO user (firstName, lastName, email, password, dateOfBirth, streetName, houseNr, zipcode, city, userType) " +
//                "SELECT ?, ?, ? WHERE NOT EXISTS (SELECT email, firstName, lastName FROM user " +
//                "WHERE (email = ? AND firstName = ? AND lastName = ?)";

        String sql = "INSERT INTO user ( firstName, lastName, email, password, dateOfBirth, streetName, houseNr, zipcode, city, userType) VALUES (?,?,?,?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        try {
            for (User p : getPatients()) {
                String exisitingFullName;
                exisitingFullName = p.getFirstName() + p.getLastName();
                if (exisitingFullName.equals(fullName)
                        && p.getStreetName().equals(user.getStreetName())
                        && p.getHouseNr() == user.getHouseNr()) {
                    throw new DatabaseException("Cannot create patient, already exists");
                }
                else if(p.getEmail().equals(user.getEmail())){
                    throw new DatabaseException("Cannot create patient, already exists");
                }
            }
            if (!exist) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getDateOfBirth());
                preparedStatement.setString(6,  user.getStreetName());
                preparedStatement.setInt(7,  user.getHouseNr());
                preparedStatement.setString(8,  user.getZipcode());
                preparedStatement.setString(9,  user.getCity());
                preparedStatement.setString(10, String.valueOf(user.getUserType()));

                int affected = preparedStatement.executeUpdate();

                if(affected <= 0) {
                    throw new DatabaseException("Cannot create contact, already exists");
                }
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
    public boolean updatePatient(User user) throws DatabaseException, SQLException, URISyntaxException {
        Connection connection = jdbcRepository.getDatabaseConnection();

        String sql = "update user set streetName=?, houseNr=?, city=?, zipcode=?, email=?, password=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
//            for (Patient p : getPatients()) {
//                if (p.getId() == patient.getId()) {
                    preparedStatement.setString(1, user.getStreetName());
                    preparedStatement.setInt(2, user.getHouseNr());
                    preparedStatement.setString(3, user.getCity());
                    preparedStatement.setString(4, user.getZipcode());
                    preparedStatement.setString(5, user.getEmail());
                    preparedStatement.setString(6, user.getPassword());
                    preparedStatement.setInt(7, user.getId());
                    int affected = preparedStatement.executeUpdate();

                    if(affected <= 0) {
                        return false;
                    }

                    connection.commit();
                    preparedStatement.close();
                    connection.close();
                    return true;
//                }
//            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot update the student.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
//        return false;
    }

//    delete a patient
    public boolean deletePatient(int patientid) throws DatabaseException, SQLException, URISyntaxException {
        Connection connection = jdbcRepository.getDatabaseConnection();

        String sql = "DELETE from user where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {

            preparedStatement.setInt(1, patientid);
            int affected = preparedStatement.executeUpdate();

            if(affected <= 0) {
                return false;
            }

            connection.commit();
            preparedStatement.close();
            connection.close();
            return true;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new student.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }

    }

}
