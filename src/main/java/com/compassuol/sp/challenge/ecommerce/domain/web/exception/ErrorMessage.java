package com.compassuol.sp.challenge.ecommerce.domain.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorMessage {
    private int code;
    private String status;
    private String message;
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

    public ErrorMessage(HttpStatus status, String message, String exceptionMessage) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
    }

    @Getter
    @Setter
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
