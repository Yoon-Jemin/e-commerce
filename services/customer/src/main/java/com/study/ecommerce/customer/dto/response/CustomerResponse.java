package com.study.ecommerce.customer.dto.response;

import com.study.ecommerce.customer.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
