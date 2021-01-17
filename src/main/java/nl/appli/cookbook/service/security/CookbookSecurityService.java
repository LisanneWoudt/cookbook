package nl.appli.cookbook.service.security;

import nl.appli.cookbook.domain.Cookbook;
import nl.appli.cookbook.service.ChefService;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class CookbookSecurityService {

    private final ChefService chefService;

    public CookbookSecurityService(ChefService chefService) {
        this.chefService = chefService;
    }

    public boolean hasPermission(String name, Cookbook cookbook) {
        Long chefId = cookbook.getCreatorId();
        if (!isNull(chefId)) {
            return name.equals(chefService.getChef(chefId).getUsername());
        }
        return false;
    }
}
