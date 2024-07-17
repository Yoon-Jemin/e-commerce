package com.study.ecommerce.order.service;

import com.study.ecommerce.customer.CustomerClient;
import com.study.ecommerce.customer.CustomerResponse;
import com.study.ecommerce.kafka.OrderConfirmation;
import com.study.ecommerce.kafka.OrderProducer;
import com.study.ecommerce.order.domain.Order;
import com.study.ecommerce.order.domain.repository.OrderRepository;
import com.study.ecommerce.order.dto.request.OrderRequest;
import com.study.ecommerce.order.dto.request.PurchaseRequest;
import com.study.ecommerce.exception.BusinessException;
import com.study.ecommerce.order.dto.response.OrderResponse;
import com.study.ecommerce.orderline.dto.request.OrderLineRequest;
import com.study.ecommerce.orderline.service.OrderLineService;
import com.study.ecommerce.payment.PaymentClient;
import com.study.ecommerce.payment.PaymentRequest;
import com.study.ecommerce.product.ProductClient;
import com.study.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(final OrderRequest request) {

        // customer check -> customer-ms 이용 (OpenFeign 이용)
        CustomerResponse customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create Order:: No Customer exists with the provided ID"));

        // purchase the prodcut -> product-ms 이용 (RestTemplate 이용)
        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.products());

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

        // start payment process -> payment-ms 이용
        PaymentRequest paymentRequest = new PaymentRequest(
          request.amount(),
          request.paymentMethod(),
          order.getId(),
          order.getReference(),
          customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation -> notification-ms 이용 (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(final Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
