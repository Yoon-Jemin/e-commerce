package com.study.ecommerce.orderline.service;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.orderline.domain.OrderLine;
import com.study.ecommerce.orderline.dto.request.OrderLineRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(final OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .productId(request.productId())
                .build();
    }
}
