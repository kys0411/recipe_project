package recipe.service;

import lombok.RequiredArgsConstructor;
import recipe.domain.Recipe;
import recipe.repository.RecipeRepository;

import java.sql.SQLException;

@RequiredArgsConstructor
public class CreateRecipeService {
    private final RecipeRepository recipeRepository;

    public void create(Recipe recipe) {
        try {
            recipeRepository.insertRecipeInfo(recipe);
        } catch (SQLException | ClassNotFoundException e) {
            // todo: 예외 로직 처리
        }
    }
}
