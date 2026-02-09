package com.example.AgriProject.service;

import com.example.AgriProject.entity.Cart;
import com.example.AgriProject.entity.CartItem;
import com.example.AgriProject.entity.Product;
import com.example.AgriProject.entity.User;
import com.example.AgriProject.repository.CartItemRepository;
import com.example.AgriProject.repository.CartRepository;
import com.example.AgriProject.repository.ProductRepository;
import com.example.AgriProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Cart addProductToCart(Long userId, Long productId,int quantity){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        Product product=productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));


        Cart cart=cartRepository.findByUser(user)
                .orElseGet(()->{
                    Cart newCart=Cart.builder()
                            .user(user)
                            .totalAmount(0)
                            .build();

                    return cartRepository.save(newCart);
                });


        CartItem cartItem=cartItemRepository
                .findByCartAndProduct(cart,product)
                .orElse(null);

        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        else{
            cartItem=CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getCost())
                    .build();
            cart.getItems().add(cartItem);
        }

        double total = cart.getItems()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cart.setTotalAmount(total);

        return cartRepository.save(cart);
    }

    public Cart getCartByUser(Long userId){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .orElseThrow(()->new RuntimeException("Cart is empty"));
    }
}
