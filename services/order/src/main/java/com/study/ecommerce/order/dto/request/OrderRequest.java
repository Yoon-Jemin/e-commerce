package com.study.ecommerce.order.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.ecommerce.order.domain.PaymentMethod;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method must be precise")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer must be present")
        @NotEmpty(message = "Customer must be present")
        @NotBlank(message = "Customer must be present")
        String customerId,
        @NotEmpty(message = "You should purchase at least one product")
        List<PurchaseRequest> products
) {
}
