package com.tecnico.attornatus.pessoas.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class StandardError implements Serializable {
    private HttpStatusCode status;
    private List<String> errors;
    private Long timestamp;

    public StandardError(HttpStatusCode status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
        this.timestamp = System.currentTimeMillis();
    }

    public StandardError(HttpStatusCode status, String error) {
        super();
        this.status = status;
        this.timestamp = System.currentTimeMillis();
        errors = Collections.singletonList(error);
    }
}
