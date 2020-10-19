package nl.appli.cookbook.dao;

import nl.appli.cookbook.domain.Chef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {
}
