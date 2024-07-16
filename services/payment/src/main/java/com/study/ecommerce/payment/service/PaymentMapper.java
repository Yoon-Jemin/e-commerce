package com.study.ecommerce.payment.service;

import com.study.ecommerce.payment.domain.Payment;
import com.study.ecommerce.payment.dto.request.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(final PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .orderId(request.orderId())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();
    }
}
