package recipe.controller;

import common.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import recipe.dto.RecipeDto;
import recipe.repository.RecipeQueryRepository;
import recipe.service.FindRecipeService;
import recipe.service.GetAllRecipesService;


import java.io.IOException;
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
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            VBox imageBox = new VBox();
            String relativePath = "defaultRecipeImage.jpg";

            Image image = new Image(relativePath);
            ImageView recipeImage = new ImageView(image);
            recipeImage.setFitWidth(130);
            recipeImage.setFitHeight(130);

            imageBox.getChildren().add(recipeImage);

            VBox contentBox = new VBox();

            HBox headerBox = new HBox();
            Label title = new Label();
            title.setText(recipe.getTitle());
            title.setStyle("-fx-font-size: 20px; -fx-cursor: hand;");
            title.setOnMouseClicked(event -> {
                try {
                    switchToRecipeDetail(recipe.getId(), event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Label createDate = new Label();
            createDate.setText(recipe.getCreatedAt());

            headerBox.getChildren().addAll(title, createDate);
            HBox.setMargin(createDate, new Insets(0, 0, 0, 200));

            Label description = new Label();
            description.setText(recipe.getDescription());

            HBox footerBox = new HBox();
            Label difficulty = new Label();
            difficulty.setText("난이도 : " + recipe.getDifficulty());
            Label reviews = new Label();
            reviews.setText("후기 : " + recipe.getReviews() + "개 " + "(" + recipe.getRating() + ")");
            Label likes = new Label();
            likes.setText(recipe.getLikes() +  " likes");

            footerBox.getChildren().addAll(difficulty, reviews, likes);
            HBox.setMargin(difficulty, new Insets(0, 50, 0, 0));
            HBox.setMargin(reviews, new Insets(0, 50, 0, 0));

            contentBox.getChildren().addAll(headerBox, description, footerBox);
            VBox.setMargin(headerBox, new Insets(15, 0, 0, 0));
            VBox.setMargin(description, new Insets(20, 0, 20, 0));

            hBox.getChildren().addAll(imageBox, contentBox);
            HBox.setMargin(contentBox, new Insets(0, 0, 0, 20));
            vBox.getChildren().add(hBox);
            VBox.setMargin(hBox, new Insets(20, 0, 0, 0));

            recipeContainer.getChildren().add(vBox);
            VBox.setMargin(vBox, new Insets(10, 0, 0, 20));
        }
    }

    private void clearRecipeContainer() {
        if (recipeContainer.getChildren().size() > 1) {
            recipeContainer.getChildren()
                    .subList(1, recipeContainer.getChildren().size())
                    .clear();
        }
    }

    private void switchToRecipeDetail(Long id, MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recipeDetail.fxml"));
            loader.setControllerFactory(param -> new RecipeController(id));
            Parent root = loader.load();

            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
