package recipe.domain;

import lombok.Getter;
import recipe.constant.Category;
import recipe.constant.Difficulty;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Recipe {
	private Long id;
	private Long memberId;
	private Category category;
	private String title;
	private String description;
	private List<String> steps;
	private Difficulty difficulty;
	private String quantity;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Recipe(Long id,
				  Long memberId,
				  Category category,
				  String title,
				  String description,
				  List<String> steps,
				  Difficulty difficulty,
				  String quantity) {
		this.id = id;
		this.memberId = memberId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.steps = steps;
		this.difficulty = difficulty;
		this.quantity = quantity;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = null;
	}
}
