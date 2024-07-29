package review.impl;

import oracle.jdbc.driver.OracleDriver;
import review.Dao.ReviewDao;
import review.domain.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewDaoImpl implements ReviewDao {

    // 데이터베이스 연결을 가져오는 메서드
    private static Connection getConnection() throws Exception {
        Map<String, String> env = System.getenv();

        OracleDriver driver = new OracleDriver();
        DriverManager.registerDriver(driver);

        Class.forName("oracle.jdbc.OracleDriver");
        String url = env.get("DB_URL");
        String id = env.get("DB_ID");
        String pass = env.get("DB_PASS");

        return DriverManager.getConnection(url, id, pass);
    }

    @Override
    public List<Review> selectMemberReview(long memberId) throws Exception {
        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT r.review_id, r.member_id, r.recipe_id, c.recipe_name, m.nickname, r.rating, r.content, r.review_date " +
                "FROM member m, review r, recipe c " +
                "WHERE r.member_id = m.member_id(+) AND r.recipe_id = c.recipe_id(+) AND r.member_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

             pstmt.setLong(1,memberId);
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getLong("review_id"));
                review.setMemberId(rs.getLong("member_id"));
                review.setRecipeId(rs.getLong("recipe_id"));
                review.setRecipeName(rs.getString("recipe_name"));
                review.setNickName(rs.getString("nickname"));
                review.setRating(rs.getLong("rating"));
                review.setContent(rs.getString("content"));
                review.setDate(rs.getDate("review_date"));

                reviews.add(review);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public void insertRecipeReview(Review review) throws Exception{

    }

    public void updateRecipeReview(Review review) throws Exception{

    }

    public void deleteRecipeReview(long reviewId) throws Exception{

    }



}
