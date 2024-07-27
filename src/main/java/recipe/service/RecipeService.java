package recipe.service;

import recipe.domain.Recipe;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecipeService {

    public void getCreateTestResult(Recipe recipe, Object[][] stepsData, Object[][] ingredientsData, Connection conn) throws SQLException {
        insertDataInfoTemporaryTable(conn, "temp_recipe_steps", stepsData);
        insertDataInfoTemporaryTable(conn, "temp_ing_msm", ingredientsData);

        String plSql =
                "DECLARE " +
                        "   p_steps SYS_REFCURSOR; " +
                        "   p_ing_msm SYS_REFCURSOR; " +
                        "BEGIN " +
                        "   OPEN p_steps FOR SELECT * FROM temp_recipe_steps; " +
                        "   OPEN p_ing_msm FOR SELECT * FROM temp_ing_msm; " +
                        "   create_full_recipe(?, ?, ?, ?, ?, ?, p_steps, p_ing_msm); " +
                        "END;";

        CallableStatement cStmt = conn.prepareCall(plSql);

        cStmt.setInt(1, Math.toIntExact(recipe.getMemberId()));
        cStmt.setString(2, recipe.getCategory().getDescription());
        cStmt.setString(3, recipe.getTitle());
        cStmt.setString(4, recipe.getDifficulty().getDescription());
        cStmt.setString(5, recipe.getDescription());
        cStmt.setString(6, recipe.getQuantity());
        cStmt.execute();
    }

    private static void insertDataInfoTemporaryTable(Connection conn, String tableName, Object[][] data) throws SQLException {
        String insertSQL = "INSERT INTO " + tableName + " VALUES (?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            for (Object[] row : data) {
                for(int i = 0; i < row.length; i++) {
                    pstmt.setObject(i + 1, row[i]);
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}
