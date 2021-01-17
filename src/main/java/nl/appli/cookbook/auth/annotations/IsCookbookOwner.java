package nl.appli.cookbook.auth.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@cookbookSecurityService.hasPermission(authentication.name, #cookbook) || hasAuthority('ADMIN')")
public @interface IsCookbookOwner {
}
