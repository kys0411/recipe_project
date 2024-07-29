package recipe.service;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;

import java.sql.*;
import java.util.*;

public class IngredientCombinationService {
    public List<Recipe> getIngredientCombinationRecipes(String[] ingredientsArray, Connection conn) throws SQLException {
        List<Recipe> recipes = new ArrayList<>();

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
