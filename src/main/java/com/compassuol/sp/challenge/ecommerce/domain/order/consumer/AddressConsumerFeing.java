package com.compassuol.sp.challenge.ecommerce.domain.order.consumer;

import com.compassuol.sp.challenge.ecommerce.domain.order.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "address-consumer", url = "https://viacep.com.br/ws")
public interface AddressConsumerFeing {
    @GetMapping(value = "/{cep}/json")
    Address getAddressByCep(@PathVariable("cep") String cep);

}
