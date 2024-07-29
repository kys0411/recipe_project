package recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import recipe.constant.Category;
import recipe.constant.Difficulty;

import java.time.LocalDateTime;
import java.util.List;

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
}
