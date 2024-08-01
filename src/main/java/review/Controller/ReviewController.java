package review.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReviewController implements Initializable {

    @FXML
    private TableView<Review> selectReviewMember;
    @FXML
    private TableColumn<Review, CheckBox> colCbDelete;
    @FXML
    private TableColumn<Review, Long> reviewId;
    @FXML
    private TableColumn<Review, String> recipeName;
    @FXML
    private TableColumn<Review, String> nickName;
    @FXML
    private TableColumn<Review, String> content;
    @FXML
    private TableColumn<Review, Long> starRating;
    @FXML
    private TableColumn<Review, Date> reviewDate;
    @FXML
    private CheckBox cbAll;

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
                content.setCellValueFactory(new PropertyValueFactory<>("content"));
                starRating.setCellValueFactory(new PropertyValueFactory<>("starRating"));
                reviewDate.setCellValueFactory(new PropertyValueFactory<>("date"));

                // TableView 에 데이터 리스트 지정
                selectReviewMember.setItems(list);

                // 전체 리뷰후기 선택 이벤트 cbAll(CheckBoxAll)
                cbAll.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        CheckBox checkBox = (CheckBox) event.getSource();
                        boolean checkAll = checkBox.isSelected();
                        selectReviewMember.getItems().forEach(review -> review.getCbDelete().setSelected(checkAll));
                        System.out.println("전체 체크박스");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 레시피 후기 등록하기
     * @param event
     */
    public void close(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("레시피 후기 종료");
        alert.setHeaderText("레시피 후기를 종료하시겠습니까?");
        alert.setContentText("프로그램이 종료됩니다.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("프로그램 종료");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /*
     * 레시피 후기 전체, 선택 삭제하기
     * @param event
     */
    public void deleteRecipeReview(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("레시피 후기 삭제");
        alert.setHeaderText("선택한 후기를 삭제하시겠습니까?");
        alert.setContentText("이 작업은 되돌릴 수 없습니다.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectReviewMember.getItems().removeIf(review -> {
                CheckBox cbDelete = review.getCbDelete();
                boolean checked = cbDelete.isSelected();

                if (checked) {
                    long id = review.getId();
                    try {
                        reviewService.deleteRecipeReview(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return checked;
            });

            initialize(null, null); // 테이블 갱신
        }
    }

    public void manageReviews() {
        try {
            // 가변적인 memberId 값을 설정하여 특정 회원의 리뷰 조회
            long memberId = 15; // 여기서 memberId 값을 원하는 값으로 설정할 수 있습니다.
            reviewService.selectMemberReview(memberId);

            // 레시피 후기 생성 예제
            Review newReview = Review.builder()
                    .id(1)
                    .memberId(19)
                    .rating(5)
                    .content("레시피 강추!")
                    .date(new Date())
                    .recipeId(4)
                    .build();
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
