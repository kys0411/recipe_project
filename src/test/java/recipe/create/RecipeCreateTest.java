package recipe.create;

import oracle.jdbc.driver.OracleDriver;
import org.junit.jupiter.api.*;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;
import recipe.service.RecipeService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.System.getenv;

public class RecipeCreateTest {
    private static Connection conn;
    private RecipeService recipeService;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        conn = getConnection();
        conn.setAutoCommit(false);
        recipeService = new RecipeService();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    @DisplayName("레시피 생성 테스트")
    public void recipeCreateTest() throws SQLException {
        Object[][] stepsData = {
                {1, "닭을 물에 넣고 끓인다."},
                {2, "끓어 오르면 월계수 잎과 통후추, 소금을 넣고 15분 더 끓인다."},
                {3, "닭을 건져내고 남은 육수에 찹쌀과 파, 양파를 넣고 10 - 15분 더 끓인다."},
                {4, "맛있는 백숙과 닭죽 완성!"}
        };

        Object[][] ingredientsData = {
                {"생닭11호", "1마리"},
                {"소금", "2꼬집"},
                {"대파", "1단"},
                {"월계수잎", "3장"},
                {"통후추", "15알"}
        };

        Recipe recipe = new Recipe(302L, 11L, Category.APPETIZER, "백숙", "닭을 끓여 요리한 음식", null, Difficulty.EASY, "2인분");
        recipeService.getCreateTestResult(recipe, stepsData, ingredientsData, conn);
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv();
        OracleDriver driver = new OracleDriver();
        DriverManager.registerDriver(driver);

        Class.forName("oracle.jdbc.OracleDriver");
        String url = env.get("DB_URL");
        String id = env.get("DB_ID");
        String pass = env.get("DB_PASS");
        return DriverManager.getConnection(url, id, pass);
    }
}
