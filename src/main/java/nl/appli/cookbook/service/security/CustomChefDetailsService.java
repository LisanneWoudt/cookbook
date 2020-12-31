package nl.appli.cookbook.service.security;

import nl.appli.cookbook.dao.ChefRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomChefDetailsService implements UserDetailsService {

    private ChefRepository chefs;

    public CustomChefDetailsService(ChefRepository chefs) {
        this.chefs = chefs;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.chefs.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}
