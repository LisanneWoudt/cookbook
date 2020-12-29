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
        return joinCookbookRequestRepository.save(request);
    }

    public long getRequestCount(Long chefId) {
        Chef chef = chefService.getChef(chefId);
        return chef.getCookbooks().stream()
                .mapToLong(cookbook -> joinCookbookRequestRepository.findByCookbookId(cookbook.getId()).size())
                .sum();
    }

    public List<JoinCookbookRequest> getRequestsByChefId(Long chefId) {
        Chef chef = chefService.getChef(chefId);
        return chef.getCookbooks().stream()
                .flatMap(cookbook -> joinCookbookRequestRepository.findByCookbookId(cookbook.getId())
                        .stream())
                .collect(Collectors.toList());
    }

    public boolean checkRequestSent(Long chefId, Long cookbookId) {
        return joinCookbookRequestRepository.findByChefIdAndCookbookId(chefId, cookbookId)
                .filter(request -> request.getStatus().equals(Status.NEW))
                .isPresent();
    }
}
