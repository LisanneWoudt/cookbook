package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.ChefRepository;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.domain.Cookbook;
import nl.appli.cookbook.exception.ChefNotUniqueException;
import nl.appli.cookbook.exception.EmailNotUniqueException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ChefService {

    private final ChefRepository chefRepository;
    private final CookbookService cookbookService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ChefService(ChefRepository chefRepository, CookbookService cookbookService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.chefRepository = chefRepository;
        this.cookbookService = cookbookService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

    public Chef saveChef(Chef chef) {
        Optional<Chef> optExistingChef = chefRepository.findById(chef.getId());
        if (optExistingChef.isPresent() && !isNull(chef.getPassword())) {
            chef.setPassword(passwordEncoder.encode(chef.getPassword()));
        } else {
            checkUsernameExists(chef);
            checkEmailExists(chef);
        }
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

    public void setEmailNotifications(Chef chef) {
        Chef updatedChef = getChef(chef.getId());
        updatedChef.setEmailNotifications(chef.isEmailNotifications());
        chefRepository.save(updatedChef);
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

    public Chef findChefByCookbookId(Long cookbookId) {
        Cookbook cookbook = cookbookService.getCookbook(cookbookId);
        return this.getChef(cookbook.getCreatorId());
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

    public void resetPassword(Chef chef) {
        Optional<Chef> existingChef = chefRepository.findByEmailIgnoreCase(chef.getEmail());
        existingChef.ifPresent(this::resetPasswordAndInformUser);
    }

    private void resetPasswordAndInformUser(Chef chef) {
        String generatedPassword =  RandomStringUtils.random(7, true, true);
        chef.setPassword(passwordEncoder.encode(generatedPassword));
        chef.setPasswordReset(true);
        chefRepository.save(chef);
        emailService.sendPasswordResetMessage(chef, generatedPassword);
    }

}
