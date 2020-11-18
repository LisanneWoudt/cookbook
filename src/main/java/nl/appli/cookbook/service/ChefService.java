package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.ChefRepository;
import nl.appli.cookbook.domain.Chef;
import org.springframework.stereotype.Service;

@Service
public class ChefService {

    private final ChefRepository chefRepository;

    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    public Chef getChef(Long id) {
        return this.chefRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Chef with id " + id + " not found"));
    }

    public Chef addChef(Chef chef) {
        return this.chefRepository.save(chef);
    }

    public Chef setLastSelectedCookbookId(Chef chef) {
        Chef updatedChef = getChef(chef.getId());
        updatedChef.setLastSelectedCookbookId(chef.getLastSelectedCookbookId());
        chefRepository.save(updatedChef);
        return updatedChef;
    }

}
