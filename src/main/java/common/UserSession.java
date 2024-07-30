package common;

import lombok.Getter;
import user.domain.User;

@Getter
public class UserSession {
    private static UserSession instance;

    private User loggedUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void clearSession() {
        loggedUser = null;
    }
}
