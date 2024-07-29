package review.Controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import review.domain.Review;
import review.service.ReviewService;
import review.impl.ReviewServiceImpl;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

public class ReviewController implements Initializable {

    @FXML
    private TableView<Review> selectReviewMember;
    @FXML
    private TableColumn<Review, CheckBox> colCbDelete;
    @FXML
    private TableColumn<Review, Long> reviewId;
    @FXML
    private TableColumn<Review, String> recipeName;
/*
    @FXML
    private TableColumn<Review, Long> memberId;
*/
    @FXML
    private TableColumn<Review, String> nickName;
    @FXML
    private TableColumn<Review, Long> recipeId;
    @FXML
    private TableColumn<Review, String> content;
    @FXML
    private TableColumn<Review, Long> rating;
    @FXML
    private TableColumn<Review, Date> reviewDate;

    private ReviewService reviewService = new ReviewServiceImpl();

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 데이터 초기화
        try {
            List<Review> reviewList = reviewService.selectMemberReview(18L);
            if (reviewList != null) {
                for (Review review : reviewList) {
                    System.out.println(review);
                }
                ObservableList<Review> list = FXCollections.observableArrayList(reviewList);

                colCbDelete.setCellValueFactory(new PropertyValueFactory<>("cbDelete"));
                reviewId.setCellValueFactory(new PropertyValueFactory<>("id"));
                nickName.setCellValueFactory(new PropertyValueFactory<>("nickName"));
                recipeName.setCellValueFactory(new PropertyValueFactory<>("recipeName"));
                //recipeId.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
                content.setCellValueFactory(new PropertyValueFactory<>("content"));
                rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
                reviewDate.setCellValueFactory(new PropertyValueFactory<>("date"));

                // TableView 에 데이터 리스트 지정
                selectReviewMember.setItems(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 후기 등록하기
     * @param event
     */
    public void close(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("프로그램 종료");
        alert.setHeaderText("프로그램을 종료하시겠습니까?");
        alert.setContentText("프로그래이 종료됩니다.");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("프로그램 종료");
            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void manageReviews() {
        try {
            // 가변적인 memberId 값을 설정하여 특정 회원의 리뷰 조회
            long memberId = 15; // 여기서 memberId 값을 원하는 값으로 설정할 수 있습니다.
            reviewService.selectMemberReview(memberId);

            // 레시피 후기 생성 예제
            Review newReview = new Review(1, memberId, 5, "Great recipe!", new Date(), 10);
            reviewService.insertRecipeReview(newReview);

            // 레시피 후기 수정 예제
            newReview.setContent("Updated content");
            newReview.setRating(4);
            reviewService.updateRecipeReview(newReview);

            // 레시피 후기 삭제 예제
            reviewService.deleteRecipeReview(newReview.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
