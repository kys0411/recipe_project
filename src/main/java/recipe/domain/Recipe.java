package recipe.domain;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import recipe.constant.Category;
import recipe.constant.Difficulty;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
	private Long id;
	private Long memberId;
	private Category category;
	private String title;
	private String description;
	private Object[][] steps;
	private Object[][] ingredients;
	private Difficulty difficulty;
	private String quantity;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private ComboBox comboBox = new ComboBox();

	public Recipe(Long memberId,
				  Category category,
				  String title,
				  String description,
				  Object[][] steps,
				  Object[][] ingredients,
				  Difficulty difficulty,
				  String quantity) {
		this.memberId = memberId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.steps = steps;
		this.ingredients = ingredients;
		this.difficulty = difficulty;
		this.quantity = quantity;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = null;
	}

	@Override
	public String toString() {
		return title; // ComboBox에서 보여줄 값
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}}
