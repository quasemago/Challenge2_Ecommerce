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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldErrorDetails> details;

    public ErrorMessage(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpStatus status, String message, BindingResult result) {
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.details = new ArrayList<>();
        addDetails(result);
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
