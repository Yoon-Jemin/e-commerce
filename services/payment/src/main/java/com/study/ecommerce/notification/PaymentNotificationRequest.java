package com.study.ecommerce.notification;

import com.study.ecommerce.payment.domain.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        // Notification-ms 에 보낼 내용들
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFistName,
        String customerLastName,
        String customerEmail
) {
}
