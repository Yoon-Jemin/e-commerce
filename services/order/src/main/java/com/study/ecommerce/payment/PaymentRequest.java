package com.study.ecommerce.payment;

import com.study.ecommerce.customer.CustomerResponse;
import com.study.ecommerce.order.domain.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
