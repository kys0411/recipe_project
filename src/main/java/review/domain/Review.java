package review.domain;

import javafx.scene.control.CheckBox;

import java.util.Date;

public class Review {

	private long id;
	private long memberId;
	private long rating;
	private String content;
	private Date date;
	private long recipeId;
	private String nickName;
	private String recipeName;
	private CheckBox cbDelete;

	// Constructors
	public Review() {}

	public Review(long id, long memberId, long rating, String content, Date date, long recipeId) {
		this.id = id;
		this.memberId = memberId;
		this.rating = rating;
		this.content = content;
		this.date = date;
		this.recipeId = recipeId;
		this.cbDelete = new CheckBox();
	}

	public CheckBox getCbDelete() {
		return cbDelete;
	}

	public void setCbDelete(CheckBox cbDelete) {
		this.cbDelete = cbDelete;
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	@Override
	public String toString() {
		return "Review{" +
				"id=" + id +
				", memberId=" + memberId +
				", rating=" + rating +
				", content='" + content + '\'' +
				", date=" + date +
				", recipeId=" + recipeId +
				", nickName='" + nickName + '\'' +
				", recipeName='" + recipeName + '\'' +
				'}';
	}
}
