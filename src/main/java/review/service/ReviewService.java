package review.service;

import review.domain.Review;

import java.util.List;

public interface ReviewService {

    //등록한 레시피 조회
    List<Review> selectMemberReview(long memberId) throws Exception;

    //레시피 후기 생성
    public void insertRecipeReview(Review review) throws Exception;

    //레시피 후기 수정
    public void updateRecipeReview(Review newReview) throws Exception;

    //레시피 후기 삭제
    public void deleteRecipeReview(long id) throws Exception;


}
