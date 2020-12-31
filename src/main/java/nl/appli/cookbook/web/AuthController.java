package nl.appli.cookbook.web;

import nl.appli.cookbook.auth.AuthenticationRequest;
import nl.appli.cookbook.auth.JwtTokenProvider;
import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.service.ChefService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth/")
@PreAuthorize("permitAll()")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChefService chefService;

    public AuthController(JwtTokenProvider jwtTokenProvider, ChefService chefService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.chefService = chefService;
    }

    @PostMapping("signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {
        Chef chef = chefService.authenticateUser(data);

        try {
            String username = chef.getUsername();
            return getAuthorizationData(chef, username);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("register")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody Chef data) {
        Chef chef = chefService.addChef(data);
        String username = chef.getUsername();
        try {
            return getAuthorizationData(chef, username);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    private ResponseEntity<Map<Object, Object>> getAuthorizationData(Chef chef, String username) {
        String token = jwtTokenProvider.createToken(username, chef.getRoles());
        Map<Object, Object> model = new HashMap<>();
        model.put("id", chef.getId());
        model.put("username", username);
        model.put("token", token);
        return ok(model);
    }
}
