package com.study.ecommerce.payment.dto.request;

import com.study.ecommerce.payment.domain.PaymentMethod;
import com.study.ecommerce.payment.dto.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
