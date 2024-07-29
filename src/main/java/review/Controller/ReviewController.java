package review.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import review.domain.Review;
import review.service.ReviewService;
import review.impl.ReviewServiceImpl;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;


public class ReviewController implements Initializable {

    @FXML
    private TableView<Review> selectReviewMember;
    @FXML
    private TableColumn<Review, CheckBox> colCbDelete;
    @FXML
    private TableColumn<Review, Integer> reviewId;
    @FXML
    private TableColumn<Review, Integer> memberId;
    @FXML
    private TableColumn<Review, Integer> recipeId;
    @FXML
    private TableColumn<Review, String> content;
    @FXML
    private TableColumn<Review, Integer> rating;
    @FXML
    private TableColumn<Review, Date> reviewDate;


    private ReviewService reviewService = new ReviewServiceImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*데이터 초기화*/
        List<Review> reviewList = new ArrayList<Review>();
        //reviewList = reviewService.selectMemberReview();

        for(Review review : reviewList) {
            System.out.println(review);
        }

        ObservableList<Review> list = FXCollections.observableArrayList(reviewList);

        reviewId.setCellValueFactory(new PropertyValueFactory<Review, Integer>("reviewId"));
        memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        recipeId.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        reviewDate.setCellValueFactory(new PropertyValueFactory<>("reviewDate"));

        //TableView 에 데이터 리스트 지정
        selectReviewMember.setItems(list);

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