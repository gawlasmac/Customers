package com.org.rjankowski.ms.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//change to interface with extends JpaRepository
// add new required correct methods
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAddressByCustomerId(Long id);
    Optional<Address> findByIdAndCustomerId(Long addressId, Long customerId);

}

