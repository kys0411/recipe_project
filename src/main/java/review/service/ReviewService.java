package review.service;

import recipe.domain.Recipe;
import review.domain.Review;

import java.util.List;

public interface ReviewService {

    //등록한 레시피 후기 전체 조회
    List<Review> selectAllRecipeReview(long id) throws Exception;

    //등록한 레시피 후기 내꺼 조회
    List<Review> selectMyRecipeReview(long memberId) throws Exception;

    //등록한 레시피 상세 조회
    Review selectDetailRecipeReview(long memberId, long id) throws Exception;

    //레시피 후기 생성
    public void insertRecipeReview(Review review) throws Exception;

    //레시피 후기 수정
    public void updateRecipeReview(Review newReview) throws Exception;

    //레시피 후기 삭제
    public long deleteRecipeReview(long id) throws Exception;

    //레시피 전체 목록
    List<Recipe> getAllRecipes() throws Exception;

}
