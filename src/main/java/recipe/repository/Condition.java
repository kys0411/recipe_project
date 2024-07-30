package recipe.repository;

enum Condition {
    LIKES("""
            select r.recipe_id, r.recipe_name, r.difficulty, count(likes_id) as sorting
            from recipe r left join likes l
            on r.recipe_id = l.recipe_id
            and l.status = 1
            group by r.recipe_id, r.recipe_name, r.difficulty
            order by sorting desc
            """),
    REVIEWS("""
            select r.recipe_id, r.recipe_name, r.difficulty, count(review_id) as sorting
            from recipe r left join review v
            on r.recipe_id = v.recipe_id
            group by r.recipe_id, r.recipe_name, r.difficulty
            order by sorting desc
            """),
    RATINGS("""
            select r.recipe_id, r.recipe_name, r.difficulty, nvl(avg(rating), 0) as sorting
            from recipe r left join review v
            on r.recipe_id = v.recipe_id
            group by r.recipe_id, r.recipe_name, r.difficulty
            order by sorting desc
            """);

    private final String sql;

    Condition(String sql) {
        this.sql = sql;
    }

    public static String getSql(String cond) {
        for (Condition condition : Condition.values()) {
            if (condition.name().equals(cond)) {
                return condition.sql;
            }
        }
        throw new IllegalArgumentException("잘못된 조건입니다");
    }
}
