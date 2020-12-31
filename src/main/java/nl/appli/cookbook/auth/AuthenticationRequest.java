package nl.appli.cookbook.auth;

import lombok.*;
import nl.appli.cookbook.domain.Chef;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest extends Chef implements Serializable {

    private static final long serialVersionUID = -6986746375915710855L;
    private String username;
    private String password;
    private String email;
}
