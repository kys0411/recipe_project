package user.service;

import common.UserSession;
import user.DBConnection;
import user.constant.Role;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    public void login(String nickname, String password) throws SQLException {

        User loggedInUser = authenticate(nickname, password);

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

    public User authenticate(String nickname, String password) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs;
        User user = null;

        try {
            String sql = "select * from MEMBER";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("nickname").trim().equals(nickname) && rs.getString("password").equals(password)) {
                    return User.builder()
                            .id(rs.getLong("member_id"))
                            .nickname(rs.getString("nickname"))
                            .role(Role.fromType(rs.getString("role")))
                            .hint(rs.getString("hint"))
                            .password(rs.getString("password"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return null;
    }
}
