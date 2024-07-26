import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.System.getenv;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class JDBCTest {
    static Connection conn;

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv();

        Class.forName("oracle.jdbc.OracleDriver");
        String url = env.get("DB_URL");
        String id = env.get("DB_ID");
        String pass = env.get("DB_PASS");
        conn = DriverManager.getConnection(url, id, pass);

        return conn;
    }

    @Test
    @DisplayName("JDBC 연결 테스트로 JDBC가 연결이 되면 통과한다.")
    public void jdbcConnection() {
        assertThatCode( () -> conn = getConnection()
        ).doesNotThrowAnyException();
    }
}
