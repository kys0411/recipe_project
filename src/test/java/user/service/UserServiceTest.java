package user.service;

import oracle.jdbc.driver.OracleDriver;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import user.constant.Role;
import user.domain.User;
import user.dto.UserjoinRequestDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.System.getenv;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("회원 조회테스트")
    void findUserTest() throws SQLException {
        UserService userService = new UserService();
        User userA = userService.findUser(8L);

        assertThat(userA.getId()).isEqualTo(8L);
        assertThat(userA.getNickname().trim()).isEqualTo("hjlee");
        assertThat(userA.getRole()).isEqualTo(Role.NORMAL);
        assertThat(userA.getHint()).isEqualTo("효주");
        assertThat(userA.getPassword()).isEqualTo("1234");
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