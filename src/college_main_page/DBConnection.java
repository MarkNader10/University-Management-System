package college_main_page;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UniversityDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "YOUR_USERNAME";
    private static final String PASSWORD = "YOUR_PASSWORD";
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
   
    public static boolean insertRecord(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int result = stmt.executeUpdate(query);
            return result > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public static boolean updateRecord(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int result = stmt.executeUpdate(query);
            return result > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public static boolean deleteRecord(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int result = stmt.executeUpdate(query);
            return result > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  
    public static ResultSet searchRecords(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeQuery(query); 
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}