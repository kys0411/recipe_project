package recipe.repository;

import common.DBConnection;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RecipeSelectRepository {
    private final DBConnection dbConnection;

    public List<Recipe> selectAllRecipe() throws SQLException, ClassNotFoundException {
        Connection conn = dbConnection.getConnection();

        String selectSQL = "SELECT * FROM recipe";

        PreparedStatement pStmt = conn.prepareStatement(selectSQL);
        ResultSet rs = pStmt.executeQuery();

        return returnRecipeListInfo(rs);
    }

    public List<Recipe> getIngredientCombinationRecipeInfo(String[] ingredientsArray) throws SQLException, ClassNotFoundException {
        Connection conn = dbConnection.getConnection();

        ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("INGREDIENT_ARRAY", conn);
        ARRAY ingredients = new ARRAY(descriptor, conn, ingredientsArray);

        String sql = "{? = call ingredient_combination(?)}";

        CallableStatement cStmt = conn.prepareCall(sql);
        cStmt.registerOutParameter(1, OracleTypes.CURSOR);
        cStmt.setArray(2, ingredients);
        cStmt.execute();

        ResultSet rs = (ResultSet) cStmt.getObject(1);

        return returnRecipeListInfo(rs);
    }

    public List<Recipe> returnRecipeListInfo(ResultSet rs) {
        List<Recipe> recipes = new ArrayList<>();

        try {
            while (rs.next()) {
                Recipe recipe = Recipe.builder()
                        .id(rs.getLong("recipe_id"))
                        .memberId(rs.getLong("member_id"))
                        .category(Category.fromDescription(rs.getString("category")))
                        .title(rs.getString("recipe_name"))
                        .description(rs.getString("description"))
                        .difficulty(Difficulty.fromDescription(rs.getString("difficulty")))
                        .quantity(rs.getString("quantity"))
                        .createdAt(rs.getTimestamp("creation_date").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("update_date").toLocalDateTime())
                        .build();
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
