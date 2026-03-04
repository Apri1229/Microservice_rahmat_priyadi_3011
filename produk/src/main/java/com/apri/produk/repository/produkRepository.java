package com.apri.produk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apri.produk.model.produk;

public interface produkRepository extends JpaRepository<produk, Long> { }
