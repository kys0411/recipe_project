package user.service;

import user.DBConnection;
import user.constant.Role;
import user.domain.User;
import user.dto.UserUpdateRequestDto;
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
            pstmt.setString(2, Role.NORMAL.getType());
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

    public User findUser(Long id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs;
        User user = null;

        try {
            String sql = "select * from MEMBER where MEMBER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = User.builder()
                        .id(rs.getLong("member_id"))
                        .nickname(rs.getString("nickname"))
                        .role(Role.fromType(rs.getString("role")))
                        .hint(rs.getString("hint"))
                        .password(rs.getString("password"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        
        return user;
    }

    public void update(Long id, UserUpdateRequestDto userUpdateRequestDto) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        User user = null;

        try {
            String sql = """
                         update MEMBER
                         set NICKNAME = ?,
                         hint = ?,
                         PASSWORD = ?
                         where MEMBER_ID = ?   
                            """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userUpdateRequestDto.getNickname().trim());
            pstmt.setString(2, userUpdateRequestDto.getHint());
            pstmt.setString(3, userUpdateRequestDto.getPassword());
            pstmt.setLong(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
    }
}
