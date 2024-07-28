package recipe.constant;

import lombok.Getter;

@Getter
public enum Category {
	APPETIZER("전채"), MAIN_DISH("메인 코스"), DESSERT("디저트");

	private final String description;

	Category(String description) {
		this.description = description;
	}

	public static Category fromDescription(String description) {
		for (Category category : Category.values()) {
            if (category.getDescription().equals(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category description: " + description);
	}
}
