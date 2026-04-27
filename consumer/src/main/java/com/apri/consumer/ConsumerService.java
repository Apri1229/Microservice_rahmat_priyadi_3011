package com.apri.consumer;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.internet.MimeMessage;

@Service
public class ConsumerService {

    @Autowired
    private JavaMailSender mailSender;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "order.queue")
    public void receiveMessage(String message) {

        System.out.println("Message masuk: " + message);

        try {
            Map<String, Object> order = objectMapper.readValue(message, Map.class);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo("raemon@pnp.ac.id");

            helper.setSubject("Notifikasi Order Masuk");

            String html = "<h2>Detail Order</h2>"
                + "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse:collapse;'>"
                + "<tr style='background:#f2f2f2;'><th>Field</th><th>Value</th></tr>"
                + "<tr><td>ID Order</td><td>" + order.getOrDefault("id", "-") + "</td></tr>"
                + "<tr><td>ID Produk</td><td>" + order.getOrDefault("idProduk", "-") + "</td></tr>"
                + "<tr><td>ID Pelanggan</td><td>" + order.getOrDefault("idPelanggan", "-") + "</td></tr>"
                + "<tr><td>Email Pelanggan</td><td>" + order.getOrDefault("emailPelanggan", "-") + "</td></tr>"
                + "<tr><td>Harga</td><td>Rp " + order.getOrDefault("harga", "-") + "</td></tr>"
                + "<tr><td>Jumlah</td><td>" + order.getOrDefault("jumlah", "-") + "</td></tr>"
                + "<tr><td>Total</td><td>Rp " + order.getOrDefault("total", "-") + "</td></tr>"
                + "</table>";

            helper.setText(html, true); // true = HTML

            mailSender.send(mimeMessage);

            System.out.println("Email berhasil dikirim!");

        } catch (Exception e) {
            System.out.println("Gagal kirim email: " + e.getMessage());
        }
    }
}