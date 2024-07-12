package com.study.ecommerce.product.domain;

import com.study.ecommerce.product.domain.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private double availableQuantity;

    private BigDecimal price;   // 가격 관련 속성은 BigDecimal을 사용하는 것이 좋음

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
