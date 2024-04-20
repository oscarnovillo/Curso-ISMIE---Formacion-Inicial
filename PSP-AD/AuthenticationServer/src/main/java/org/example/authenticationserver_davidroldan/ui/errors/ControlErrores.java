package org.example.authenticationserver_davidroldan.ui.errors;

import org.example.authenticationserver_davidroldan.domain.modelo.errores.CertificateException;
import org.example.authenticationserver_davidroldan.domain.modelo.errores.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControlErrores {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException e) {
        ApiError apiError = new ApiError(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(CertificateException.class)
    public ResponseEntity<ApiError> handleValidationException(CertificateException e) {
        ApiError apiError = new ApiError(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
