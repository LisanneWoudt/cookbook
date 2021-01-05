package nl.appli.cookbook.service.security;

import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.service.ChefService;
import org.springframework.stereotype.Component;

@Component("chefSecurityService")
public class ChefSecurityService {

    private final ChefService chefService;

    public ChefSecurityService(ChefService chefService) {
        this.chefService = chefService;
    }

    public boolean hasPermission(String name, Long id) {
        if (id != null) {
            Chef chef = chefService.getChef(id);
            return chef.getUsername().equals(name);
        }
        return false;
    }

}
