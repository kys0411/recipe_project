package like.service;

import like.constant.Status;
import like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class UpsertLikeService {
    private final LikeRepository likeRepository;

    public Status upsertLike(Long userId, Long recipeId) throws SQLException, ClassNotFoundException {
        return likeRepository.upsertLikeInfo(userId, recipeId);
    }
}
