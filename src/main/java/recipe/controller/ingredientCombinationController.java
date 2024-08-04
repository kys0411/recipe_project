package recipe.controller;

import common.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipe.domain.Recipe;
import recipe.repository.RecipeQueryRepository;
import recipe.repository.RecipeSelectRepository;
import recipe.service.GetAllRecipesService;
import recipe.service.IngredientCombinationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ingredientCombinationController implements Initializable {
    DBConnection dbConnection = new DBConnection();
    private final IngredientCombinationService ingredientCombinationService = new IngredientCombinationService(new RecipeSelectRepository(new DBConnection()));

    @FXML
    private ImageView backButton;

    @FXML
    private TextField searchField;

    @FXML
    private Text notFoundText;

    @FXML
    private VBox recipeContainer;

    @FXML
    private VBox ingredientAllListVBox;

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
    public void handleSearch() throws SQLException, ClassNotFoundException {
        ingredientAllListVBox.getChildren().clear();
        recipeContainer.getChildren().clear();

        String query = searchField.getText();
        if (!query.isEmpty()) {
            String[] ingredients = query.trim().split("\\s+");
            addIngredient(ingredients);

            List<Recipe> recipes = ingredientCombinationService.ingredientCombination(ingredients);
            if (recipes.isEmpty()) {
                notFoundText.setVisible(true);
            } else {
                notFoundText.setVisible(false);
                selectCombinatedRecipes(recipes);
            }
        }
    }
    
    // 사용자가 검색한 재료를 검색창 밑에 따로 보이게 설정
    private void addIngredient(String[] ingredients) {
        HBox ingredientListHBox = createNewHBox();
        double currentWidth = 0;

        int count = 0;
        for (String ingredient : ingredients) {
            HBox ingredientHBox = createIngredientHBox(ingredient);

            if (count > 5) {
                ingredientAllListVBox.getChildren().add(ingredientListHBox);
                ingredientListHBox = createNewHBox();
                count = 0;
            }

            ingredientListHBox.getChildren().add(ingredientHBox);
            count++;
        }

        if (!ingredientListHBox.getChildren().isEmpty()) {
            ingredientAllListVBox.getChildren().add(ingredientListHBox);
        }
    }

    // 새로운 HBox 생성 메서드
    private HBox createNewHBox() {
        HBox hBox = new HBox();
        hBox.setPrefHeight(47.0);
        hBox.setStyle("-fx-background-color: #fffddc;");
        return hBox;
    }

    // 새로운 Pane 생성 메서드
    private HBox createIngredientHBox(String ingredient) {
        HBox ingredientHBox = new HBox();
        ingredientHBox.setStyle("-fx-border-radius: 30px; -fx-border-color: gray; -fx-padding: 5 15 6 15; -fx-alignment: center;");
        ingredientHBox.setMaxHeight(35);
        ingredientHBox.setMaxWidth(Double.MAX_VALUE);
        ingredientHBox.setMinHeight(0);
        ingredientHBox.setMinWidth(0);

        Text ingredientText = new Text(ingredient);
        ingredientText.setFont(new Font("LINE Seed Sans KR Regular", 16.0));
        ingredientText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        ingredientText.setStrokeWidth(0);

        ingredientHBox.getChildren().add(ingredientText);
        HBox.setMargin(ingredientHBox, new Insets(0, 10, 0, 0));

        return ingredientHBox;
    }

    public void selectCombinatedRecipes(List<Recipe> recipes) {
        int counter = 0;
        HBox currentHBox = new HBox();
        currentHBox.setPrefHeight(295.0);
        currentHBox.setPrefWidth(632.0);

        for (Recipe recipe : recipes) {
            if (counter % 3 == 0) {
                if (counter != 0) {
                    recipeContainer.getChildren().add(currentHBox);
                }
                currentHBox = new HBox();
                currentHBox.setPrefHeight(295.0);
                currentHBox.setPrefWidth(632.0);
            }

            VBox recipeVBox = null;
            try {
                recipeVBox = createRecipeVBox(recipe);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            currentHBox.getChildren().add(recipeVBox);
            VBox.setMargin(currentHBox, new Insets(0, 0, 20, 0));
            counter++;
        }

        // 마지막 HBox 추가
        if (!currentHBox.getChildren().isEmpty()) {
            recipeContainer.getChildren().add(currentHBox);
        }
    }

    private VBox createRecipeVBox(Recipe recipe) throws SQLException {
        VBox recipeVBox = new VBox();
        recipeVBox.setPrefHeight(250.0);
        recipeVBox.setPrefWidth(233.0);
        recipeVBox.setPadding(new Insets(10, 14, 10, 10));

        ImageView recipeImage = new ImageView();
        recipeImage.setFitHeight(183.0);
        recipeImage.setFitWidth(192.0);
        recipeImage.setPickOnBounds(true);
        recipeImage.setSmooth(false);
        recipeImage.setImage(new Image("/images/food1.jpg"));
        recipeVBox.setCursor(javafx.scene.Cursor.HAND);
        Rectangle clip = new Rectangle(recipeImage.getFitWidth(), recipeImage.getFitHeight());
        clip.setArcWidth(20); // 둥근 모서리의 너비 설정
        clip.setArcHeight(20); // 둥근 모서리의 높이 설정
        recipeImage.setClip(clip);

        VBox.setMargin(recipeImage, new Insets(0, 0, 15, 0));

        Pane recipeTitlePane = new Pane();
        recipeTitlePane.setPrefHeight(40.0);
        recipeTitlePane.setPrefWidth(200.0);
        Text recipeTitle = new Text(recipe.getTitle());
        recipeTitle.setFont(new Font("LINE Seed Sans KR Regular", 13.0));
        recipeTitle.setWrappingWidth(203.7734375);
        recipeTitlePane.getChildren().add(recipeTitle);
        VBox.setMargin(recipeTitlePane, new Insets(6, 0, 6,0 ));

        VBox recipeInfoBox = new VBox();
        recipeInfoBox.setPrefHeight(200.0);
        recipeInfoBox.setPrefWidth(100.0);

        Text author = new Text("야왕이네");
        author.setFill(javafx.scene.paint.Color.valueOf("#4a4a4a"));
        author.setFont(new Font("LINE Seed Sans KR Regular", 13.0));
        author.setWrappingWidth(203.7734375);
        VBox.setMargin(author, new Insets(0, 0, 6, 0));

        HBox ratingBox = new HBox();
        ratingBox.setPrefHeight(100.0);
        ratingBox.setPrefWidth(200.0);
        ImageView starImage = new ImageView(new Image("/images/star.png"));
        starImage.setFitHeight(16.0);
        starImage.setFitWidth(20.0);
        starImage.setPickOnBounds(true);
        starImage.setPreserveRatio(true);

        Random random = new Random();
        Text rating = new Text(String.valueOf((Math.round((random.nextDouble() * 5.0) * 10) / 10.0) + " (" + (random.nextInt(20) + 1) + ")"));
        rating.setFont(new Font("LINE Seed Sans KR Bold", 12.0));
        ratingBox.getChildren().addAll(starImage, rating);

        recipeInfoBox.getChildren().addAll(author, ratingBox);

        recipeVBox.getChildren().addAll(recipeImage, recipeTitlePane, recipeInfoBox);
        recipeVBox.setOnMouseClicked(event -> {
            try {
                switchToRecipeDetail(recipe.getId(), event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return recipeVBox;
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
