import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import review.Controller.ReviewController;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeProject extends Application {

	static List<Review> reviewList = new ArrayList<>();
	static ReviewService reviewService = new ReviewServiceImpl();

	public static void main(String[] args) throws Exception {

		ReviewService reviewService = new ReviewServiceImpl();
		ReviewController controller = new ReviewController();
		controller.manageReviews();

		//특정 회원의 리뷰 조회
		long memberId = 15;
		reviewService.selectMemberReview(memberId);

        launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		stage.setTitle("레시피");
		stage.setScene(new Scene(root,600, 400));
		stage.show();
		/*try {
			// FXML 파일의 경로가 올바른지 확인
			URL fxmlLocation = getClass().getResource("/fxml/RecipeReview.fxml");
			if (fxmlLocation == null) {
				throw new IllegalArgumentException("FXML file not found: /fxml/RecipeReview.fxml");
			}

			Parent root = FXMLLoader.load(fxmlLocation);

			//스테이지 설정(이미지 아이콘 지정 추가)
			Image icon = new Image("reviewIcon.png");
			stage.getIcons().add(icon);
			stage.setTitle("Recipe Review");

			//stage 객체로 프로그램 크기 변경막기
			stage.setResizable(false);
			stage.show();

			stage.setTitle("Recipe Review");
			stage.setResizable(false);

			stage.setScene(new Scene(root, 800, 600));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
