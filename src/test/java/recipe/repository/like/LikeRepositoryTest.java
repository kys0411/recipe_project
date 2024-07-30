package recipe.repository.like;

import common.DBConnection;
import like.constant.Status;
import like.repository.LikeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.SQLException;

public class LikeRepositoryTest {
    DBConnection dbConnection = new DBConnection();
    LikeRepository likeRepository = new LikeRepository(dbConnection);

    @Test
    @DisplayName("좋아요 테스트 - 좋아요 기능 실행 시 주어진 값에 해당하면 예외가 발생하지 않는다.")
    public void upsert_Like_Data_Success() throws SQLException, ClassNotFoundException {
        Status status = likeRepository.upsertLikeInfo(19L, 12L);

        Assertions.assertThat(status)
                .isNotNull()
                .isIn(Status.LIKE, Status.UNLIKE);
    }

}
