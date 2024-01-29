package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

import feign.Response;
import feign.codec.ErrorDecoder;



public class OpenFeignDecoderExceptionHandler implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 400:
                return new OpenFeignBadRequestException("Requisição inválida.");
            case 404:
                return new OpenFeignNotFoundException("Requisição não encontrada.");
            default:
                return new Exception("Ocorreu um erro inesperado.");
        }
    }
}
