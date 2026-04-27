package com.apri.order.vo;

import com.apri.order.model.Order;
import lombok.Data;

@Data
public class ResponseTemplate {

    private Order order;
    private Produk produk;

}