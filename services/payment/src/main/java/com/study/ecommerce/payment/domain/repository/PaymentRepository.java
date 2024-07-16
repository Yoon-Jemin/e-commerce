package com.study.ecommerce.payment.domain.repository;

import com.study.ecommerce.payment.domain.Payment;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
