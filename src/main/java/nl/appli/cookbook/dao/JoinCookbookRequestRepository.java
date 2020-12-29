package nl.appli.cookbook.dao;

import nl.appli.cookbook.domain.JoinCookbookRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinCookbookRequestRepository extends CrudRepository<JoinCookbookRequest, Long> {

    Optional<JoinCookbookRequest> findByChefIdAndCookbookId(long chefId, long cookbookId);

    List<JoinCookbookRequest> findByCookbookId(long cookbookId);
}
