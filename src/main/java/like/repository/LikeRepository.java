package like.repository;

import common.DBConnection;
import like.constant.Status;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.internal.OracleTypes;

import java.sql.*;

@RequiredArgsConstructor
public class LikeRepository {
    private final DBConnection dbConnection;

    public Status upsertLikeInfo(Long userId, Long recipeId) throws SQLException, ClassNotFoundException {
        Connection conn = dbConnection.getConnection();
        String plSql = "{ call upsert_member_likes(?, ?, ?)}";
        Status status;
        CallableStatement cStmt = conn.prepareCall(plSql);

        cStmt.setLong(1, userId);
        cStmt.setLong(2, recipeId);
        cStmt.registerOutParameter(3, OracleTypes.VARCHAR);
        cStmt.execute();
        status =  Status.fromDescription(cStmt.getString(3));

        conn.close();

        return status;
    }
}
