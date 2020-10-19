package nl.appli.cookbook.web;

import nl.appli.cookbook.domain.Recipe;
import nl.appli.cookbook.service.RecipeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "recipes/")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(method = GET, value = "{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return this.recipeService.getRecipe(id);
    }

    @RequestMapping(method = POST, value = "/add")
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return this.recipeService.addRecipe(recipe);
    }
}
