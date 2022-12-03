package com.extraem.protocart.service;

import com.extraem.protocart.exception.NotEnoughStockException;
import com.extraem.protocart.model.Product;

import java.util.Map;

public interface CartService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    Integer checkout() throws NotEnoughStockException;

    Integer getTotal();

}
