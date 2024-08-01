package recipe.repository;

import common.DBConnection;
import lombok.RequiredArgsConstructor;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import recipe.domain.Recipe;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	public void deleteById(Long id) throws SQLException, ClassNotFoundException {
		String sql1 = "DELETE FROM recipe_step WHERE recipe_id = ?";
		String sql2 = "DELETE FROM recipe_ingredient WHERE recipe_id = ?";
		String sql3 = "DELETE FROM likes WHERE recipe_id = ?";
		String sql4 = "DELETE FROM review WHERE recipe_id = ?";
		String sql5 = "DELETE FROM recipe WHERE recipe_id = ?";

		try (Connection conn = dbConnection.getConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
				 PreparedStatement stmt2 = conn.prepareStatement(sql2);
				 PreparedStatement stmt3 = conn.prepareStatement(sql3);
				 PreparedStatement stmt4 = conn.prepareStatement(sql4);
				 PreparedStatement stmt5 = conn.prepareStatement(sql5)) {

				stmt1.setInt(1, id.intValue());
				stmt2.setInt(1, id.intValue());
				stmt3.setInt(1, id.intValue());
				stmt4.setInt(1, id.intValue());
				stmt5.setInt(1, id.intValue());

				stmt1.executeUpdate();
				stmt2.executeUpdate();
				stmt3.executeUpdate();
				stmt4.executeUpdate();
				stmt5.executeUpdate();

				conn.commit();

			} catch (SQLException e) {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
