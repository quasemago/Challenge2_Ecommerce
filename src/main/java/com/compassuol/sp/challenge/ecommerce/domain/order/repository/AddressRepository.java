package com.compassuol.sp.challenge.ecommerce.domain.order.repository;

import com.compassuol.sp.challenge.ecommerce.domain.order.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
