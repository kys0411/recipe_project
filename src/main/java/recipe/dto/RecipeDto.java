package recipe.dto;

import lombok.Builder;
import lombok.Getter;

public class RecipeDto {

    @Getter
    @Builder
    public static class FindAll {
        private final Long id;
        private final String title;
        private final String description;
        private final String difficulty;
        private final int likes;
        private final int reviews;
        private final double rating;
        private final String createdAt;
    }
}
