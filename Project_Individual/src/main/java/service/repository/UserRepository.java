package service.repository;

import service.model.User;
import service.model.UserType;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepository extends JDBCRepository {
    JDBCRepository jdbcRepository;

    public UserRepository() {
        this.jdbcRepository = new JDBCRepository();
    }

    public User getUser(String givenEmail, String givenPassword) throws DatabaseException, SQLException, URISyntaxException {

        User user = null;

        Connection connection = jdbcRepository.getDatabaseConnection();
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        try {

            statement.setString(1, givenEmail);
            statement.setString(2, givenPassword);
            ResultSet resultSet = statement.executeQuery();

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

                user = new User(id, firstName, lastName, email, dateOfBirth, password, streetName, houseNr, city, zipcode, r);
            }
//            statement.close();
//            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read pharmacist from the database.",throwable);
        }
        finally {
            statement.close();
            connection.close();
        }
        return user;
    }
    public User getUserByEmail(String givenEmail) throws DatabaseException, SQLException, URISyntaxException {

        User user = null;

        Connection connection = jdbcRepository.getDatabaseConnection();
        String sql = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        try {

            statement.setString(1, givenEmail);
            ResultSet resultSet = statement.executeQuery();

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

                user = new User(id, firstName, lastName, email, dateOfBirth, password, streetName, houseNr, city, zipcode, r);
            }
//            statement.close();
//            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read pharmacist from the database.",throwable);
        }
        finally {
            statement.close();
            connection.close();
        }
        return user;
    }
}
