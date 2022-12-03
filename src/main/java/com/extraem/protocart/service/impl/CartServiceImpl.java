package com.extraem.protocart.service.impl;

import com.extraem.protocart.exception.NotEnoughStockException;
import com.extraem.protocart.model.Product;
import com.extraem.protocart.repository.ProductRepository;
import com.extraem.protocart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    @Override
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1) {
                products.replace(product, products.get(product) - 1);
            } else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public Integer checkout() throws NotEnoughStockException {
        Product product;
        Integer total = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()){
            product  = productRepository.findById(entry.getKey().getId()).get();
            if (product.getQuantity() < entry.getValue()) {
                throw new NotEnoughStockException(product);
            }
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
            total += product.getPrice() * entry.getValue();
        }
        productRepository.saveAll(products.keySet());
        products.clear();
        return total;
    }

    @Override
    public Integer getTotal() {
        return products.entrySet().stream().map(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(Integer::sum)
                .orElse(0);
    }
}
