package recipe.repository;

import common.DBConnection;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import recipe.domain.Recipe;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeQueryRepositoryTest {
    DBConnection dbConnection = new DBConnection();
    RecipeQueryRepository recipeQueryRepository = new RecipeQueryRepository(dbConnection);

    @Test
    @DisplayName("레시피 상세 조회 성공 테스트 - 레시피 아이디로 조회한 결과가 기대값과 같아야 한다.")
    public void find_Recipe_By_Id_Success() throws SQLException, ClassNotFoundException {
        // when
        Recipe result = recipeQueryRepository.findRecipeById(85L).get();

        // then
        assertThat(result.getId()).isEqualTo(85L);
        assertThat(result.getSteps()).isEqualTo(new Object[][]{
                {1, "닭을 물에 넣고 끓인다."},
                {2, "끓어 오르면 월계수 잎과 통후추, 소금을 넣고 15분 더 끓인다."},
                {3, "닭을 건져내고 남은 육수에 찹쌀과 파, 양파를 넣고 10 - 15분 더 끓인다."},
                {4, "맛있는 백숙과 닭죽 완성!"}
        });
        assertThat(result.getIngredients()).isEqualTo(new Object[][]{
                {"생닭11호", "1마리"},
                {"소금", "2꼬집"},
                {"대파", "1단"},
                {"월계수잎", "3장"},
                {"통후추", "15알"}
        });
    }
}
