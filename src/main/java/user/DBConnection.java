package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.System.getenv;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;

        try {
            Map<String, String> env = getenv();
            Class.forName("oracle.jdbc.OracleDriver");

            String url = env.get("DB_URL");
            String id = env.get("DB_ID");
            String pass = env.get("DB_PASS");
            conn = DriverManager.getConnection(url, id, pass);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
