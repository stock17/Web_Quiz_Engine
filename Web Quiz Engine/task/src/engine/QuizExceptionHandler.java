package engine;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class QuizExceptionHandler {

    private final String NOT_FOUND = "No such element in collection";

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NOT_FOUND)
    public Map<String, String> handleNoSuchElement(NoSuchElementException e){
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NOT_FOUND)
    public Map<String, String> handleIndexOutOfBounds(IndexOutOfBoundsException e){
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
