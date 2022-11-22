package kristina.document.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyNotOwnerException extends RuntimeException {
    public MyNotOwnerException(String message) {
        super(message);
    }

    public MyNotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}