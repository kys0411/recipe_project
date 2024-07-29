package review.domain;

import java.awt.*;
import java.util.Date;


//Field
public class Review {

	//Field
	private Checkbox colCbDelete;
	private long id;
	private long memberId;

	public Checkbox getColCbDelete() {
		return colCbDelete;
	}

	public void setColCbDelete(Checkbox colCbDelete) {
		this.colCbDelete = colCbDelete;
	}

	private long rating;
	private String content;
	private Date date;
	private long recipeId;

	//Constructor
	public Review() {

	}

	//Method
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

	public Review(long id, long memberId, long rating, String content, Date date, long recipeId) {
		this.id = id;
		this.memberId = memberId;
		this.rating = rating;
		this.content = content;
		this.date = date;
		this.recipeId = recipeId;
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
				'}';
	}

}




