package nl.appli.cookbook.web;

import nl.appli.cookbook.auth.annotations.IsAuthorizedChefId;
import nl.appli.cookbook.auth.annotations.IsCookbookOwner;
import nl.appli.cookbook.auth.annotations.IsCookbookOwnerId;
import nl.appli.cookbook.domain.Cookbook;
import nl.appli.cookbook.service.ChefService;
import nl.appli.cookbook.service.CookbookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "cookbooks/")
@PreAuthorize("isAuthenticated()")
public class CookbookController {

    private final CookbookService cookbookService;
    private final ChefService chefService;

    public CookbookController(CookbookService cookbookService, ChefService chefService) {
        this.cookbookService = cookbookService;
        this.chefService = chefService;
    }

    @RequestMapping(method = GET, value = "{id}")
    public Cookbook getCookbook(@PathVariable Long id) {
        return this.cookbookService.getCookbook(id);
    }

    @IsCookbookOwner
    @RequestMapping(method = POST, value = "/save")
    public Cookbook saveCookbook(@RequestBody Cookbook cookbook) {
        return this.cookbookService.saveCookbook(cookbook);
    }

    @IsCookbookOwnerId
    @RequestMapping(method = DELETE, value = "{id}")
    public void deleteCookbook(@PathVariable Long id) {
        this.cookbookService.deleteCookbook(id);
    }

    @IsAuthorizedChefId
    @RequestMapping(method = GET, value = "/by-chefId")
    public List<Cookbook> findCookbookByChefId(@RequestParam Long id) {
        return this.chefService.findCookbookByChefId(id);
    }
}
