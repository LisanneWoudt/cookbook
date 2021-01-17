package nl.appli.cookbook.service.security;

import nl.appli.cookbook.domain.Recipe;
import nl.appli.cookbook.service.ChefService;
import nl.appli.cookbook.service.RecipeService;
import org.springframework.stereotype.Service;

@Service
public class RecipeSecurityService {

    private final ChefService chefService;
    private final RecipeService recipeService;

    public RecipeSecurityService(ChefService chefService, RecipeService recipeService) {
        this.chefService = chefService;
        this.recipeService = recipeService;
    }

    public boolean hasPermissionForRecipe(String name, Recipe recipe) {
        return name.equals(chefService.findChefByCookbookId(recipe.getCookbookId()).getUsername());
    }

    public boolean hasPermissionForRecipeId(String name, Long id) {
        return this.hasPermissionForRecipe(name, recipeService.getRecipe(id));
    }

}
