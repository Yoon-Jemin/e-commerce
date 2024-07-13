package com.study.ecommerce.order.service;

import com.study.ecommerce.customer.CustomerClient;
import com.study.ecommerce.customer.CustomerResponse;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.repository.OrderRepository;
import com.study.ecommerce.order.dto.request.OrderRequest;
import com.study.ecommerce.order.dto.request.PurchaseRequest;
import com.study.ecommerce.exception.BusinessException;
import com.study.ecommerce.orderline.dto.request.OrderLineRequest;
import com.study.ecommerce.orderline.service.OrderLineService;
import com.study.ecommerce.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(final OrderRequest request) {

        // customer check (OpenFeign 이용)
        CustomerResponse customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create Order:: No Customer exists with the provided ID"));

        // purchase the prodcut -> product-ms 이용 (RestTemplate 이용)
        this.productClient.purchaseProducts(request.products());

        // persist order
        Order order = this.repository.save(mapper.toOrder(request));

        // persist order lines
        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo: start payment process -> payment-ms 이용

        // send the order confirmation -> notification-ms 이용 (kafka)

        return null;
    }
}
