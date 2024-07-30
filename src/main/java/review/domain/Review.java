package review.domain;

import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	private long id;
	private long memberId;
	private long rating;
	private String starRating;
	private String content;
	private Date date;
	private long recipeId;
	private String nickName;
	private String recipeName;
	private CheckBox cbDelete = new CheckBox();

	public Review(long id) {
		this.id = id;
		this.cbDelete = new CheckBox();
	}

	public CheckBox getCbDelete() {
		return cbDelete;
	}

	public void setCbDelete(CheckBox cbDelete) {
		this.cbDelete = cbDelete;
	}
}
