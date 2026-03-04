package com.apri.produk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apri.produk.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> { }
