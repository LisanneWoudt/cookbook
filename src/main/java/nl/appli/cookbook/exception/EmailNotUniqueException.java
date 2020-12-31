package nl.appli.cookbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Temp response status because message not showing in api response. Why not?
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class EmailNotUniqueException extends RuntimeException {

    private static final long serialVersionUID = 2177814051826052781L;

    public EmailNotUniqueException(String message) {
        super(message);
    }
}
