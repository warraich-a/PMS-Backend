package fontys.service.repository;

import fontys.service.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMedicineRepository extends JDBCRepository  {

    public List<Medicine> getMedicines() throws DatabaseException {
        List<Medicine> medicines = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM medicine";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String medName = resultSet.getString("medName");
                double price = resultSet.getDouble("price");
                double sellingPrice = resultSet.getDouble("sellingPrice");

                Medicine medicine = new Medicine(id, medName, price, sellingPrice);
                medicines.add(medicine);
            }
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read students from the database.",throwable);
        }
        return medicines;
    }


    public Medicine getMedicine(int medicineId) throws DatabaseException {
//        JDBCCountriesRepository countriesRepository = new JDBCCountriesRepository();
        Medicine medicine;
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM medicine WHERE id = ?";

        try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, medicineId); // set student_number parameter
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()){
            connection.close();
            throw new DatabaseException("Medicine with number " + medicineId + " cannot be found");
        } else {
            String name = resultSet.getString("medName");
            int id = resultSet.getInt("id");
            double price = resultSet.getDouble("price");
            double sellingPrice = resultSet.getDouble("sellingPrice");

            medicine = new Medicine(id, name, price, sellingPrice);
        }
        connection.close();
        return  medicine;
    } catch (SQLException throwable) {
        throw new DatabaseException("Cannot read students from the database.",throwable);
        }

    }

    public boolean createMedicine(Medicine medicine) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

        String sql = "INSERT INTO medicine ( medName, price, sellingPrice) VALUES (?,?,?) ";
        try {
            for (Medicine m : getMedicines()) {
                if (m.getMedName().equals(medicine.getMedName())) {
                    exist = true;
                }
            }
            if (!exist) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, medicine.getMedName());
                preparedStatement.setDouble(2, medicine.getPrice());
                preparedStatement.setDouble(3, medicine.getSellingPrice());
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
//
//                if (resultSet.next()) {
//                    int medId = resultSet.getInt(1);
//                    connection.commit();
//                    connection.close();
//
//                    medicine.setId(medId);
//                    return true;
//                } else {
//                    connection.close();
//                    throw new DatabaseException("Cannot get the id of the new student.");
//                }
            } else {
                connection.close();
                //throw new DatabaseException("Medicine with the same name already exist.");
                return false;
            }

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new student.", throwable);
        }

    }

    public boolean deleteMedicine(int medicinId) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

        String sql = "DELETE from medicine where id=?";

//        String sql = "INSERT INTO medicine ( medName, price, sellingPrice) VALUES (?,?,?) ";
        try {
            for (Medicine m : getMedicines()) {
                if (m.getId() == medicinId) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, medicinId);
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

    public boolean updateMedicine(Medicine medicine) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

//        String sql = "UPDATE medicine set medName=? where id=?";
        String sql = "update medicine set medName=?, price=?, sellingPrice=? where id=?";

//        String sql = "INSERT INTO medicine ( medName, price, sellingPrice) VALUES (?,?,?) ";
        try {
            for (Medicine m : getMedicines()) {
                if (m.getId() == medicine.getId()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, medicine.getMedName());
                    preparedStatement.setDouble(2, medicine.getPrice());
                    preparedStatement.setDouble(3, medicine.getSellingPrice());
                    preparedStatement.setInt(4, medicine.getId());
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
