package com.study.ecommerce.customer.domain.repository;

import com.study.ecommerce.customer.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
