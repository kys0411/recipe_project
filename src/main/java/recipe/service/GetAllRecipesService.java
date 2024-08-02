package recipe.service;

import lombok.RequiredArgsConstructor;
import recipe.dto.RecipeDto;
import recipe.repository.RecipeQueryRepository;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class GetAllRecipesService {
    private static final String DEFAULT_CONDITION = "좋아요순";
    private final RecipeQueryRepository repository;

    public List<RecipeDto.FindAll> getAll(String condition) {
        if (condition == null) {
            condition = DEFAULT_CONDITION;
        }

        try {
            return repository.findAllSortByCondition(condition);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
