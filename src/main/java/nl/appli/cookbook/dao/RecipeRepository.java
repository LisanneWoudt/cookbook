package nl.appli.cookbook.dao;

import nl.appli.cookbook.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Collection<Recipe> findByCookbookId(long cookbookId);
}
