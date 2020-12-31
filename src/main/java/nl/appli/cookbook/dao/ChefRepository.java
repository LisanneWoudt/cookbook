package nl.appli.cookbook.dao;

import nl.appli.cookbook.domain.Chef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {

    Optional<Chef> findByUsername(String username);

    Optional<Chef> findByEmailIgnoreCase(String email);
}
