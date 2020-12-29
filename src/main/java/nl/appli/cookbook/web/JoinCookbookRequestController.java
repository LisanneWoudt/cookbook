package nl.appli.cookbook.web;

import nl.appli.cookbook.domain.JoinCookbookRequest;
import nl.appli.cookbook.service.JoinCookbookRequestService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "requests/")
public class JoinCookbookRequestController {

    private final JoinCookbookRequestService joinCookbookRequestService;

    public JoinCookbookRequestController(JoinCookbookRequestService joinCookbookRequestService) {
        this.joinCookbookRequestService = joinCookbookRequestService;
    }

    @RequestMapping(method = POST, value = "save")
    public JoinCookbookRequest saveRequest(@RequestBody JoinCookbookRequest request) {
        return this.joinCookbookRequestService.saveRequest(request);
    }

    @RequestMapping(method = GET, value = "check")
    public boolean checkRequestSent(@RequestParam Long chefId, @RequestParam Long cookbookId) {
        return this.joinCookbookRequestService.checkRequestSent(chefId, cookbookId);
    }

    @RequestMapping(method = GET, value = "count")
    public long getRequestCount(@RequestParam Long chefId) {
        return this.joinCookbookRequestService.getRequestCount(chefId);
    }

    @RequestMapping(method = GET, value = "by-chefId")
    public List<JoinCookbookRequest> getRequestsByChefId(@RequestParam Long chefId) {
        return this.joinCookbookRequestService.getRequestsByChefId(chefId);
    }
}
