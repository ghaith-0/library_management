package com.example.librarymanagementsystem.ExceptionHandler;

import com.example.librarymanagementsystem.Configs.APIResponseConfigs.APIResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleEntityNotFoundException(EntityNotFoundException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.NOT_FOUND.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<APIResponse<Void>> handleIllegalStateException(IllegalStateException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), false, null, "Validation failed: " + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.CONFLICT.value(), false, null, "Data integrity violation: " + Objects.requireNonNull(ex.getRootCause()).getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<APIResponse<Void>> handleServletException(ServletException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.UNAUTHORIZED.value(), false, null, Objects.requireNonNull(ex.getRootCause()).getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<Void>> handleAuthenticationException(AuthenticationException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.UNAUTHORIZED.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.UNAUTHORIZED.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleException(Exception ex) {
        APIResponse<Void> response = new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}