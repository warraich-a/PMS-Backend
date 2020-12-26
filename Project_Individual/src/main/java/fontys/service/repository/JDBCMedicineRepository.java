package fontys.service.repository;

import fontys.service.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMedicineRepository extends JDBCRepository  {

    public List<Medicine> getMedicines() throws DatabaseException, SQLException {
        List<Medicine> medicines = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM medicine";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        try {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String medName = resultSet.getString("medName");
                double price = resultSet.getDouble("price");
                double sellingPrice = resultSet.getDouble("sellingPrice");

                Medicine medicine = new Medicine(id, medName, price, sellingPrice);
                medicines.add(medicine);
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
        return medicines;
    }


    public Medicine getMedicine(int medicineId) throws DatabaseException, SQLException {
        Medicine medicine;
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM medicine WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        try {
        statement.setInt(1, medicineId);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()){
            statement.close();
            connection.close();
            throw new DatabaseException("Medicine with number " + medicineId + " cannot be found");
        } else {
            String name = resultSet.getString("medName");
            int id = resultSet.getInt("id");
            double price = resultSet.getDouble("price");
            double sellingPrice = resultSet.getDouble("sellingPrice");

            medicine = new Medicine(id, name, price, sellingPrice);
        }
        statement.close();
        connection.close();
        return  medicine;
        }
        catch (SQLException throwable)
        {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

    }

    public boolean createMedicine(Medicine medicine) throws DatabaseException, SQLException {


        Boolean exist;
        exist = false;
        for (Medicine m : getMedicines()) {
            if (m.getMedName().equals(medicine.getMedName())) {
                exist = true;
            }
        }
        Connection connection = this.getDatabaseConnection();
        String sql = "INSERT INTO medicine ( medName, price, sellingPrice) VALUES (?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            if (!exist) {
                preparedStatement.setString(1, medicine.getMedName());
                preparedStatement.setDouble(2, medicine.getPrice());
                preparedStatement.setDouble(3, medicine.getSellingPrice());
                preparedStatement.executeUpdate();

                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "value");
                connection.setAutoCommit(false);
                connection.commit();
                connection.close();
                return true;

            } else {
                connection.close();
                return false;
            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new student.", throwable);
        }
        finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

    }

    public boolean deleteMedicine(int medicinId) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();
        String sql = "DELETE from medicine where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            for (Medicine m : getMedicines()) {
                if (m.getId() == medicinId) {
                    preparedStatement.setInt(1, medicinId);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.close();
                    return true;
                }
            }
           return false;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot delete student.", throwable);
        }
        finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    public boolean updateMedicine(Medicine medicine) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();


        String sql = "update medicine set medName=?, price=?, sellingPrice=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            for (Medicine m : getMedicines()) {
                if (m.getId() == medicine.getId()) {
                    preparedStatement.setString(1, medicine.getMedName());
                    preparedStatement.setDouble(2, medicine.getPrice());
                    preparedStatement.setDouble(3, medicine.getSellingPrice());
                    preparedStatement.setInt(4, medicine.getId());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    connection.close();
                    return true;
                }
            }
            return false;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot update student.", throwable);
        }
        finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
}
