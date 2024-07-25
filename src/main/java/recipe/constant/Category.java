package recipe.constant;

import lombok.Getter;

@Getter
public enum Category {
	APPETIZER("전채"), MAIN_DISH("메인 요리"), DESSERT("디저트");

	private final String description;

	Category(String description) {
		this.description = description;
	}
}
