package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.ChefRepository;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.domain.Cookbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    private final ChefRepository chefRepository;
    private final CookbookService cookbookService;

    public ChefService(ChefRepository chefRepository, CookbookService cookbookService) {
        this.chefRepository = chefRepository;
        this.cookbookService = cookbookService;
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

    public Chef addCookbookToChef(Long chefId, Long cookbookId) {
        Chef chef = getChef(chefId);
        Cookbook cookbook = cookbookService.getCookbook(cookbookId);
        cookbook.getChefs().add(chef);
        chef.getCookbooks().add(cookbook);
        this.cookbookService.addCookbook(cookbook);
        chef.setLastSelectedCookbookId(cookbook.getId());
        return this.chefRepository.save(chef);
    }

    public List<Cookbook> findCookbookByChefId(Long chefId) {
        return getChef(chefId).getCookbooks();
    }

}
