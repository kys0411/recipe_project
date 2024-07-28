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
            System.out.println("드라이버 로드 성공");

            String url = env.get("DB_URL");
            String id = env.get("DB_ID");
            String pass = env.get("DB_PASS");
            conn = DriverManager.getConnection(url, id, pass);
            System.out.println("db연결 성공");

            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
