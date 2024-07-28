package recipe.service;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;

import java.sql.*;
import java.util.ArrayList;

public class RecipeService {

    public void getCreateTestResult(Recipe recipe, Object[][] stepsData, Object[][] ingredientsData, Connection conn) throws SQLException {
        ArrayDescriptor stepDesc = ArrayDescriptor.createDescriptor("STEP_TAB", conn);
        ARRAY stepsArray = new ARRAY(stepDesc, conn, stepsData);

        ArrayDescriptor ingredientDesc = ArrayDescriptor.createDescriptor("INGREDIENT_TAB", conn);
        ARRAY ingredientsArray = new ARRAY(ingredientDesc, conn, ingredientsData);

        String plSql = "{CALL create_full_recipe2(?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cStmt = conn.prepareCall(plSql);

        cStmt.setInt(1, Math.toIntExact(recipe.getMemberId()));
        cStmt.setString(2, recipe.getCategory().getDescription());
        cStmt.setString(3, recipe.getTitle());
        cStmt.setString(4, recipe.getDifficulty().getDescription());
        cStmt.setString(5, recipe.getDescription());
        cStmt.setString(6, recipe.getQuantity());
        cStmt.setArray(7, stepsArray);
        cStmt.setArray(8, ingredientsArray);
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

    public ArrayList<Recipe> selectAllRecipe(Connection conn) {
        Recipe recipe = null;
        ArrayList<Recipe> recipes = new ArrayList<>();
        String selectSQL = "SELECT * FROM recipe";

        try(PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                recipe = Recipe.builder()
                        .id(rs.getLong("recipe_id"))
                        .memberId(rs.getLong("member_id"))
                        .category(Category.fromDescription(rs.getString("category")))
                        .title(rs.getString("recipe_name"))
                        .description(rs.getString("description"))
                        .difficulty(Difficulty.fromDescription(rs.getString("difficulty")))
                        .quantity(rs.getString("quantity"))
                        .build();

                recipes.add(recipe);    // create Recipe object and add to recipes
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }
}
