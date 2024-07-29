package recipe.combination;

import common.DBConnection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;
import recipe.repository.RecipeSelectRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RecipeCombinationTest {
    DBConnection dbConnection = new DBConnection();
    RecipeSelectRepository recipeSelectRepository = new RecipeSelectRepository(dbConnection);

    @Test
    @DisplayName("식재료 조합 레시피 테스트")
    public void IngredientUtilizationRecipeTest() throws SQLException, ClassNotFoundException {
        // Given
        String[] ingredientsArray = {"테스트재료1"};
        List<Recipe> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(Recipe.builder()
                        .id(109L)
                        .memberId(22L)
                        .category(Category.APPETIZER)
                        .title("테스트 레시피")
                        .description("테스트 레시피")
                        .difficulty(Difficulty.DIFFICULT)
                        .quantity("1인분")
                        .build()
        );

        // When
        List<Recipe> recipes = recipeSelectRepository.getIngredientCombinationRecipeInfo(ingredientsArray);

        // Then
        Assertions.assertThat(recipes)
                .isNotNull()
                .hasSize(expectedRecipes.size());
   }
}
