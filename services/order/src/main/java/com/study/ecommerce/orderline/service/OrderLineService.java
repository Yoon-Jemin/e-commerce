package com.study.ecommerce.orderline.service;

import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.orderline.domain.OrderLine;
import com.study.ecommerce.orderline.domain.repository.OrderLineRepository;
import com.study.ecommerce.orderline.dto.request.OrderLineRequest;
import com.study.ecommerce.orderline.dto.response.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;
    public Integer saveOrderLine(final OrderLineRequest request) {
        OrderLine order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(final Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
