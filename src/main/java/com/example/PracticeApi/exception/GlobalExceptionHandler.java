package com.example.PracticeApi.exception;

import com.example.PracticeApi.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExists(AlreadyExistsException ex){
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseDto> handleDisabledUser(DisabledException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return new ErrorResponseDto(errors, HttpStatus.BAD_REQUEST.value());
    }
}
