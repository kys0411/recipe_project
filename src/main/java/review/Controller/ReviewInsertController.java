package review.Controller;

import common.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import recipe.domain.Recipe;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.io.IOException;
import java.util.List;

public class ReviewInsertController {

    @FXML
    private ComboBox<Recipe> cbRecipe;
    @FXML
    private TextField tfNickname;
    @FXML
    private TextField tfRating;
    @FXML
    private TextArea taContent;
    @FXML
    private Button selectAllRecipeReview;
    @FXML
    private Button insertRecipeReview;

    private ReviewService reviewService = new ReviewServiceImpl();
    private long memberId;

    @FXML
    public void initialize() {
        // 로그인한 회원의 닉네임을 자동으로 가져옴
        memberId = UserSession.getInstance().getLoggedUser().getId();
        tfNickname.setText(UserSession.getInstance().getLoggedUser().getNickname());
        tfNickname.setEditable(false); // 닉네임 필드는 수정 불가

        // 레시피 목록을 불러와 콤보 박스에 설정
        loadRecipeList();
    }

    private void loadRecipeList() {
        try {
            List<Recipe> recipes = reviewService.getAllRecipes();
            ObservableList<Recipe> recipeList = FXCollections.observableArrayList(recipes);
            cbRecipe.setItems(recipeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 화면이동 (NavigationEvent)
     * @param event
     * @param fxml
     * @throws IOException
     * */
    private void switchScene(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // 목록 화면 이동
    public void selectAllRecipeReview(ActionEvent event) {
        try {
            switchScene(event, "/fxml/RecipeReview.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 레시피 후기 등록 처리
    public void insertRecipeReview(ActionEvent event) {
        Recipe selectedRecipe = cbRecipe.getValue();
        String rating = tfRating.getText();
        String content = taContent.getText();

        // 모든 필드가 입력되었는지 확인
        if (selectedRecipe == null || rating.isEmpty() || content.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("입력 오류");
            alert.setHeaderText(null);
            alert.setContentText("모든 필드를 입력해 주세요.");
            alert.showAndWait();
            return;
        }

        try {
            Review review = new Review();
            review.setMemberId(memberId);
            review.setRecipeId(selectedRecipe.getId());
            review.setStarRating(rating);
            review.setContent(content);

            reviewService.insertRecipeReview(review);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("등록 완료");
            alert.setHeaderText(null);
            alert.setContentText("레시피 후기가 성공적으로 등록되었습니다.");
            alert.showAndWait();

            switchScene(event, "/fxml/RecipeReview.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
