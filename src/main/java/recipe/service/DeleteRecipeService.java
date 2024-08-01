package recipe.service;

import lombok.RequiredArgsConstructor;
import recipe.repository.RecipeRepository;

import java.sql.SQLException;

@RequiredArgsConstructor
public class DeleteRecipeService {
	private final RecipeRepository recipeRepository;

	public boolean delete(Long id) {
		try {
			recipeRepository.deleteById(id);
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			return false;
		}
	}
}
