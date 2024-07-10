package com.study.ecommerce.customer.domain.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
