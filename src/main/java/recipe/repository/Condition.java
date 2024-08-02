package recipe.repository;

import lombok.Getter;

@Getter
enum Condition {
    LIKES("좋아요순", """
            select r.recipe_id, r.recipe_name, r.difficulty, count(likes_id) as sorting
            from recipe r left join likes l
            on r.recipe_id = l.recipe_id
            and l.status = 1
            group by r.recipe_id, r.recipe_name, r.difficulty
            order by sorting desc
            """),
    REVIEWS("리뷰순", """
            select r.recipe_id, r.recipe_name, r.difficulty, count(review_id) as sorting
            from recipe r left join review v
            on r.recipe_id = v.recipe_id
            group by r.recipe_id, r.recipe_name, r.difficulty
            order by sorting desc
            """),
    RATINGS("별점순", """
            select r.recipe_id, r.recipe_name, r.difficulty, nvl(avg(rating), 0) as sorting
            from recipe r left join review v
            on r.recipe_id = v.recipe_id
            group by r.recipe_id, r.recipe_name, r.difficulty
            order by sorting desc
            """);

    private final String description;
    private final String sql;

    Condition(String description, String sql) {
        this.description = description;
        this.sql = sql;
    }

    public static String getSql(String cond) {
        for (Condition condition : Condition.values()) {
            if (condition.getDescription().equals(cond)) {
                return condition.sql;
            }
        }
        throw new IllegalArgumentException("잘못된 조건입니다");
    }
}
