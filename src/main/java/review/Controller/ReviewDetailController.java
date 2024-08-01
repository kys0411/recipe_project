package review.Controller;
/*
 * 작성일 2024-08-01
 * 작성자 황석현
 * */

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

public class ReviewDetailController {

    @FXML
    private TextField tfNickname;
    @FXML
    private TextField tfRating;
    @FXML
    private TextArea taContent;

    private ReviewService reviewService = new ReviewServiceImpl();

    //후기글 읽기 처리
    public void selectReviewMember(long reviewId) throws Exception {
        System.out.println(reviewId);
        //게시글 읽기 요청
/*
        Review review = reviewService.selectMemberReview(reviewId);
        tfNickname.setText(review.getNickName());
        tfRating.setText(String.valueOf(review.getRating()));
        taContent.setText(review.getContent());
*/
    }
}
