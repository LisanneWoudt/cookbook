package nl.appli.cookbook.dao;

import nl.appli.cookbook.domain.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Collection<Recipe> findByCookbookId(long cookbookId);

    @Query("SELECT r.id FROM RECIPE r " +
            "INNER JOIN r.categories c " +
            "WHERE c = :category " +
            "AND r.id IN :recipeIds")
    List<Long> getRecipeIdsByCategory(String category, List<Long> recipeIds);
}
