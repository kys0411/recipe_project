package recipe.dto;

import lombok.Builder;
import lombok.Getter;

public class RecipeDto {

    @Getter
    @Builder
    public static class FindAll {
        private final Long id;
        private final String title;
        private final String difficulty;
        private final String sorting;
    }
}
