package com.apri.order.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apri.order.RabbitMqConfig;
import com.apri.order.model.Order;
import com.apri.order.repository.OrderRepository;
import com.apri.order.vo.Produk;
import com.apri.order.vo.ResponseTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Order order) {
        try {
            String message = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_NAME, message);
            System.out.println("Message sent ke RabbitMQ:");
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("Gagal kirim message: " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        sendMessage(savedOrder);
        return savedOrder;
    }

    public Order updateOrder(Order order) {
        Order updatedOrder = orderRepository.save(order);
        sendMessage(updatedOrder);
        return updatedOrder;
    }

    public void deleteOrder(Integer id) {
        String message = "{\"event\":\"Order deleted\",\"orderId\":" + id + "}";
        rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_NAME, message);
        System.out.println("Order delete message sent: " + message);
        orderRepository.deleteById(id);
    }

    public void deleteOrdersByPelanggan(Integer idPelanggan) {
        List<Order> orders = orderRepository.findByIdPelanggan(idPelanggan);
        orderRepository.deleteAll(orders);
    }

    public List<Order> getOrdersByPelanggan(Integer idPelanggan) {
        return orderRepository.findByIdPelanggan(idPelanggan);
    }

    public ResponseTemplate getOrderWithProduk(Integer id) {
        Order order = getOrderById(id);

        Produk produk = restTemplate.getForObject(
                "http://localhost:8080/api/produk/" + order.getIdProduk(),
                Produk.class);

        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduk(produk);

        return vo;
    }
}