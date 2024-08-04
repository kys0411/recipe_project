package recipe.controller;

import common.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipe.domain.Recipe;
import recipe.repository.RecipeSelectRepository;
import recipe.service.IngredientCombinationService;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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

    @FXML
    private VBox recipeContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBackButtonClick(MouseEvent event) throws IOException {
        Parent mainRoot = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
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
        recipeContainer.getChildren().clear();

        List<Recipe> recipeList = ingredientCombinationService.ingredientCombination(ingredients);

        for (Recipe recipe : recipeList) {
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            VBox imageBox = new VBox();
            String relativePath = "images/defaultRecipeImage.jpg";

            Image image = new Image(relativePath);
            ImageView recipeImage = new ImageView(image);
            recipeImage.setFitWidth(130);
            recipeImage.setFitHeight(130);

            imageBox.getChildren().add(recipeImage);

            VBox contentBox = new VBox();

            HBox headerBox = new HBox();
            Label title = new Label();
            title.setText(recipe.getTitle());
            title.setStyle("-fx-font-size: 20px; -fx-padding: 0 14px 2px 0;");


            Label createDate = new Label();
            createDate.setText(recipe.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            headerBox.getChildren().addAll(title, createDate);
            HBox.setMargin(createDate, new Insets(0, 0, 0, 0));

            Label description = new Label();
            description.setText(recipe.getDescription());

            HBox footerBox = new HBox();
            Label difficulty = new Label();
            difficulty.setText("난이도 : " + recipe.getDifficulty());
            Label reviews = new Label();
            reviews.setText("후기 : " + "10" + "개 " + "(" + 2.5 + ")");
            Label likes = new Label();
            likes.setText(5 +  " likes");

            footerBox.getChildren().addAll(difficulty, reviews, likes);
            HBox.setMargin(difficulty, new Insets(0, 50, 0, 0));
            HBox.setMargin(reviews, new Insets(0, 50, 0, 0));

            contentBox.getChildren().addAll(headerBox, description, footerBox);
            VBox.setMargin(headerBox, new Insets(15, 0, 0, 0));
            VBox.setMargin(description, new Insets(20, 0, 20, 0));

            hBox.getChildren().addAll(imageBox, contentBox);
            HBox.setMargin(contentBox, new Insets(0, 0, 0, 20));
            vBox.getChildren().add(hBox);
            VBox.setMargin(hBox, new Insets(10, 0, 0, 0));

            recipeContainer.getChildren().add(vBox);
            VBox.setMargin(vBox, new Insets(20, 0, 0, 10));
        }
    }
}
