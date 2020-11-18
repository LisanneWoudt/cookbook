package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.CookbookRepository;
import nl.appli.cookbook.domain.Cookbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookbookService {

    private final CookbookRepository cookbookRepository;

    public CookbookService(CookbookRepository cookbookRepository) {
        this.cookbookRepository = cookbookRepository;
    }

    public Cookbook getCookbook(Long id) {
        return this.cookbookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cookbook with id " + id + " not found"));
    }

    public Cookbook addCookbook(Cookbook cookbook) {
        return this.cookbookRepository.save(cookbook);
    }

    public List<Cookbook> findCookbookByChefId(Long chefId) {
        return this.cookbookRepository.findByChefId(chefId);
    }

}
