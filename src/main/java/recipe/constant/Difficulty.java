package recipe.constant;

import lombok.Getter;

@Getter
public enum Difficulty {
	EASY("쉬움"), NORMAL("보통"), DIFFICULT("어려움");

	private final String description;

	Difficulty(String description) {
		this.description = description;
	}
}
