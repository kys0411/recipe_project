package recipe.repository;

import lombok.Getter;

@Getter
enum Condition {
    LIKES("좋아요순"),
    REVIEWS("리뷰순"),
    RATINGS("별점순");

    private final String description;

    Condition(String description) {
        this.description = description;
    }

    public static String getCondition(String cond) {
        for (Condition condition : Condition.values()) {
            if (condition.getDescription().equals(cond)) {
                return condition.name();
            }
        }
        throw new IllegalArgumentException("잘못된 조건입니다");
    }
}
