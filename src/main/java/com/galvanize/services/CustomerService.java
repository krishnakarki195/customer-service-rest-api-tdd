package com.galvanize.services;

import com.galvanize.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerService extends JpaRepository<Customer, Long> {
}
