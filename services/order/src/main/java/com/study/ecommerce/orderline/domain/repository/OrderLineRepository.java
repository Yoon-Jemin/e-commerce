package com.study.ecommerce.orderline.domain.repository;

import com.study.ecommerce.orderline.domain.OrderLine;
import com.study.ecommerce.orderline.dto.response.OrderLineResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
