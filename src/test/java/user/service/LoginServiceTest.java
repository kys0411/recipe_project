package user.service;

import common.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


class LoginServiceTest {

    LoginService loginService = new LoginService();

    @Test
    @DisplayName("로그인 성공테스트")
    void loginSuccess() throws SQLException {
        loginService.login("hjlee", "1234");

        assertThat(UserSession.getInstance().getLoggedUser()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("로그인 아이디, 비밀번호 다르게 입력할 경우 실패테스트")
    void loginFail() throws SQLException {
        loginService.login("hjlee", "12345");

        assertThat(UserSession.getInstance().getLoggedUser()).isEqualTo(null);
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logout() throws SQLException {
        loginService.login("hjlee", "1234");
        loginService.logout();

        assertThat(UserSession.getInstance().getLoggedUser()).isEqualTo(null);
    }
}