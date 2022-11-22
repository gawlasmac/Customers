package com.org.rjankowski.ms.customers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/")
@RequiredArgsConstructor
public class CustomerResource {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    private final Environment env;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers(@RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName) {
        String port = env.getProperty("server.port");
        if (firstName != null && lastName != null) {
            return ResponseEntity.ok().header("x-port", port).body(customerRepository.findAllByFirstNameAndLastName(firstName, lastName));
        }
        if (firstName != null) {
            return ResponseEntity.ok().header("x-port", port).body(customerRepository.findAllByFirstName(firstName));
        }
        if (lastName != null) {
            return ResponseEntity.ok().header("x-port", port).body(customerRepository.findAllByLastName(lastName));
        }
        return ResponseEntity.ok().header("x-port", port).body(customerRepository.findAll());
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Optional<Customer> itemById = customerRepository.findById(id);
        if (itemById.isPresent()) {
            return new ResponseEntity<>(itemById.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/customers")
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        customerRepository.saveAndFlush(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/activate")
    public ResponseEntity activateCustomer(@RequestBody Customer customer) {
        customerRepository.saveAndFlush(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/updateCustomer")
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        customerRepository.saveAndFlush(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("customers/{id}/addresses")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable Long id) {
        return new ResponseEntity<>(addressRepository.findAddressByCustomerId(id), HttpStatus.OK);
    }

    @GetMapping("customers/address/{customerId}/{addressId}")
    public ResponseEntity<Address> findAddressById(@PathVariable Long customerId, @PathVariable Long addressId) {
        Optional<Address> address = addressRepository.findByIdAndCustomerId(addressId, customerId);
        if (address.isPresent()) {
            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
