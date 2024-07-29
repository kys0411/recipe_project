package user.service;

import oracle.jdbc.driver.OracleDriver;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import user.dto.UserjoinRequestDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.System.getenv;

class UserServiceTest {
    private static Connection conn;
    private UserService userService;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        conn = getConnection();
        conn.setAutoCommit(false);
        userService = new UserService();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    @DisplayName("회원가입 성공테스트")
    void join() throws SQLException {
        UserjoinRequestDto userjoinRequestDto = UserjoinRequestDto.builder().
                nickname("qqqqqqq")
                .hint("dlgywn hint")
                .password("12345678")
                .build();

        UserService userService = new UserService();

        Assertions.assertThatNoException()
                .isThrownBy(() -> userService.join(userjoinRequestDto));
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv();
        OracleDriver driver = new OracleDriver();
        DriverManager.registerDriver(driver);

        Class.forName("oracle.jdbc.OracleDriver");
        String url = env.get("DB_URL");
        String id = env.get("DB_ID");
        String pass = env.get("DB_PASS");
        return DriverManager.getConnection(url, id, pass);
    }
}