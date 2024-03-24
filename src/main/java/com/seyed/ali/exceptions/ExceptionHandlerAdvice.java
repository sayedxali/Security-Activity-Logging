package com.seyed.ali.exceptions;

import com.seyed.ali.model.response.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.seyed.ali.model.response.StatusCode.INVALID_ARGUMENT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseEntity<Result> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach(error -> {
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            map.put(key, value);
        });

        return new ResponseEntity<>(
                new Result(
                        false,
                        INVALID_ARGUMENT,
                        "Provided arguments are invalid, see data for details.",
                        map
                ),
                BAD_REQUEST
        );
    }

}
