package recipe.repository.like;

import common.DBConnection;
import like.repository.LikeQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import recipe.domain.Recipe;
import recipe.repository.RecipeQueryRepository;

import java.sql.SQLException;
import java.util.List;

public class LikeQueryRepositoryTest {
    DBConnection dbConnection = new DBConnection();
    RecipeQueryRepository recipeIndexRepository = new RecipeQueryRepository(dbConnection);
    LikeQueryRepository likeQueryRepository = new LikeQueryRepository(dbConnection, recipeIndexRepository);

    @Test
    @DisplayName("좋아요 한 목록 조회 테스트 - 조회되는 목록 개수가 테스트시 설정한 개수와 맞아야 한다.")
    public void like_Recipe_List() throws SQLException, ClassNotFoundException {
        // given
        List<Recipe> recipes = likeQueryRepository.findByLikeRecipe(19L);

        // when, then
        Assertions.assertThat(recipes)
                .isNotEmpty()
                .hasSize(5);
    }
}
