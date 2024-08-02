package review.Controller;
/*
 * 작성일 2024-08-01
 * 작성자 황석현
 * */

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
    private TextField tfNickname;
    @FXML
    private TextField tfRating;
    @FXML
    private TextArea taContent;
    @FXML
    private Button selectAllRecipeReview;

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



    //후기글 읽기 처리
    public void selectReviewMember(long memberId, long id) throws Exception {
        System.out.println(memberId);
        //게시글 읽기 요청
/*
        Review review = reviewService.selectDetailRecipeReview(memberId, id);
        tfNickname.setText(review.getNickName());
        tfRating.setText(String.valueOf(review.getRating()));
        taContent.setText(review.getContent());
*/
    }

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
}
