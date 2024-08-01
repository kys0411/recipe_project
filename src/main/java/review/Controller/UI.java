package review.Controller;
/*
* url 열거타입 지정 class
*
* RecipeReview.fxml (레시피 후기 목록) 으로 부터
* insertRecipeReview (레시피 후기 등록)
* updateRecipeReview (레시피 후기 수정)
* selectMemberReview (레시피 후기 상세조회)
*
* 어떤 url 으로 이동할지 상수로 작성
* */
public enum UI {

    MAIN("/fxml/RecipeReview.fxml"),
    INSERT("/fxml/insertRecipeReview.fxml"),
    //READ("/fxml/selectRecipeReview.fxml"),
    READ("/fxml/DetailMemberReview.fxml"),
    UPDATE("/fxml/updateRecipeReview.fxml");

    private final String path;

    public String getPath() {
        return path;
    }

    UI(String path) {
        this.path = path;
    }
}
