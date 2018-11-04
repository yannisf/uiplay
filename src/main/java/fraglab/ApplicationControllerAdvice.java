package fraglab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.badRequest().body(String.format("Validation error: %s", e.getMessage()));
    }

}