package com.compassuol.sp.challenge.ecommerce.config;

import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignDecoderExceptionHandler;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringOpenFeignDecoderConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new OpenFeignDecoderExceptionHandler();
    }
}
