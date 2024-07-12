package com.study.ecommerce.product.service;

import com.study.ecommerce.product.domain.Product;
import com.study.ecommerce.product.domain.repository.ProductRepository;
import com.study.ecommerce.product.dto.request.ProductPurchaseRequest;
import com.study.ecommerce.product.dto.request.ProductRequest;
import com.study.ecommerce.product.dto.response.ProductPurchaseResponse;
import com.study.ecommerce.product.dto.response.ProductResponse;
import com.study.ecommerce.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    public Integer createProduct(final ProductRequest request) {
        Product product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(final List<ProductPurchaseRequest> request) {
        // 구매 요청 받은 Product ID
        List<Integer> productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        // 요청 받은 Product ID에 해당하는 Product 리스트
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more products does not exists");
        }

        // Product ID 순으로 정렬된 주문 요청 목록
        List<ProductPurchaseRequest> storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        // 구매한 Product 리스트
        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        // 충분한 수량이 있는지 확인
        for(int i = 0; i < storedProducts.size(); i++){
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            double newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    public ProductResponse findById(final Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
