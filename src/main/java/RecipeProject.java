import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import review.Controller.ReviewController;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public void start(Stage stage) throws Exception {

		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(
					getClass().getClassLoader().getResource("fxml/RecipeReview.fxml")
			));

			stage.setTitle("Recipe Program");
			stage.setResizable(false);

			stage.setScene(new Scene(root, 800, 600));

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
