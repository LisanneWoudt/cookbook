package nl.appli.cookbook.web;

import nl.appli.cookbook.auth.annotations.IsAdmin;
import nl.appli.cookbook.auth.annotations.IsAuthorizedChef;
import nl.appli.cookbook.auth.annotations.IsAuthorizedChefId;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.service.ChefService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "chefs/")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @IsAdmin
    @RequestMapping(method = GET, value = "all")
    public List<Chef> getAllChefs() {
        return chefService.getAllChefs();
    }

    @IsAuthorizedChefId
    @RequestMapping(method = GET, value = "{id}")
    public Chef getChef(@PathVariable Long id) {
        return chefService.getChef(id);
    }

    @IsAuthorizedChef
    @RequestMapping(method = PUT, value = "update")
    public Chef updateChef(@RequestBody Chef chef) {
        return chefService.saveChef(chef);
    }

    @IsAuthorizedChef
    @RequestMapping(method = PUT, value = "set-cookbook-id")
    public Chef setLastSelectedCookbookId(@RequestBody Chef chef) {
        return chefService.setLastSelectedCookbookId(chef);
    }

    @IsAuthorizedChef
    @RequestMapping(method = PUT, value = "set-email-notifications")
    public void setEmailNotifications(@RequestBody Chef chef) {
        chefService.setEmailNotifications(chef);
    }

    @IsAuthorizedChefId
    @RequestMapping(method = GET, value = "add-cookbook")
    public Chef addCookbookToChef(@RequestParam String id,
                                  @RequestParam String cookbookId) {
        return chefService.addCookbookToChef(Long.valueOf(id), Long.valueOf(cookbookId));
    }

    @RequestMapping(method = GET, value = "minimal")
    @PreAuthorize("isAuthenticated()")
    public List<Chef> getMinimalChefs() {
        return chefService.getMinimalChefs();
    }

    @PostMapping(value ="reset-password")
    public void resetPassword(@RequestBody Chef chef) {
        chefService.resetPassword(chef);
    }

}
