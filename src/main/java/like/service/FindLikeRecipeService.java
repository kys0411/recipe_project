package like.service;

import like.repository.LikeQueryRepository;
import lombok.RequiredArgsConstructor;
import recipe.domain.Recipe;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class FindLikeRecipeService {
    private final LikeQueryRepository likeQueryRepository;

    public List<Recipe> select(Long userId) throws SQLException, ClassNotFoundException {
        return likeQueryRepository.findByLikeRecipe(userId);
    }
}
