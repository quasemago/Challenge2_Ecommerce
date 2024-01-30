package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

import feign.Response;
import feign.codec.ErrorDecoder;



public class OpenFeignDecoderExceptionHandler implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new OpenFeignBadRequestException("Não foi possível processar o CEP informado.");
            case 404 -> new OpenFeignNotFoundException("CEP informado não encontrado.");
            default -> new Exception("Ocorreu um erro inesperado.");
        };
    }
}
