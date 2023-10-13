package com.tecnico.attornatus.pessoas.exception;

import com.tecnico.attornatus.pessoas.service.exception.ObjectNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        StandardError standardError = new StandardError(status, errors);

        return new ResponseEntity<>(standardError, standardError.getStatus());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {

        return handleExceptionInternal(
                ex,
                new StandardError(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request
        );
    }

}
