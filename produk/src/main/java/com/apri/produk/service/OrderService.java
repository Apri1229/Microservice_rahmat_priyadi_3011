package com.apri.produk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.apri.produk.model.Order;

@Service
public class OrderService {

    private List<Order> orders = new ArrayList<>();
    private AtomicLong counter = new AtomicLong(1);

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(Long id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Order createOrder(Order o) {
        o.setId(counter.getAndIncrement());

        if (o.getHarga() != null && o.getJumlah() != null) {
            o.setTotal(o.getHarga() * o.getJumlah());
        }

        orders.add(o);
        return o;
    }

    public void deleteOrder(Long id) {
        orders.removeIf(o -> o.getId().equals(id));
    }
}