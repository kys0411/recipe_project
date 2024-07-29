package recipe.repository;

import common.DBConnection;
import lombok.RequiredArgsConstructor;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import recipe.domain.Recipe;

import java.sql.*;

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
}
