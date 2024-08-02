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

    MAIN("/fxml/RecipeReview.fxml"), //등록한 레시피 후기 전체 조회
    INSERT("/fxml/insertRecipeReview.fxml"),
    //MY("/fxml/selectRecipeReview.fxml"), //등록한 레시피 후기 내꺼 조회
    READ("/fxml/DetailMemberReview.fxml"), //등록한 레시피 상세 조회
    UPDATE("/fxml/updateRecipeReview.fxml");

    private final String path;

    public String getPath() {
        return path;
    }

    UI(String path) {
        this.path = path;
    }
}
