package review.impl;
/*
 * 작성일 2024-08-01
 * 작성자 황석현
 * */

import common.DBConnection;
import javafx.scene.control.CheckBox;
import review.Dao.ReviewDao;
import review.domain.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl implements ReviewDao {

    private DBConnection dbConnection = new DBConnection();

    @Override
    public List<Review> selectMemberReview(long memberId) throws Exception {
        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT r.review_id, r.member_id, r.recipe_id, c.recipe_name, m.nickname, " +
                "LPAD('★', r.rating, '★') AS star_rating, r.content, r.review_date " +
                "FROM member m, review r, recipe c " +
                "WHERE r.member_id = m.member_id(+) AND r.recipe_id = c.recipe_id(+) AND r.member_id = ?"+
                "ORDER BY r.review_id ASC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getLong("review_id"));
                review.setMemberId(rs.getLong("member_id"));
                review.setRecipeId(rs.getLong("recipe_id"));
                review.setRecipeName(rs.getString("recipe_name"));
                review.setNickName(rs.getString("nickname"));
                review.setStarRating(rs.getString("star_rating"));
                review.setContent(rs.getString("content"));
                review.setDate(rs.getDate("review_date"));
                review.setCbDelete(new CheckBox());

                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public void insertRecipeReview(Review review) throws Exception {
        // 구현 필요
    }

    public void updateRecipeReview(Review review) throws Exception {
        // 구현 필요
    }

    public long deleteRecipeReview(long reviewId) throws Exception {
        String sql = "DELETE FROM review WHERE review_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, reviewId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewId;
    }
}
