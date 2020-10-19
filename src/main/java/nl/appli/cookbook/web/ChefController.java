package nl.appli.cookbook.web;

import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.service.ChefService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "chefs/")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @RequestMapping(method = GET, value = "{id}")
    public Chef getChef(@PathVariable  Long id) {
        return chefService.getChef(id);
    }

    @RequestMapping(method = POST, value = "/add")
    public Chef addChef(@RequestBody Chef chef) {
        return chefService.addChef(chef);
    }

}
