package nl.appli.cookbook.auth.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@recipeSecurityService.hasPermissionForRecipeId(authentication.name, #id) || hasAuthority('ADMIN')")
public @interface IsRecipeIdInChefsCookbook {
}
