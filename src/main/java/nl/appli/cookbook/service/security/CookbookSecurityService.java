package nl.appli.cookbook.service.security;

import nl.appli.cookbook.domain.Cookbook;
import nl.appli.cookbook.service.ChefService;
import nl.appli.cookbook.service.CookbookService;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class CookbookSecurityService {

    private final ChefService chefService;
    private final CookbookService cookbookService;

    public CookbookSecurityService(ChefService chefService, CookbookService cookbookService) {
        this.chefService = chefService;
        this.cookbookService = cookbookService;
    }

    public boolean hasPermission(String name, Cookbook cookbook) {
        Long chefId = cookbook.getCreatorId();
        if (!isNull(chefId)) {
            return name.equals(chefService.getChef(chefId).getUsername());
        }
        return false;
    }

    public boolean hasPermissionForId(String name, Long id) {
        Cookbook cookbook = cookbookService.getCookbook(id);
        Long chefId = cookbook.getCreatorId();
        if (!isNull(chefId)) {
            return name.equals(chefService.getChef(chefId).getUsername());
        }
        return false;
    }

}
