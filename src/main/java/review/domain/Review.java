package review.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Review {
	private Long id;
	private Long memberId;
	private Long recipeId;
	private int rating;
	private String content;
	private LocalDateTime createdAt;

	public Review(Long id, Long memberId, Long recipeId, int rating, String content) {
		this.id = id;
		this.memberId = memberId;
		this.recipeId = recipeId;
		this.rating = rating;
		this.content = content;
		this.createdAt = LocalDateTime.now();
	}
}
