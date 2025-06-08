package com.example.demo.services;

import com.example.demo.dao.CartItemRepository;
import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Cart cart = purchase.getCart();

        // Generate and assign tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // Set status type properly
        cart.setStatusType(StatusType.ordered);

        // Add each cart item using the correct method
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(cart::addCartItem);

        // Add cart to customer
        Customer customer = purchase.getCustomer();
        customer.addCart(cart); // ensure this method exists on Customer entity

        // Save customer (cascade should save cart and items if configured)
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}