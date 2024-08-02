package recipe.controller;

import common.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.NoArgsConstructor;
import recipe.dto.RecipeDto;
import recipe.repository.RecipeQueryRepository;
import recipe.service.FindRecipeService;
import recipe.service.GetAllRecipesService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor(force = true)
public class GetAllRecipesController implements Initializable {
    @FXML
    private VBox recipeContainer;

    @FXML
    private ChoiceBox<String> conditionBox;

    @FXML
    private TextField searchField;

    private final GetAllRecipesService recipesService = new GetAllRecipesService(new RecipeQueryRepository(new DBConnection()));
    private final FindRecipeService findRecipeService = new FindRecipeService(new RecipeQueryRepository(new DBConnection()));

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conditionBox.setValue("좋아요순");
        conditionBox.getItems().addAll("좋아요순", "리뷰순", "별점순");

        conditionBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            handleChangeCondition(newValue);
                        });


        List<RecipeDto.FindAll> recipes = recipesService.getAll(null);
        addRecipesToRecipeContainer(recipes);
    }

    private void handleChangeCondition(String condition) {
        clearRecipeContainer();

        List<RecipeDto.FindAll> recipes = recipesService.getAll(condition);
        addRecipesToRecipeContainer(recipes);
    }

    @FXML
    private void handleSearchRecipe() {
        String keyword = searchField.getText();

        List<RecipeDto.FindAll> findRecipes = findRecipeService.getAllByKeyword(keyword);

        clearRecipeContainer();
        addRecipesToRecipeContainer(findRecipes);
    }

    private void addRecipesToRecipeContainer(List<RecipeDto.FindAll> recipes) {
        for (RecipeDto.FindAll recipe : recipes) {
            HBox hBox = new HBox();
            Label label = new Label();
            label.setText(recipe.getTitle());
            hBox.getChildren().add(label);

            recipeContainer.getChildren().add(hBox);
        }
    }

    private void clearRecipeContainer() {
        if (recipeContainer.getChildren().size() > 1) {
            recipeContainer.getChildren()
                    .subList(1, recipeContainer.getChildren().size())
                    .clear();
        }
    }
}
