package ar.com.ada.maven.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    private static String user = "AdaBank";
    private static String password = "daiyvicky";
    private static String host = "jdbc:mysql://localhost:3306/";
    private static String db = "bank";
    private static String drive = "com.mysql.cj.jdbc.Driver";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (connection == null || connection.isClosed()) {
            Class.forName(drive);
            String url = host + db + "?serverTimezone=UTC";
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
