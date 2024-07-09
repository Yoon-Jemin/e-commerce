package com.study.ecommerce.customer.service;

import com.study.ecommerce.customer.domain.Customer;
import com.study.ecommerce.customer.dto.request.CustomerRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(final CustomerRequest request) {
        if(request == null) return null;
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }
}
