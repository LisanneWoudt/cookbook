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
    private final EmailService emailService;
    private final CookbookService cookbookService;

    public JoinCookbookRequestService(JoinCookbookRequestRepository joinCookbookRequestRepository, ChefService chefService, EmailService emailService, CookbookService cookbookService) {
        this.joinCookbookRequestRepository = joinCookbookRequestRepository;
        this.chefService = chefService;
        this.emailService = emailService;
        this.cookbookService = cookbookService;
    }

    public JoinCookbookRequest saveRequest(JoinCookbookRequest request) {
        Chef chef;
        switch(request.getStatus()) {
            case ACCEPTED:
                chefService.addCookbookToChef(request.getChefId(), request.getCookbookId());
                chef = chefService.getChef(request.getChefId());
                emailService.sendRequestAcceptedMail(request, chef);
                break;
            case REJECTED:
                chef = chefService.getChef(request.getChefId());
                emailService.sendRequestRejectedMail(request, chef);
                break;
            case NEW:
                Long chefId = cookbookService.getCookbook(request.getCookbookId()).getCreatorId();
                chef = chefService.getChef(chefId);
                emailService.sendNewRequestMail(request, chef);
                break;
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
