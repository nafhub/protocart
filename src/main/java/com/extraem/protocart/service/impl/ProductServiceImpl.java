package com.extraem.protocart.service.impl;

import com.extraem.protocart.model.Product;
import com.extraem.protocart.repository.ProductRepository;
import com.extraem.protocart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> finAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
