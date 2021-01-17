package nl.appli.cookbook.service.security;

import nl.appli.cookbook.domain.JoinCookbookRequest;
import nl.appli.cookbook.service.ChefService;
import org.springframework.stereotype.Service;

@Service
public class RequestSecurityService {

    private final ChefService chefService;

    public RequestSecurityService(ChefService chefService) {
        this.chefService = chefService;
    }

    public boolean hasPermission(String name, JoinCookbookRequest request) {
        switch(request.getStatus()) {
            case ACCEPTED:
            case REJECTED:
                return name.equals(chefService.findChefByCookbookId(request.getCookbookId()).getUsername());
            case NEW:
                return name.equals(chefService.getChef(request.getChefId()).getUsername());
        }
        return false;
    }
}
