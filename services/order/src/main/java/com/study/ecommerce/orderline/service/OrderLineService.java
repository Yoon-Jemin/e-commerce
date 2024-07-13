package com.study.ecommerce.orderline.service;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.orderline.domain.OrderLine;
import com.study.ecommerce.orderline.domain.repository.OrderLineRepository;
import com.study.ecommerce.orderline.dto.request.OrderLineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;
    public Integer saveOrderLine(final OrderLineRequest request) {
        OrderLine order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }
}
