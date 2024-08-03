package review.Controller;

import com.sun.javafx.scene.SceneUtils;
import common.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.io.IOException;

public class ReviewDetailController {

    @FXML
    private TextField tfRecipeName;
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

    private long reviewId;
    private long memberId;
    private ReviewService reviewService = new ReviewServiceImpl();

    /*
     * 화면이동 (NavigationEvent)
     * @param event
     * @param fxml
     * @throws IOException
     * */
    private void switchScene(ActionEvent event, String fxml) throws IOException {
        //메인 url 으로부터 네비게이션 하기위한 url 경로를 받는 메서드
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //후기글 읽기 처리(등록한 레시피 상세 조회)
    public void selectDetailRecipeReview(long memberId, long id) throws Exception {
        //this.memberId = memberId;
        //this.reviewId = id;
        this.memberId = id;
        this.reviewId = memberId;
        System.out.println("상세 memberId :"+id);
        System.out.println("상세 reviewId :"+memberId);
        //게시글 읽기 요청
        Review review = reviewService.selectDetailRecipeReview(memberId, id);
        if(review != null){
            tfRecipeName.setText(review.getRecipeName());
            tfNickname.setText(review.getNickName());
            tfRating.setText(review.getStarRating());
            taContent.setText(review.getContent());
        }
    }

    //목록 화면 이동
    public void selectAllRecipeReview(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == selectAllRecipeReview) {
            fxmlFile = "/fxml/RecipeReview.fxml";
        }

        try {
            switchScene(event, fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 수정 화면 이동
    public void updateRecipeReview(ActionEvent event) {
        if (UserSession.getInstance().getLoggedUser().getId() != memberId) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("권한 오류");
            alert.setHeaderText(null);
            alert.setContentText("다른 사용자의 후기는 수정할 수 없습니다.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/updateRecipeReview.fxml"));
            Parent root = loader.load();
            ReviewUpdateController updateController = loader.getController();
            updateController.setReviewData(memberId, reviewId, tfNickname.getText(), tfRating.getText(), taContent.getText());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
