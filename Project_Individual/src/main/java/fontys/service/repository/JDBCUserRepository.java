package fontys.service.repository;

import fontys.service.model.Patient;
import fontys.service.model.UserType;

import java.sql.*;


public class JDBCUserRepository extends JDBCRepository{
    public Patient getUser(String givenEmail, String givenPassword) throws DatabaseException, SQLException {

        Patient patient = null;

        Connection connection = this.getDatabaseConnection();
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
//            statement.close();
//            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read pharmacist from the database.",throwable);
        }
        finally {
            statement.close();
            connection.close();
        }
        return patient;
    }
}
