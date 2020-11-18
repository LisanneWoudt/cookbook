package nl.appli.cookbook.web;

import nl.appli.cookbook.domain.Cookbook;
import nl.appli.cookbook.service.CookbookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "cookbooks/")
public class CookbookController {

    private final CookbookService cookbookService;

    public CookbookController(CookbookService cookbookService) {
        this.cookbookService = cookbookService;
    }

    @RequestMapping(method = GET, value = "{id}")
    public Cookbook getCookbook(@PathVariable Long id) {
        return this.cookbookService.getCookbook(id);
    }

    @RequestMapping(method = POST, value = "/add")
    public Cookbook addCookbook(@RequestBody Cookbook cookbook) {
        return this.cookbookService.addCookbook(cookbook);
    }

    @RequestMapping(method = GET, value = "/by-chefId")
    public List<Cookbook> findCookbookByChefId(@RequestParam Long chefId) {
        return this.cookbookService.findCookbookByChefId(chefId);
    }
}
