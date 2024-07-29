package recipe.service;

import lombok.RequiredArgsConstructor;
import recipe.domain.Recipe;
import recipe.repository.RecipeQueryRepository;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class FindRecipeService {
    private final RecipeQueryRepository recipeQueryRepository;

    public Recipe getOne(Long id) throws SQLException, ClassNotFoundException {
        return recipeQueryRepository.findRecipeById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 레시피가 존재하지 않습니다."));
    }
}
