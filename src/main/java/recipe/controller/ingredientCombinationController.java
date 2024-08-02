package recipe.controller;

import common.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipe.domain.Recipe;
import recipe.repository.RecipeSelectRepository;
import recipe.service.IngredientCombinationService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ingredientCombinationController implements Initializable {
    DBConnection dbConnection = new DBConnection();
    RecipeSelectRepository recipeSelectRepository = new RecipeSelectRepository(dbConnection);
    private final IngredientCombinationService ingredientCombinationService = new IngredientCombinationService(recipeSelectRepository);


    @FXML
    private ImageView backButton;

    @FXML
    private TextField searchField;


    @FXML
    private HBox searchIngredeintListHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBackButtonClick() throws IOException {
        //Parent mainRoot = FXMLLoader.load(getClass().getResource("fxml/RecipeReview.fxml"));
        Parent mainRoot = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(mainRoot));
    }

    @FXML
    public void handleSearch() {
        searchIngredeintListHBox.getChildren().clear();

        String query = searchField.getText();
        String[] ingredients = query.trim().split("\\s+");

        for(String ingredient : ingredients) {
            Text text = new Text(ingredient + "    ");
            text.setTextOrigin(javafx.geometry.VPos.TOP);
            text.setFont(new Font("Pretendard", 21.0));
            searchIngredeintListHBox.getChildren().add(text);
        }
        searchField.setText("");
        selectCombinationRecipeList(ingredients);
    }
    
    // 식재료 조합 레시피 조회
    public void selectCombinationRecipeList(String[] ingredients) {
        List<Recipe> recipeList = ingredientCombinationService.ingredientCombination(ingredients);

    }
}
