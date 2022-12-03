package com.extraem.protocart.service;

import com.extraem.protocart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findByName(String name);

    Optional<Product> findById(Integer id);

    Page<Product> finAllProducts(Pageable pageable);
}
