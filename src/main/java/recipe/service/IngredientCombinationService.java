package recipe.service;

import lombok.RequiredArgsConstructor;
import recipe.domain.Recipe;
import recipe.repository.RecipeSelectRepository;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
public class IngredientCombinationService {
    private final RecipeSelectRepository recipeSelectRepository;

    public List<Recipe> ingredientCombination(String[] ingredientsArray) {
        try {
            return recipeSelectRepository.getIngredientCombinationRecipeInfo(ingredientsArray);
        } catch (SQLException | ClassNotFoundException e) {
            // todo: 예외 로직 처리
        }
        return List.of();
    }
}
