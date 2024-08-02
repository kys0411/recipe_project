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

    public Button deleteRecipeReview;
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
        //메인 url 으로부터 네비게이션 하기위한 url 경로를 받는 메서드
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //후기글 읽기 처리(등록한 레시피 상세 조회)
    public void selectDetailRecipeReview(long memberId, long id) throws Exception {
        System.out.println(memberId);
        //게시글 읽기 요청
        Review review = reviewService.selectDetailRecipeReview(memberId, id);
        if(review != null){
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
    
/*    //수정 화면 이동
    public void updateRecipeReview(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == updateRecipeReview) {
            fxmlFile = "/fxml/updateRecipeReview.fxml";
        }

        try {
            switchScene(event, fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //글 삭제
    public void deleteRecipeReview(ActionEvent event) {
        String fxmlFile = "";
        this.id = id;
        try {
            long review = reviewService.deleteRecipeReview(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("레시피 후기 삭제");
        alert.setHeaderText("선택한 후기를 삭제하시겠습니까?");
        alert.setContentText("이 작업은 되돌릴 수 없습니다.");

        long result = 0;
        if(alert.showAndWait().get() == ButtonType.OK) {
            try {
                result = reviewService.deleteRecipeReview(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result > 0) {
            System.out.println("글삭제 처리 성공!");
            fxmlFile = "/fxml/RecipeReview.fxml";
        }
    }*/

    //수정 화면 이동
    public void updateRecipeReview(ActionEvent event) {
        try {
            Review updatedReview = new Review();
            updatedReview.setId(reviewId);
            updatedReview.setMemberId(memberId);
            updatedReview.setNickName(tfNickname.getText());
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


}
