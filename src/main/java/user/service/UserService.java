package user.service;

import user.DBConnection;
import user.constant.Role;
import user.domain.User;
import user.dto.UserjoinRequestDto;

import java.sql.*;

public class UserService {

    public void join(UserjoinRequestDto userjoinRequestDto) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into member(member_id, nickname, role, hint, password) values(SEQ_MEMBER_ID.nextval, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userjoinRequestDto.getNickname());
            pstmt.setString(2, Role.NORMAL.getRoleType());
            pstmt.setString(3, userjoinRequestDto.getHint());
            pstmt.setString(4, userjoinRequestDto.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
    }
}
