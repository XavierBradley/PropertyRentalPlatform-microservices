package com.champsoft.propertyrentalplatform.rental.api;

import com.champsoft.propertyrentalplatform.rental.application.exception.*;
import com.champsoft.propertyrentalplatform.rental.domain.exception.*;
import com.champsoft.propertyrentalplatform.rental.web.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice(assignableTypes = RentalController.class)
public class RentalExceptionHandler {

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFound(RentalNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex, req);
    }

    @ExceptionHandler(CrossContextValidationException.class)
    public ResponseEntity<ApiErrorResponse> unprocessable(CrossContextValidationException ex, HttpServletRequest req) {
        return build(HttpStatus.valueOf(422), ex, req);
    }

    @ExceptionHandler({
            InvalidRentException.class,
            ExpiryDateMustBeFutureException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorResponse> badRequest(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex, req);
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
