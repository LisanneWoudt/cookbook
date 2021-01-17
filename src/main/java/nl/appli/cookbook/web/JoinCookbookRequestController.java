package nl.appli.cookbook.web;

import nl.appli.cookbook.auth.annotations.IsAuthorizedChefId;
import nl.appli.cookbook.auth.annotations.IsRequestChef;
import nl.appli.cookbook.domain.JoinCookbookRequest;
import nl.appli.cookbook.service.JoinCookbookRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "requests/")
@PreAuthorize("isAuthenticated()")
public class JoinCookbookRequestController {

    private final JoinCookbookRequestService joinCookbookRequestService;

    public JoinCookbookRequestController(JoinCookbookRequestService joinCookbookRequestService) {
        this.joinCookbookRequestService = joinCookbookRequestService;
    }

    @IsRequestChef
    @RequestMapping(method = POST, value = "save")
    public JoinCookbookRequest saveRequest(@RequestBody JoinCookbookRequest request) {
        return this.joinCookbookRequestService.saveRequest(request);
    }

    @IsAuthorizedChefId
    @RequestMapping(method = GET, value = "check")
    public JoinCookbookRequest checkRequestSent(@RequestParam Long id, @RequestParam Long cookbookId) {
        return this.joinCookbookRequestService.checkRequestSent(id, cookbookId);
    }

    @IsAuthorizedChefId
    @RequestMapping(method = GET, value = "count")
    public long getRequestCount(@RequestParam Long id) {
        return this.joinCookbookRequestService.getRequestCount(id);
    }

    @IsAuthorizedChefId
    @RequestMapping(method = GET, value = "by-chefId")
    public List<JoinCookbookRequest> getRequestsByChefId(@RequestParam Long id) {
        return this.joinCookbookRequestService.getRequestsByChefId(id);
    }
}
