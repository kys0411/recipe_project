package recipe.repository;

import common.DBConnection;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;

public class RecipeRepositoryTest {
    DBConnection dbConnection = new DBConnection();
    RecipeRepository recipeRepository = new RecipeRepository(dbConnection);

    @Test
    @DisplayName("레시피 생성 성공 테스트 - 레시피 생성 로직 실행 시 예외가 발생하지 않는다.")
    public void insert_Recipe_Data_Success() {
        // given
        Recipe recipe = Recipe.builder()
                .memberId(1L)
                .title("티라미수 케이크")
                .description("커피맛이 나는 케이크")
                .category(Category.APPETIZER)
                .quantity("2인분")
                .difficulty(Difficulty.NORMAL)
                .steps(new Object[][]{
                        {1, "닭을 물에 넣고 끓인다."},
                        {2, "끓어 오르면 월계수 잎과 통후추, 소금을 넣고 15분 더 끓인다."},
                        {3, "닭을 건져내고 남은 육수에 찹쌀과 파, 양파를 넣고 10 - 15분 더 끓인다."},
                        {4, "맛있는 백숙과 닭죽 완성!"}
                })
                .ingredients(new Object[][]{
                        {"생닭11호", "1마리"},
                        {"소금", "2꼬집"},
                        {"대파", "1단"},
                        {"월계수잎", "3장"},
                        {"통후추", "15알"}
                })
                .build();

        // when, then
        Assertions.assertThatNoException()
                .isThrownBy(() -> recipeRepository.insertRecipeInfo(recipe));
    }
}
