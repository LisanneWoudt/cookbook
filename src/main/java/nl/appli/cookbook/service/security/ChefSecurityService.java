package nl.appli.cookbook.service.security;

import nl.appli.cookbook.service.ChefService;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ChefSecurityService {

    private final ChefService chefService;

    public ChefSecurityService(ChefService chefService) {
        this.chefService = chefService;
    }

    public boolean hasPermission(String name, Long id) {
        if (id != null) {
            return name.equals(chefService.getChef(id).getUsername());
        }
        return false;
    }

    public boolean hasPermission(String name) {
        return !isNull(name) && !name.isBlank();
    }

}
