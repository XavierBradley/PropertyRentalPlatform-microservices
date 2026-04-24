package com.champsoft.propertyrentalplatform.tenant.api;

import com.champsoft.propertyrentalplatform.tenant.application.exception.*;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.*;
import com.champsoft.propertyrentalplatform.tenant.web.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice(assignableTypes = TenantController.class)
public class TenantExceptionHandler {

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFound(TenantNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex, req);
    }

    @ExceptionHandler({
            DuplicateTenantException.class,
            TooLowCreditScoreException.class
    })
    public ResponseEntity<ApiErrorResponse> conflict(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex, req);
    }

    @ExceptionHandler({
            InvalidTenantNameException.class,
            InvalidABAException.class,
            InvalidAccountNumberException.class,
            InvalidCreditScoreException.class,
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
