package user.service;

import user.DBConnection;
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
            pstmt.setString(2, "1");
            pstmt.setString(3, userjoinRequestDto.getHint());
            pstmt.setString(4, userjoinRequestDto.getPassword());

//            pstmt.setString(1, "qwe");
//            pstmt.setString(2, "1");
//            pstmt.setString(3, "aaaaaaa");
//            pstmt.setString(4, "7878");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            conn.close();
            pstmt.close();
        }
    }
}
