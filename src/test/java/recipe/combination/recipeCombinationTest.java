package recipe.combination;

import oracle.jdbc.driver.OracleDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import recipe.domain.Recipe;
import recipe.service.IngredientUtilizationRecipeService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.lang.System.getenv;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class recipeCombinationTest {
    private static Connection conn;
    private IngredientUtilizationRecipeService ingService;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        conn = getConnection();
        conn.setAutoCommit(false);
        ingService = new IngredientUtilizationRecipeService();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    @DisplayName("식재료 조합 레시피 테스트")
    public void IngredientUtilizationRecipeTest() throws SQLException, ClassNotFoundException {
        // Given
        String[] ingredientsArray = {"우유", "양파", "모짜렐라치즈"};
        // When
        List<Recipe> recipes = ingService.getIngredientUtilizationRecipes(ingredientsArray, conn);
        // Then
        assertThat(recipes).isNotNull();
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
