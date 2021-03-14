package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.CookbookRepository;
import nl.appli.cookbook.domain.Cookbook;
import org.springframework.stereotype.Service;

@Service
public class CookbookService {

    private final CookbookRepository cookbookRepository;
    private final ImageService imageService;

    public CookbookService(CookbookRepository cookbookRepository, ImageService imageService) {
        this.cookbookRepository = cookbookRepository;
        this.imageService = imageService;
    }

    public Cookbook getCookbook(Long id) {
        return this.cookbookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cookbook with id " + id + " not found"));
    }

    public Cookbook saveCookbook(Cookbook cookbook) {
        return this.cookbookRepository.save(cookbook);
    }

    public void deleteCookbook(Long id) {
        Cookbook cookbook = this.cookbookRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cookbook with id " + id + " not found"));
        // Remove cookbook from JoinTable CHEF_COOKBOOK
        cookbook.removeCookbook(cookbook.getChefs());
        // Remove recipe images
        cookbook.getRecipes().forEach(recipe -> imageService.deleteImages(String.valueOf(recipe.getId()), recipe.getImageCount()));
        // Remove cookbook including all recipes
        this.cookbookRepository.deleteById(id);
    }

}
