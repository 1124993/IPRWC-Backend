package nl.hsleiden.ammaris.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates exceptions into consistent JSON errors for clients.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        for (var err : ex.getBindingResult().getAllErrors()) {
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            details.put(field, message);
        }
        return ResponseEntity.badRequest().body(Map.of(
                "error", "validation_failed",
                "details", details
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "bad_request",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        // In dev you might log stacktrace. Keep the response simple.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "internal_error",
                "message", "Something went wrong"
        ));
    }
}
