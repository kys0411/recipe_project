package recipe.service;

import lombok.RequiredArgsConstructor;
import recipe.domain.Recipe;
import recipe.dto.RecipeDto;
import recipe.repository.RecipeQueryRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class FindRecipeService {
    private final RecipeQueryRepository recipeQueryRepository;

    public Recipe getOne(Long id) throws SQLException, ClassNotFoundException {
        return recipeQueryRepository.findRecipeById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 레시피가 존재하지 않습니다."));
    }

    public List<RecipeDto.FindAll> getAllByKeyword(String keyword) {
        try {
            return recipeQueryRepository.findRecipeByKeyword(keyword);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
