import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class RecipeProject extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(
				getClass().getClassLoader().getResource("fxml/testFX.fxml")
		));

		stage.setTitle("Recipe Program");

		stage.setScene(new Scene(root, 800, 600));

		stage.show();
	}
}
