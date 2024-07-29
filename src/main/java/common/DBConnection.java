package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.System.getenv;

public class DBConnection {
    private static final Map<String, String> env = getenv();
    private static final String DRIVER_NAME = "oracle.jdbc.OracleDriver";
    private static final String DB_URL = env.get("DB_URL");
    private static final String DB_ID = env.get("DB_ID");
    private static final String DB_PW = env.get("DB_PASS");

    private Connection conn;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);

        return conn;
    }
}
