package com.example.AgriProject.controller;

import com.example.AgriProject.entity.Cart;
import com.example.AgriProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ){
        Cart cart=cartService.addProductToCart(userId,productId,quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("cart/user/{userId}")
    public Cart getUserCart(@PathVariable Long userId){
        return cartService.getCartByUser(userId);
    }
}
