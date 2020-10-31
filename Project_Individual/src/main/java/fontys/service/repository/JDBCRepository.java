package fontys.service.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class JDBCRepository {

    protected Connection getDatabaseConnection() throws DatabaseException {
        String url = "jdbc:mysql://studmysql01.fhict.local:3306/dbi435688";
        String username = "dbi435688";
        String password = "database54";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (SQLException e){
            throw  new IllegalStateException("Driver failed" +url+ ".", e);

        }


//        ClassLoader classLoader = this.getClass().getClassLoader();
//        String DATABASE_FILE_NAME = "school.accdb";
//        URL url = classLoader.getResource(DATABASE_FILE_NAME);
//        if (url != null) {
//            try {
//                String path = url.toURI().getPath();
//                return DriverManager.getConnection("jdbc:ucanaccess://" + path);
//            }  catch (URISyntaxException e) {
//                throw new databaseException("Cannot load the file "+ DATABASE_FILE_NAME +"! Check path.", e);
//            } catch (SQLException e) {
//                throw new databaseException("JDBC driver failed to connect to the database " + DATABASE_FILE_NAME + ".", e);
//            }
//        } else {
//            throw new databaseException("Failed to open file " + DATABASE_FILE_NAME);
//        }
    }
}
