package review.impl;

import review.Dao.ReviewDao;
import review.domain.Review;
import review.service.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private ReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public List<Review> selectMemberReview(long memberId) throws Exception {
        List<Review> reviews = reviewDao.selectMemberReview((int) memberId);

        for (Review review : reviews) {
            System.out.println("Review ID: " + review.getId());
            System.out.println("Review Name: " + review.getRecipeName());
            System.out.println("Member ID: " + review.getMemberId());
            System.out.println("NickName : " + review.getNickName());
            System.out.println("Rating: " + review.getRating());
            System.out.println("Content: " + review.getContent());
            System.out.println("Date: " + review.getDate());
            System.out.println("Recipe ID: " + review.getRecipeId());
            System.out.println("------------");
        }
        return reviewDao.selectMemberReview(memberId);
        //return reviews;
    }

    @Override
    public void insertRecipeReview(Review review) throws Exception {
        reviewDao.insertRecipeReview(review);
    }

    @Override
    public void updateRecipeReview(Review review) throws Exception {
        reviewDao.updateRecipeReview(review);
    }

    @Override
    public void deleteRecipeReview(long id) throws Exception {

    }

}
