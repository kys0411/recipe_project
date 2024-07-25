package recipe.domain;

import lombok.Getter;

@Getter
public class Ingredient {
	private Long id;
	private Long recipeId;
	private String measurement;

	public Ingredient(Long id, Long recipeId, String measurement) {
		this.id = id;
		this.recipeId = recipeId;
		this.measurement = measurement;
	}
}
