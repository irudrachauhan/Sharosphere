package sharosphere.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a single JDBC connection to the sharosphere MySQL database.
 * Usage:
 *   try (Connection conn = DBConnection.getConnection()) {
 *       // use conn
 *   }
 */
public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sharosphere?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";        // ← replace with your MySQL username
    private static final String PASSWORD = "1234"; // ← replace with your MySQL password

    static {
        try {
            // Explicitly load the driver (required for some environments)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "MySQL JDBC Driver not found. Add mysql-connector-j-*.jar to your classpath.", e);
        }
    }

    /**
     * Returns a new Connection. Caller is responsible for closing it
     * (use try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Prevent instantiation
    private DBConnection() {}
}
