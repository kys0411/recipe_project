package recipe.combination;

import common.DBConnection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import recipe.repository.RecipeSelectRepository;

import java.sql.SQLException;


public class RecipeCombinationTest {
    DBConnection dbConnection = new DBConnection();
    RecipeSelectRepository recipeSelectRepository = new RecipeSelectRepository(dbConnection);

    @Test
    @DisplayName("식재료 조합 레시피 테스트")
    public void IngredientUtilizationRecipeTest() throws SQLException, ClassNotFoundException {
        // Given
        String[] ingredientsArray = {"우유", "양파", "모짜렐라치즈"};
        // When, Then
        Assertions.assertThatNoException()
                .isThrownBy(() -> recipeSelectRepository.getIngredientCombinationRecipeInfo(ingredientsArray));
    }
}
