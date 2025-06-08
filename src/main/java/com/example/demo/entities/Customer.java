package com.example.demo.entities;

import jakarta.persistence.*;


import java.util.HashSet;

import java.util.Set;


@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Cart> carts = new HashSet<>();



    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    // Convenience method to add a Cart and keep bidirectional consistency
    public void addCart(Cart cart) {
        if (cart != null) {
            carts.add(cart);
            cart.setCustomer(this);
        }
    }
}
