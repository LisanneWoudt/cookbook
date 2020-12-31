package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@Entity(name = "CHEF")
public class Chef implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    @Column(name = "LAST_SELECTED_COOKBOOK_ID")
    private Long lastSelectedCookbookId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "PASSWORD_RESET")
    private Boolean passwordReset;

    @Column(name = "EMAIL_NOTIFICATIONS")
    private boolean emailNotifications;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "CHEF_COOKBOOK",
            joinColumns = { @JoinColumn(name = "CHEF_ID") },
            inverseJoinColumns = { @JoinColumn(name = "COOKBOOK_ID") }
    )
    private List<Cookbook> cookbooks;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<String> roles;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
