package common;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import lombok.NoArgsConstructor;
import recipe.controller.RecipeController;
import recipe.dto.RecipeDto;
import recipe.repository.RecipeQueryRepository;
import recipe.service.FindRecipeService;
import recipe.service.GetAllRecipesService;
import user.domain.User;
import user.service.LoginService;
import user.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor(force = true)
public class MainController implements Initializable {

    @FXML
    private VBox rankRecipesRoot;

    @FXML
    private ImageView imageMyInfo;

    @FXML
    private ImageView imageLogout;

    @FXML
    private Pane startIngredientRecipe;

    @FXML
    private Pane startGetAllRecipes;

    @FXML
    private Pane createRecipePane;
    @FXML
    private Pane startReview;

    private final LoginService loginService = new LoginService();
    private final GetAllRecipesService recipesService = new GetAllRecipesService(new RecipeQueryRepository(new DBConnection()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 인기 레시피 레시피 리스트 추가 - 6개만
        List<RecipeDto.FindAll> recipes = recipesService.getAll("좋아요순");

        int counter = 0;
        HBox currentHBox = new HBox();
        currentHBox.setPrefHeight(295.0);
        currentHBox.setPrefWidth(632.0);

        for (RecipeDto.FindAll recipe : recipes.subList(0, 6)) {
            if (counter % 3 == 0) {
                if (counter != 0) {
                    rankRecipesRoot.getChildren().addFirst(currentHBox);
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
            rankRecipesRoot.getChildren().addFirst(currentHBox);
        }
    }
    private VBox createRecipeVBox(RecipeDto.FindAll recipe) throws SQLException {
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
        Text rating = new Text(String.valueOf(recipe.getRating()) + " (" + recipe.getLikes() + ") ");
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

    // Image 타입들 클릭 이벤트
    @FXML
    private void handleButtonAction(MouseEvent event) throws Exception {
        ImageView clickedImage = (ImageView) event.getSource();
        String fxmlFile = "";

        if (clickedImage == imageMyInfo) {
            fxmlFile = "/fxml/myInfo.fxml";
        } else if (clickedImage == imageLogout) {
            loginService.logout();
            fxmlFile = "/fxml/login.fxml";
        }

        switchScene(event, fxmlFile);
    }

    // Pane 타입들 클릭 이벤트
    @FXML
    private void paneHandleButtonAction(MouseEvent event) throws Exception {
        Pane clickedPane = (Pane) event.getSource();
        String fxmlFile = "";

        if (clickedPane == startIngredientRecipe) {
            fxmlFile = "/fxml/ingredientCombination.fxml";
        } else if (clickedPane == startGetAllRecipes) {
            fxmlFile = "/fxml/getAllRecipes.fxml";
        } else if (clickedPane == startReview) {
            fxmlFile = "/fxml/RecipeReview.fxml";
        } else if (clickedPane == createRecipePane) {
            fxmlFile = "/fxml/createRecipe.fxml";
        }
        switchScene(event, fxmlFile);
    }

    private void switchScene(MouseEvent event, String fxml) throws IOException {
        //메인 url 으로부터 네비게이션 하기위한 url 경로를 받는 메서드
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
