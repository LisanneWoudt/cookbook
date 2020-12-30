package nl.appli.cookbook.service;

import nl.appli.cookbook.dao.JoinCookbookRequestRepository;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.domain.JoinCookbookRequest;
import nl.appli.cookbook.domain.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoinCookbookRequestService {

    private final JoinCookbookRequestRepository joinCookbookRequestRepository;
    private final ChefService chefService;

    public JoinCookbookRequestService(JoinCookbookRequestRepository joinCookbookRequestRepository, ChefService chefService) {
        this.joinCookbookRequestRepository = joinCookbookRequestRepository;
        this.chefService = chefService;
    }

    public JoinCookbookRequest saveRequest(JoinCookbookRequest request) {
        if (Status.ACCEPTED.equals(request.getStatus())) {
            chefService.addCookbookToChef(request.getChefId(), request.getCookbookId());
        }
        return joinCookbookRequestRepository.save(request);
    }

    public long getRequestCount(Long chefId) {
        Chef chef = chefService.getChef(chefId);
        return chef.getCookbooks().stream()
                .flatMap(cookbook -> joinCookbookRequestRepository.findByCookbookId(cookbook.getId())
                        .stream())
                .filter(request -> request.getStatus().equals(Status.NEW))
                .count();
    }

    public List<JoinCookbookRequest> getRequestsByChefId(Long chefId) {
        Chef chef = chefService.getChef(chefId);
        return chef.getCookbooks().stream()
                .flatMap(cookbook -> joinCookbookRequestRepository.findByCookbookId(cookbook.getId())
                        .stream())
                .filter(request -> request.getStatus().equals(Status.NEW))
                .collect(Collectors.toList());
    }

    public JoinCookbookRequest checkRequestSent(Long chefId, Long cookbookId) {
        return joinCookbookRequestRepository.findByChefIdAndCookbookId(chefId, cookbookId)
                .filter(request -> Status.NEW.equals(request.getStatus()) || Status.REJECTED.equals(request.getStatus()))
                .orElse(null);
    }
}
