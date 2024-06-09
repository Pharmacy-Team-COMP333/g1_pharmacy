package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/g1_pharmcy";
    private static final String USER = "root";
    private static final String PASSWORD = "SAMI21135sami";

    private static Connection conn;
    private static Statement stmt;

    public static Connector a = new Connector();

    public Connector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return conn;
    }
    

    public Connection connectDB() throws ClassNotFoundException, SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        stmt = conn.createStatement();
        return conn;
    }

    public Statement ExecuteStatement(String query) throws SQLException {
        return (Statement) stmt.executeQuery(query);
    }
    
    public void ExecuteUpdate(String SQL) throws SQLException {
        try {
            java.sql.Statement stmt = connectDB().createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}