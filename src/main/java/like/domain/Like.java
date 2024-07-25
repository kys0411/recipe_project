package like.domain;

import lombok.Getter;

@Getter
public class Like {
	private Long id;
	private Long memberId;
	private Long recipeId;

	public Like(Long id, Long memberId, Long recipeId) {
		this.id = id;
		this.memberId = memberId;
		this.recipeId = recipeId;
	}
}
