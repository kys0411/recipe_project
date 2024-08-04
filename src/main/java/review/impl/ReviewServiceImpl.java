package review.impl;

import recipe.domain.Recipe;
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
    public Review selectDetailRecipeReview(long memberId, long id) throws Exception {
        return reviewDao.selectDetailRecipeReview(memberId, id);
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

    @Override
    public List<Recipe> getAllRecipes() throws Exception {
        return reviewDao.getAllRecipes();
    }
}
