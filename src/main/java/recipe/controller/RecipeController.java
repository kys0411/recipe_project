package recipe.controller;

import common.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipe.domain.Recipe;
import recipe.repository.RecipeQueryRepository;
import recipe.service.FindRecipeService;
import user.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RecipeController implements Initializable {
    DBConnection dbConnection = new DBConnection();
    RecipeQueryRepository recipeQueryRepository = new RecipeQueryRepository(dbConnection);
    private final FindRecipeService findRecipeService = new FindRecipeService(recipeQueryRepository);
    private final UserService userService = new UserService();

    @FXML
    private VBox recipeStepsVBox;

    @FXML
    private Text recipeTitleText;

    @FXML
    private Text recipeDescriptionText;

    @FXML
    private Text recipeQuantityText;

    @FXML
    private Text recipeCategoryText;

    @FXML
    private Text recipeDifficultyText;

    @FXML
    private Text recipeMemberText;

    @FXML
    private Text recipeCreateText;

    @FXML
    private Text recipeUpdateText;

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView recipeImage;

    @FXML
    private Button updateRecipeButton;

    @FXML
    private Pane ingredientPane;

    private static final int MAX_ITEMS_PER_HBOX = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 이미지 border 작업
        Rectangle clip = new Rectangle(recipeImage.getFitWidth(), recipeImage.getFitHeight());
        clip.setArcWidth(20); // 둥근 모서리의 너비 설정
        clip.setArcHeight(20); // 둥근 모서리의 높이 설정
        recipeImage.setClip(clip);
        loadRecipeStepsFromDB();

        Object[][] ingredients = null;

        try {
            ingredients = recipeQueryRepository.getIngredients(dbConnection.getConnection(), 73L);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addIngredientsToPane(ingredients);
    }

    private void loadRecipeStepsFromDB() {
        try {
            Recipe recipe = findRecipeService.getOne(73L);
            handleAddStep(recipe);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddStep(Recipe recipe) throws SQLException {
        // 레시피 타이틀 작업
        recipeTitleText.setText(recipe.getTitle());

        // 레시피 설명(Description) 작업
        recipeDescriptionText.setText(recipe.getDescription());

        // 레시리 quantity, difficulty,
        recipeDifficultyText.setText("카테고리 : " + recipe.getDifficulty().getDescription());
        recipeQuantityText.setText("양 : " + recipe.getQuantity());
        recipeCategoryText.setText("난이도 : " + recipe.getCategory().getDescription());
        recipeMemberText.setText("작성자 : " + userService.findUser(recipe.getMemberId()).getNickname());

        // 등록, 수정일자
        recipeCreateText.setText("등록일 : " + recipe.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " | ");
        recipeUpdateText.setText("수정일 : " + recipe.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // 조리과정 작업
        for(Object[] step : recipe.getSteps()) {
            // 새로운 HBox 생성
            HBox newStepHBox = new HBox();
            newStepHBox.getStyleClass().add("step-hBox");
            newStepHBox.setPrefHeight(66.0);
            newStepHBox.setPrefWidth(598.0);

            // 첫 번째 Pane 생성
            Pane stepNumberPane = new Pane();
            stepNumberPane.setPrefHeight(100.0);
            stepNumberPane.setPrefWidth(54.0);
            stepNumberPane.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
            stepNumberPane.setPadding(new Insets(10, 0, 10, 0));

            // Text 요소 생성 및 설정
            Text stepNumberText = new Text(step[0].toString());
            stepNumberText.setTextOrigin(javafx.geometry.VPos.TOP);
            stepNumberText.setFont(new Font("Pretendard", 36.0));
            stepNumberPane.getChildren().add(stepNumberText);

            // 두 번째 Pane 생성
            Pane stepDescriptionPane = new Pane();
            stepDescriptionPane.setPrefHeight(57.0);
            stepDescriptionPane.setPrefWidth(531.0);

            // Text 요소 생성 및 설정
            Text stepDescriptionText = new Text(step[1].toString());
            stepDescriptionText.setLayoutX(21.0);
            stepDescriptionText.setLayoutY(10.0);
            stepDescriptionText.setTextOrigin(javafx.geometry.VPos.TOP);
            stepDescriptionText.setWrappingWidth(496.953125);
            stepDescriptionPane.getChildren().add(stepDescriptionText);

            // HBox에 Pane 추가
            newStepHBox.getChildren().addAll(stepNumberPane, stepDescriptionPane);
            newStepHBox.setPadding(new Insets(10, 0, 50, 0));

            // VBox에 새로운 HBox 추가
            recipeStepsVBox.getChildren().add(newStepHBox);
        }
    }
    
    // 뒤로가기
    @FXML
    public void handleBackButtonClick() throws IOException {
        Parent mainRoot = FXMLLoader.load(getClass().getResource("fxml/RecipeReview.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(mainRoot));
    }

    private void addIngredientsToPane(Object[][] ingredients) {
        HBox currentHBox = new HBox();
        ingredientPane.getChildren().add(currentHBox);

        int count = 0;

        for (Object[] ingredient : ingredients) {
            if (count >= MAX_ITEMS_PER_HBOX) {
                currentHBox = new HBox();
                ingredientPane.getChildren().add(currentHBox);
                count = 0;
            }
            Text text = new Text(ingredient[0].toString() + "  " + ingredient[1].toString() + "  ");
            text.setTextOrigin(javafx.geometry.VPos.TOP);
            currentHBox.getChildren().add(text);
            count++;
        }
    }
    
    // 레시피 수정
    @FXML
    public void handleUpdateButtonClick() throws IOException {
        Parent mainRoot = FXMLLoader.load(getClass().getResource("fxml/RecipeReview.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(mainRoot));
    }
}
