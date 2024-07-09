package com.study.ecommerce.customer.service;

import com.study.ecommerce.customer.domain.Customer;
import com.study.ecommerce.customer.domain.repository.CustomerRepository;
import com.study.ecommerce.customer.dto.request.CustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    public String createCustomer(final CustomerRequest request) {
        Customer customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }
}
