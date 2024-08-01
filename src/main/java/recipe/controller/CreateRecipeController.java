package recipe.controller;

import common.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import recipe.constant.Category;
import recipe.constant.Difficulty;
import recipe.domain.Recipe;
import recipe.repository.RecipeRepository;
import recipe.service.CreateRecipeService;
import common.DBConnection;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class CreateRecipeController implements Initializable {
	DBConnection dbConnection = new DBConnection();
	RecipeRepository recipeRepository = new RecipeRepository(dbConnection);
	private final CreateRecipeService createRecipeService = new CreateRecipeService(recipeRepository);

	@FXML
	private TextField titleField = new TextField();

	@FXML
	private TextArea descriptionField = new TextArea();

	@FXML
	private ChoiceBox<String> difficultyBox = new ChoiceBox<>();

	@FXML
	private ChoiceBox<String> quantityBox = new ChoiceBox<>();

	@FXML
	private ChoiceBox<String> categoryBox = new ChoiceBox<>();

	@FXML
	private VBox ingredientContainer;

	@FXML
	private VBox stepContainer;


	@FXML
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		difficultyBox.setValue("쉬움");
		difficultyBox.getItems().addAll("쉬움", "중간", "어려움");

		quantityBox.setValue("1인분");
		quantityBox.getItems().addAll("1인분", "2인분", "3인분", "4인분", "5인분 이상");

		categoryBox.setValue("전채");
		categoryBox.getItems().addAll("전채", "메인 코스", "디저트");
	}

	@FXML
	public void handleAddIngredient() {
		HBox hbox = new HBox(10);

		TextField newIngredient = new TextField();
		newIngredient.setPromptText("재료");
		newIngredient.setPrefSize(200, 30);

		TextField measurement = new TextField();
		measurement.setPromptText("수량");
		measurement.setPrefSize(200, 30);

		Button removeIngredientButton = new Button();
		removeIngredientButton.setText("삭제");
		removeIngredientButton.setOnAction(e -> handleRemoveIngredient(hbox));

		// VBox에 추가
		hbox.getChildren().addAll(newIngredient, measurement, removeIngredientButton);
		ingredientContainer.getChildren().add(hbox);

		VBox.setMargin(hbox, new Insets(15, 0, 0, 20));
	}

	@FXML
	public void handleRemoveIngredient(HBox hbox) {
		ingredientContainer.getChildren().remove(hbox);
	}

	@FXML
	public void handleAddStep() {
		HBox hbox = new HBox(10);

		TextField newStep = new TextField();
		newStep.setPromptText("조리 순서를 입력해주세요");
		newStep.setPrefSize(200, 30);

		Button removeStepButton = new Button();
		removeStepButton.setText("삭제");
		removeStepButton.setOnAction(e -> handleRemoveStep(hbox));

		// VBox에 추가
		hbox.getChildren().addAll(newStep, removeStepButton);
		stepContainer.getChildren().add(hbox);

		VBox.setMargin(hbox, new Insets(15, 0, 0, 20));
	}

	@FXML
	public void handleRemoveStep(HBox hbox) {
		stepContainer.getChildren().remove(hbox);
	}

	@FXML
	public void handleCreateRecipe() {
		String title = titleField.getText();
		System.out.println(title);
		String description = descriptionField.getText();
		String difficulty = difficultyBox.getValue();
		String quantity = quantityBox.getValue();
		String category = categoryBox.getValue();

		int ingredientSize = ingredientContainer.getChildren().size();
		Object[][] ingredientArray = setNodeStepAndIngredient(ingredientSize, "ingredient", ingredientContainer);


		int stepContainerSize = stepContainer.getChildren().size();
		Object[][] stepArray = setNodeStepAndIngredient(stepContainerSize, "step", stepContainer);

		Recipe recipe = Recipe.builder()
				.memberId(UserSession.getInstance().getLoggedUser().getId())
				.title(title)
				.description(description)
				.quantity(quantity)
				.category(Category.fromDescription(category))
				.difficulty(Difficulty.fromDescription(difficulty))
				.ingredients(ingredientArray)
				.steps(stepArray)
				.build();

		createRecipeService.create(recipe);
	}

	// ingredient, step 입력 값으로 Object 생성
	private Object[][] setNodeStepAndIngredient(int size, String flag, VBox container) {
		Object[][] stepIngredientArray = new Object[size][2];

		int i = 0;
		int stepNumber = 1;
		for (Node node : container.getChildren()) {
			if (node instanceof HBox) {
				Object[] stepIngredientElements = new Object[2];
				int j = 0;
				for (Node hBoxNode : ((HBox) node).getChildren()) {
					if (flag.equals("step")) {
						stepIngredientElements[j++] = stepNumber++;
						if (hBoxNode instanceof TextField textField) {
							stepIngredientElements[j] = textField.getText();
						}
					} else {
						if (hBoxNode instanceof TextField textField) {
							stepIngredientElements[j++] = textField.getText();
						}
					}
				}
				stepIngredientArray[i++] = stepIngredientElements;
			}
		}
		return stepIngredientArray;
	}
}
