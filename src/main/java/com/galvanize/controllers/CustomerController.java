package com.galvanize.controllers;


import com.galvanize.entities.Customer;
import com.galvanize.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/customers")
    public Customer save(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    @GetMapping(value = "/customers")
    public List<Customer> getCustomers(){
        return customerService.findAll();
    }

    @GetMapping(value = "/customers/{id}")
    public Optional<Customer> getCustomer(@PathVariable Long id){
        return customerService.findById(id);
    }

    @PatchMapping(value = "/customers/{id}")
    public Customer updateCustomerById(@PathVariable Long id,@RequestBody String name) {
        Optional<Customer> optionalCustomer = customerService.findById(id);
        Customer customer = optionalCustomer.get();
        customer.setName(name);
        return customerService.save(customer);
    }

    @DeleteMapping(value = "/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteById(id);
    }

}
