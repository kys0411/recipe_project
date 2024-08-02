package review.impl;

import review.Dao.ReviewDao;
import review.domain.Review;
import review.service.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private ReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public List<Review> selectAllRecipeReview(long id) throws Exception {
        List<Review> reviews = reviewDao.selectAllRecipeReview(id);
        return reviewDao.selectAllRecipeReview(id);
    }

    @Override
    public List<Review> selectMyRecipeReview(long memberId) throws Exception {
        return reviewDao.selectMyRecipeReview(memberId);
    }

    @Override
    public List<Review> selectDetailRecipeReview(long memberId, long id) throws Exception {
        return List.of();
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
    public long deleteRecipeReview(long id) throws Exception {
        return reviewDao.deleteRecipeReview(id);
    }
}
