package com.study.ecommerce.orderline.domain.repository;

import com.study.ecommerce.orderline.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
