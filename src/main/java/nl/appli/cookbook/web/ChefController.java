package nl.appli.cookbook.web;

import nl.appli.cookbook.auth.annotations.IsAdmin;
import nl.appli.cookbook.auth.annotations.IsAuthorizedChefId;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.service.ChefService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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

    @RequestMapping(method = POST, value = "add")
    public Chef addChef(@RequestBody Chef chef) {
        return chefService.addChef(chef);
    }

    @RequestMapping(method = PUT, value = "set-cookbook-id")
    public Chef setLastSelectedCookbookId(@RequestBody Chef chef) {
        return chefService.setLastSelectedCookbookId(chef);
    }

    @RequestMapping(method = GET, value = "add-cookbook")
    public Chef addCookbookToChef(@RequestParam String chefId,
                                  @RequestParam String cookbookId) {
        return chefService.addCookbookToChef(Long.valueOf(chefId), Long.valueOf(cookbookId));
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(method = GET, value = "minimal")
    public List<Chef> getMinimalChefs() {
        return chefService.getMinimalChefs();
    }

}
