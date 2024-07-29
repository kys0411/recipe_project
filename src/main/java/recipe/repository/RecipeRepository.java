package recipe.repository;

import common.DBConnection;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static oracle.sql.ArrayDescriptor.createDescriptor;

@RequiredArgsConstructor
public class RecipeRepository {
    private final DBConnection dbConnection;

    public void insertRecipeInfo(Recipe recipe) throws SQLException, ClassNotFoundException {
        Connection conn = dbConnection.getConnection();

        ArrayDescriptor stepDesc = createDescriptor("STEP_TAB", conn);
        ARRAY steps = new ARRAY(stepDesc, conn, recipe.getSteps());

        ArrayDescriptor ingredientDesc = createDescriptor("INGREDIENT_TAB", conn);
        ARRAY ingredients = new ARRAY(ingredientDesc, conn, recipe.getIngredients());

        String plSql = "{ CALL create_full_recipe2(?, ?, ?, ?, ?, ?, ?, ?) }";
        CallableStatement cStmt = conn.prepareCall(plSql);

        cStmt.setInt(1, Math.toIntExact(recipe.getMemberId()));
        cStmt.setString(2, recipe.getCategory().getDescription());
        cStmt.setString(3, recipe.getTitle());
        cStmt.setString(4, recipe.getDifficulty().getDescription());
        cStmt.setString(5, recipe.getDescription());
        cStmt.setString(6, recipe.getQuantity());
        cStmt.setArray(7, steps);
        cStmt.setArray(8, ingredients);
        cStmt.execute();

        conn.close();
    }

    public List<Recipe> getIngredientCombinationRecipeInfo(String[] ingredientsArray) throws SQLException, ClassNotFoundException {
        List<Recipe> recipes = new ArrayList<>();
        Connection conn = dbConnection.getConnection();

        ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("INGREDIENT_ARRAY", conn);
        ARRAY ingredients = new ARRAY(descriptor, conn, ingredientsArray);

        String sql = "{? = call ingredient_combination(?)}";
        CallableStatement cStmt = conn.prepareCall(sql);

        cStmt.registerOutParameter(1, OracleTypes.CURSOR);
        cStmt.setArray(2, ingredients);
        cStmt.execute();

        ResultSet rs = (ResultSet) cStmt.getObject(1);

        while (rs.next()) {
            Recipe recipe = Recipe.builder()
                    .id(rs.getLong("recipe_id"))
                    .memberId(rs.getLong("member_id"))
                    .category(Category.fromDescription(rs.getString("category")))
                    .title(rs.getString("recipe_name"))
                    .description(rs.getString("description"))
                    .difficulty(Difficulty.fromDescription(rs.getString("difficulty")))
                    .quantity(rs.getString("quantity"))
                    .build();
            recipes.add(recipe);
        }

        return recipes;
    }
}
