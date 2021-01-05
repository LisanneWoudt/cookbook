package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.ChefRepository;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.domain.Cookbook;
import nl.appli.cookbook.exception.ChefNotUniqueException;
import nl.appli.cookbook.exception.EmailNotUniqueException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChefService {

    private final ChefRepository chefRepository;
    private final CookbookService cookbookService;
    private final PasswordEncoder passwordEncoder;

    public ChefService(ChefRepository chefRepository, CookbookService cookbookService, PasswordEncoder passwordEncoder) {
        this.chefRepository = chefRepository;
        this.cookbookService = cookbookService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Chef> getAllChefs() {
        List<Chef> allChefs = new ArrayList<>();
        this.chefRepository.findAll().forEach(allChefs::add);
        return allChefs;
    }

    public Chef getChef(Long id) {
        return this.chefRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Chef with id " + id + " not found"));
    }

    public Chef addChef(Chef chef) {
        checkUsernameExists(chef);
        checkEmailExists(chef);
        chef.setPassword(passwordEncoder.encode(chef.getPassword()));
        return this.chefRepository.save(chef);
    }

    private void checkUsernameExists(Chef chef) {
        if (chefRepository.findByUsername(chef.getUsername()).isPresent()) {
            throw new ChefNotUniqueException("ChefNotUniqueException: Username already in use");
        }
    }

    private void checkEmailExists(Chef chef) {
        if (chefRepository.findByEmailIgnoreCase(chef.getEmail()).isPresent()) {
            throw new EmailNotUniqueException("EmailNotUniqueException: Email already in use");
        }
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

    public Chef authenticateUser(Chef chef) {
        Optional<Chef> existingChef = chefRepository.findByEmailIgnoreCase(chef.getEmail());
        if (existingChef.isPresent()) {
            if (passwordEncoder.matches(chef.getPassword(), existingChef.get().getPassword())) {
                return existingChef.get();
            } else {
                throw new BadCredentialsException("Invalid username/password supplied");
            }
        }
        throw new BadCredentialsException("Invalid username/password supplied");
    }

    public List<Chef> getMinimalChefs() {
        List<Chef> minimalChefs = new ArrayList<>();
        getAllChefs()
                .forEach(chef -> {
                    Chef minimalChef = new Chef();
                    minimalChef.setId(chef.getId());
                    minimalChef.setUsername(chef.getUsername());
                    minimalChef.setCookbooks(chef.getCookbooks());
                    minimalChefs.add(minimalChef);
                });
        return minimalChefs;
    }

}
