package com.champsoft.propertyrentalplatform.property.api;

import com.champsoft.propertyrentalplatform.property.application.exception.*;
import com.champsoft.propertyrentalplatform.property.domain.exception.*;
import com.champsoft.propertyrentalplatform.shared.web.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice(assignableTypes = PropertyController.class)
public class PropertyExceptionHandler {

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFound(PropertyNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex, req);
    }

    @ExceptionHandler({
            DuplicateAddressException.class
    })
    public ResponseEntity<ApiErrorResponse> conflict(DuplicateAddressException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex, req);
    }

    @ExceptionHandler({
            InvalidPropertyTaxException.class,
            InvalidAddressException.class,
            PropertyAlreadyBeingRentedException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorResponse> badRequest(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex, req);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> badRequest(org.springframework.web.bind.MethodArgumentNotValidException ex,
                                                       HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .orElse("Validation failed");
        return build(HttpStatus.BAD_REQUEST, new IllegalArgumentException(message), req);
    }

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, Exception ex, HttpServletRequest req) {
        var body = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}
