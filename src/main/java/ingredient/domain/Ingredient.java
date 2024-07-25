package ingredient.domain;

import lombok.Getter;

@Getter
public class Ingredient {
	private Long id;
	private String name;
	private int kcal;

	public Ingredient(Long id, String name, int kcal) {
		this.id = id;
		this.name = name;
		this.kcal = kcal;
	}

	public Ingredient(Long id, String name) {
		this.id = id;
		this.name = name;
		this.kcal = 0;
	}
}
