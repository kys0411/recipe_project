package recipe.dto;

import lombok.Builder;

public class RecipeDto {
    @Builder
    public static class FindAll {
        private final Long id;
        private final String title;
        private final String difficulty;
        private final String sorting;
    }
}
