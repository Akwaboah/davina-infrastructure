package com.ikanetapps.hotelinfrastructure.config;

import com.ikanetapps.hotelinfrastructure.exception.RecordNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class ExceptionAdviserHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();

            if (error instanceof FieldError) {
                // Handle FieldError: extract the field name
                String fieldName = ((FieldError) error).getField();
                errors.put(fieldName, errorMessage);
            } else {
                // Handle ObjectError (global error): extract the object name
                String objectName = error.getObjectName();
                errors.put(objectName, errorMessage);
            }
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid fields");
        problemDetail.setProperty("messages", errors);
        return problemDetail;
    }

    // 415
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ProblemDetail handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex) {
        final StringBuilder error = new StringBuilder();
        error.append(ex.getContentType());
        error.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> error.append(t).append(" "));
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error.toString());
        problemDetail.setProperty("message", error);
        return problemDetail;
    }

    // 400
    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolation(final ConstraintViolationException ex) {
        var errors = new HashMap<String, String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(String.valueOf(violation.getPropertyPath()), violation.getMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid fields");
        problemDetail.setProperty("messages", errors);
        return problemDetail;
    }

    // 404
    @ExceptionHandler(RecordNotFoundException.class)
    ProblemDetail handleUsernameNotFoundException(final RecordNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Record not found");
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }

    // 422
    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        problemDetail.setProperty("message", getRootCauseMessage(ex));
        return problemDetail;
    }

    private String getRootCauseMessage(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause.getMessage();
    }

    // 500
    @ExceptionHandler(RuntimeException.class)
    ProblemDetail handleRuntimeException(final RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }

}
