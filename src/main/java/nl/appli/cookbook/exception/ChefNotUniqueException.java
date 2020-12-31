package nl.appli.cookbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Temp response status because message not showing in api response. Why not?
@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class ChefNotUniqueException extends RuntimeException {

    private static final long serialVersionUID = 7188771233198126375L;

    public ChefNotUniqueException(String message) {
        System.out.println(message);
    }

}
