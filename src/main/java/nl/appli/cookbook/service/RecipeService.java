package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.RecipeRepository;
import nl.appli.cookbook.domain.Recipe;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipe(Long id) {
        return this.recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Recipe with id " + id + " not found"));
    }

    public Recipe addRecipe(Recipe recipe) {
        return this.recipeRepository.save(recipe);
    }

}
