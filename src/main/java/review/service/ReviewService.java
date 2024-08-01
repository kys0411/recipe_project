package review.service;
/*
 * 작성일 2024-08-01
 * 작성자 황석현
 * */

import review.domain.Review;

import java.util.List;

public interface ReviewService {

    //등록한 레시피 후기 조회
    List<Review> selectMemberReview(long memberId) throws Exception;

    //레시피 후기 생성
    public void insertRecipeReview(Review review) throws Exception;

    //레시피 후기 수정
    public void updateRecipeReview(Review newReview) throws Exception;

    //레시피 후기 삭제
    public long deleteRecipeReview(long id) throws Exception;

}
