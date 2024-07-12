package com.study.ecommerce.product.domain.repository;

import com.study.ecommerce.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByIdInOrderById(final List<Integer> productIds);
}
