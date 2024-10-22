package review.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.io.IOException;

public class ReviewUpdateController {

    @FXML
    private TextField tfNickname;
    @FXML
    private TextField tfRating;
    @FXML
    private TextArea taContent;
    @FXML
    private Button selectAllRecipeReview;
    @FXML
    private Button updateRecipeReview;
    @FXML
    private Button deleteRecipeReview;

    private ReviewService reviewService = new ReviewServiceImpl();
    private long reviewId;
    private long memberId;

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

    public void setReviewData(long memberId, long reviewId, String nickname, String rating, String content) {
        this.memberId = memberId;
        this.reviewId = reviewId;
        tfNickname.getText();
        tfRating.setText(rating);
        taContent.setText(content);
        tfNickname.setEditable(false); // 닉네임은 수정하지 못하도록 설정
    }

    // 후기글 수정 처리
    public void updateRecipeReview(ActionEvent event) {
        try {
            System.out.println("후기글 수정 reviewId :"+reviewId);
            System.out.println("후기글 수정 memberId : "+memberId);
            Review updatedReview = new Review();
            updatedReview.setId(reviewId);
            updatedReview.setMemberId(memberId);
            updatedReview.setNickName(tfNickname.getText());
            updatedReview.setStarRating(tfRating.getText());
            updatedReview.setContent(taContent.getText());

            reviewService.updateRecipeReview(updatedReview);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("수정 완료");
            alert.setHeaderText(null);
            alert.setContentText("레시피 후기가 성공적으로 수정되었습니다.");
            alert.showAndWait();

            switchScene(event, "/fxml/RecipeReview.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //글 삭제
    public void deleteRecipeReview(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("레시피 후기 삭제");
        alert.setHeaderText("선택한 후기를 삭제하시겠습니까?");
        alert.setContentText("이 작업은 되돌릴 수 없습니다.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                reviewService.deleteRecipeReview(reviewId);

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("삭제 완료");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("레시피 후기가 성공적으로 삭제되었습니다.");
                infoAlert.showAndWait();

                switchScene(event, "/fxml/RecipeReview.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //목록 화면 이동
    public void selectAllRecipeReview(ActionEvent event) {
        try {
            switchScene(event, "/fxml/RecipeReview.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
