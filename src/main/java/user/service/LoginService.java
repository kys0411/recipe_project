package user.service;

import common.UserSession;
import user.domain.User;
import user.repository.LoginRepository;

import java.sql.SQLException;

public class LoginService {

    LoginRepository loginRepository = new LoginRepository();

    public void login(String nickname, String password) throws SQLException {

        User loggedInUser = loginRepository.authenticate(nickname, password);

        if (loggedInUser != null) {
            UserSession.getInstance().setLoggedUser(loggedInUser);
            System.out.println("User logged in: " + loggedInUser.getNickname());
        } else {
            System.out.println("Invalid username or password");
        }
    }

    public void logout() {
        UserSession.getInstance().clearSession();
        System.out.println("User logged out");
    }
}
