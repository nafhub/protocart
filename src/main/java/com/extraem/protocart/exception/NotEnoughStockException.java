package com.extraem.protocart.exception;

import com.extraem.protocart.model.Product;

public class NotEnoughStockException extends Exception {
    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public NotEnoughStockException() { super(DEFAULT_MESSAGE);}

    public NotEnoughStockException(Product product) {
        super (String.format("Not enough %s in stock. Only %d left", product.getName(), product.getQuantity()));
    }
}
