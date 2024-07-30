package like.repository;

import lombok.RequiredArgsConstructor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;
import recipe.repository.RecipeQueryRepository;
import common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LikeQueryRepository {
    private final DBConnection dbConnection;
    private final RecipeQueryRepository recipeQueryRepository;

    public List<Recipe> findByLikeRecipe(Long userId) throws SQLException, ClassNotFoundException {
        List<Recipe> resultRecipes = new ArrayList<>();
        Connection conn = dbConnection.getConnection();

        String selectSQL = "SELECT * " +
                "FROM likes l JOIN recipe r ON (l.recipe_id = r.recipe_id) " +
                "WHERE l.member_id = ?";

        PreparedStatement pStmt = conn.prepareStatement(selectSQL);
        pStmt.setLong(1, userId);
        ResultSet rs = pStmt.executeQuery();

        while(rs.next()) {
            Recipe recipe = Recipe.builder()
                    .id(rs.getLong("recipe_id"))
                    .memberId(rs.getLong("member_id"))
                    .category(Category.fromDescription(rs.getString("category")))
                    .title(rs.getString("recipe_name"))
                    .description(rs.getString("description"))
                    .steps(recipeQueryRepository.getRecipeSteps(conn, rs.getLong("recipe_id")))
                    .ingredients(recipeQueryRepository.getIngredients(conn, rs.getLong("recipe_id")))
                    .difficulty(Difficulty.fromDescription(rs.getString("difficulty")))
                    .quantity(rs.getString("quantity"))
                    .build();

            resultRecipes.add(recipe);
        }

        return resultRecipes;
    }
}
