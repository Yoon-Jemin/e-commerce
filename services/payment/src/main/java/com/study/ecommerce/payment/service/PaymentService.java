package com.study.ecommerce.payment.service;

import com.study.ecommerce.notification.NotificationProducer;
import com.study.ecommerce.notification.PaymentNotificationRequest;
import com.study.ecommerce.payment.domain.Payment;
import com.study.ecommerce.payment.domain.repository.PaymentRepository;
import com.study.ecommerce.payment.dto.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(final PaymentRequest request) {
        Payment payment = repository.save(mapper.toPayment(request));
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
