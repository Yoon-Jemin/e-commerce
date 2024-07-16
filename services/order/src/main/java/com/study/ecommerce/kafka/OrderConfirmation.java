package com.study.ecommerce.kafka;

import com.study.ecommerce.customer.CustomerResponse;
import com.study.ecommerce.order.domain.PaymentMethod;
import com.study.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
