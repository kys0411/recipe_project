package recipe.constant;

import lombok.Getter;

@Getter
public enum Difficulty {
	EASY("쉬움"), NORMAL("중간"), DIFFICULT("어려움");

	private final String description;

	Difficulty(String description) {
		this.description = description;
	}

	public static Difficulty fromDescription(String description) {
		for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.getDescription().equals(description)) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Unknown difficulty: " + description);
	}
}
