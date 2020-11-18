package nl.appli.cookbook.dao;

import nl.appli.cookbook.domain.Cookbook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookbookRepository extends CrudRepository<Cookbook, Long> {

    List<Cookbook> findByChefId(Long chefId);

}
