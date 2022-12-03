package com.extraem.protocart.controller;

import com.extraem.protocart.exception.NotEnoughStockException;
import com.extraem.protocart.model.Product;
import com.extraem.protocart.service.CartService;
import com.extraem.protocart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public ResponseEntity cart() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getProductsInCart());

    }

    @GetMapping("/cart/addproduct/{productId}")
    public ResponseEntity addProductToCart(@PathVariable("productId") Integer productId) {
        productService.findById(productId).ifPresent(cartService::addProduct);
        return cart();
    }

    @GetMapping("/cart/removeproduct/{productId}")
    public ResponseEntity removeProductFromCart(@PathVariable("productId") Integer productId) {
        productService.findById(productId).ifPresent(cartService::removeProduct);
        return cart();
    }

    @GetMapping("/cart/checkout")
    public ResponseEntity checkout(){
        Integer total = 0;
        try {
            total = cartService.checkout();
        } catch (NotEnoughStockException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("please pay: " + total);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity lookForProduct(@PathVariable("name") String name) {
        Optional<Product> p = productService.findByName(name);
        return p.map(product -> ResponseEntity.status(HttpStatus.OK).body(createPath(product))).orElseGet(() -> ResponseEntity.status(HttpStatus.OK).body("not found"));
    }

    String createPath(Product product) {
        return "/cart/addproduct/"+product.getId();
    }

}
