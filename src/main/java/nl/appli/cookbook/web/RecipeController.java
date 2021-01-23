package nl.appli.cookbook.web;

import nl.appli.cookbook.auth.annotations.IsRecipeIdInChefsCookbook;
import nl.appli.cookbook.auth.annotations.IsRecipeInChefsCookbook;
import nl.appli.cookbook.domain.Recipe;
import nl.appli.cookbook.service.RecipeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "recipes/")
@PreAuthorize("isAuthenticated()")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(method = GET, value = "{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return this.recipeService.getRecipe(id);
    }

    @IsRecipeInChefsCookbook
    @RequestMapping(method = POST, value = "save")
    public Recipe saveRecipe(@RequestBody Recipe recipe) {
        return this.recipeService.saveRecipe(recipe);
    }

    @IsRecipeIdInChefsCookbook
    @RequestMapping(method = DELETE, value = "{id}")
    public void deleteRecipe(@PathVariable Long id) {
        this.recipeService.deleteRecipe(id);
    }

    @RequestMapping(method = GET, value = "categories")
    public List<String> getRecipeCategories(@RequestParam long cookbookId) {
        return this.recipeService.getRecipeCategories(cookbookId);
    }

    @RequestMapping(method = GET, value = "ids-per-category")
    public Map<String, List<Long>> getRecipeIdsPerCategory(@RequestParam long cookbookId) {
        return this.recipeService.getRecipeIdsPerCategory(cookbookId);
    }

}
