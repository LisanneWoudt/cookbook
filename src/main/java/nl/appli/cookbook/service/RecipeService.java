package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.RecipeRepository;
import nl.appli.cookbook.domain.EstimatedTime;
import nl.appli.cookbook.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getRecipeCategories(Long cookbookId) {
        return this.recipeRepository.findByCookbookId(cookbookId).stream()
                .flatMap(recipe -> recipe.getCategories().stream())
                .collect(Collectors.toList());
    }

    public Recipe saveRecipe(Recipe recipe) {
        EstimatedTime estimatedTime = recipe.getEstimatedTime();
        estimatedTime.setRecipe(recipe);
        return this.recipeRepository.save(recipe);
    }


    public void deleteRecipe(Long id) {
        this.recipeRepository.deleteById(id);
    }

}
