package recipe.repository;

import common.DBConnection;
import lombok.RequiredArgsConstructor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;
import recipe.dto.RecipeDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RequiredArgsConstructor
public class RecipeQueryRepository {
    private static final int NUMBER_OF_INDEX = 2;

    private final DBConnection dbConnection;

    public Optional<Recipe> findRecipeById(Long id) throws SQLException, ClassNotFoundException {
        Recipe resultRecipe = null;
        Connection conn = dbConnection.getConnection();

        String sql = "SELECT * FROM recipe WHERE recipe_id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            resultRecipe = Recipe.builder()
                    .id(rs.getLong("recipe_id"))
                    .memberId(rs.getLong("member_id"))
                    .category(Category.fromDescription(rs.getString("category")))
                    .title(rs.getString("recipe_name"))
                    .description(rs.getString("description"))
                    .steps(getRecipeSteps(conn, id))
                    .ingredients(getIngredients(conn, id))
                    .difficulty(Difficulty.fromDescription(rs.getString("difficulty")))
                    .quantity(rs.getString("quantity"))
                    .createdAt(rs.getTimestamp("creation_date").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("update_date").toLocalDateTime())
                    .build();
        }

        return Optional.ofNullable(resultRecipe);
    }

    public Object[][] getRecipeSteps(Connection conn, Long id) throws SQLException {
        String sql = "SELECT s.step_number, s.step_description FROM recipe_step s WHERE s.recipe_id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();

        rs.last();
        int row = rs.getRow();
        rs.beforeFirst();

        Object[][] recipeSteps = new Object[row][NUMBER_OF_INDEX];
        int i = 0;

        while(rs.next()) {
            recipeSteps[i][0] = rs.getInt("step_number");
            recipeSteps[i++][1] = rs.getString("step_description");
        }

        return recipeSteps;
    }

    public Object[][] getIngredients(Connection conn, Long id) throws SQLException {
        String sql = """
                 SELECT i.ingredient_id, i.ingredient_name, r_i.measurement
                 FROM recipe_ingredient r_i JOIN ingredient i
                 ON r_i.ingredient_id = i.ingredient_id
                 WHERE r_i.recipe_id = ?
                 ORDER BY recipeingredient_id
                 """;

        PreparedStatement pstmt = conn.prepareStatement(sql, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();

        rs.last();
        int row = rs.getRow();
        rs.beforeFirst();

        Object[][] ingredients = new Object[row][NUMBER_OF_INDEX];
        int i = 0;

        while(rs.next()) {
            ingredients[i][0] = rs.getString("ingredient_name");
            ingredients[i++][1] = rs.getString("measurement");
        }

        return ingredients;
    }

    public List<RecipeDto.FindAll> findAllSortByCondition(String cond) throws SQLException, ClassNotFoundException {
        List<RecipeDto.FindAll> recipes = new ArrayList<>();
        Connection conn = dbConnection.getConnection();

        String sql = """
                SELECT
                    r.recipe_id,
                    r.recipe_name,
                    r.description,
                    r.difficulty,
                    r.creation_date,
                    COALESCE(l.like_count, 0) AS LIKES,
                    COALESCE(v.review_count, 0) AS reviews,
                    COALESCE(v.avg_rating, 0) AS ratings
                FROM
                    recipe r
                LEFT JOIN (SELECT recipe_id, status, COUNT(*) AS like_count FROM likes GROUP BY recipe_id, status) l
                ON r.recipe_id = l.recipe_id and l.status = 1
                LEFT JOIN (SELECT recipe_id, COUNT(*) AS review_count, AVG(rating) avg_rating FROM review GROUP BY recipe_id) v
                ON r.recipe_id = v.recipe_id
                """;
        sql = sql + "ORDER BY " + Condition.getCondition(cond) + " desc";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            RecipeDto.FindAll recipe = RecipeDto.FindAll.builder()
                    .id(rs.getLong("recipe_id"))
                    .title(rs.getString("recipe_name"))
                    .description(rs.getString("description"))
                    .difficulty(rs.getString("difficulty"))
                    .likes(rs.getInt("likes"))
                    .reviews(rs.getInt("reviews"))
                    .rating(rs.getInt("ratings"))
                    .createdAt(rs.getDate("creation_date").toString())
                    .build();
            recipes.add(recipe);
        }

        conn.close();
        pstmt.close();
        rs.close();

        return recipes;
    }

    public List<RecipeDto.FindAll> findRecipeByKeyword(String keyword) throws SQLException, ClassNotFoundException {
        List<RecipeDto.FindAll> recipes = new ArrayList<>();
        Connection conn = dbConnection.getConnection();

        String sql = "SELECT * FROM recipe WHERE recipe_name LIKE ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + keyword + "%");

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            RecipeDto.FindAll recipe = RecipeDto.FindAll.builder()
                    .id(rs.getLong("recipe_id"))
                    .title(rs.getString("recipe_name"))
                    .difficulty(rs.getString("difficulty"))
                    .likes(rs.getInt("likes"))
                    .reviews(rs.getInt("reviews"))
                    .rating(rs.getInt("rating"))
                    .createdAt(rs.getDate("creation_date").toString())
                    .build();
            recipes.add(recipe);
        }

        conn.close();
        pstmt.close();
        rs.close();

        return recipes;
    }
}
