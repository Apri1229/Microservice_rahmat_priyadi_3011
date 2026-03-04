package com.apri.produk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@JsonPropertyOrder({ "id", "id_produk", "id_pelanggan", "harga", "jumlah", "total" })
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_produk", nullable = false)
    @JsonProperty("id_produk")
    private Long idProduk;

    @Column(name = "id_pelanggan", nullable = false)
    @JsonProperty("id_pelanggan")
    private Long idPelanggan;

    @Column(nullable = false)
    private Double harga;

    @Column(nullable = false)
    private Integer jumlah;

    private Double total;

    // ===== GETTER & SETTER =====

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getIdProduk() { return idProduk; }

    public void setIdProduk(Long idProduk) { this.idProduk = idProduk; }

    public Long getIdPelanggan() { return idPelanggan; }

    public void setIdPelanggan(Long idPelanggan) { this.idPelanggan = idPelanggan; }

    public Double getHarga() { return harga; }

    public void setHarga(Double harga) { this.harga = harga; }

    public Integer getJumlah() { return jumlah; }

    public void setJumlah(Integer jumlah) { this.jumlah = jumlah; }

    public Double getTotal() { return total; }

    public void setTotal(Double total) { this.total = total; }
}