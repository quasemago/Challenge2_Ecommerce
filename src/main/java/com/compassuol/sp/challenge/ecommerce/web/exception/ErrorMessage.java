package com.compassuol.sp.challenge.ecommerce.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorMessage {
    private final int code;
    private final String status;
    private final String message;
    private final List<FieldErrorDetails> details = new ArrayList<>();

    public ErrorMessage(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpStatus status, String message, BindingResult result) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        addDetails(result);
    }

    @Getter
    @AllArgsConstructor
    private static class FieldErrorDetails {
        private String field;
        private String message;
    }

    private void addDetails(BindingResult result) {
        for (FieldError error : result.getFieldErrors()) {
            this.details.add(new FieldErrorDetails(error.getField(), error.getDefaultMessage()));
        }
    }
}
