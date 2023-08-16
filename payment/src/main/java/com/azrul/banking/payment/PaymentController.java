/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.azrul.banking.payment;

import com.azrul.banking.domain.Transaction;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Random;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author azrul
 */
@RestController
public class PaymentController {
    


    private static final String DAPR_HTTP_PORT = System.getenv().getOrDefault("DAPR_HTTP_PORT", "3500");

    @RequestMapping("/payment")
    public ResponseEntity<Transaction> produceMessages() throws IOException, InterruptedException {
        Random random = new Random();
        int amount = random.nextInt(1000 - 1) + 1;
        Transaction t = new Transaction();
        t.setValue(amount);
        t.setFrom("Luffy");
        t.setTo("Zoro");
        JSONObject tjson = new JSONObject(t);

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        
        

        String dapr_url = "http://localhost:" + DAPR_HTTP_PORT + "/v1.0/invoke/checkout/method/checkout";

        

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(tjson.toString()))
                .uri(URI.create(dapr_url))
                .header("Content-Type", "application/json")
                //.header("dapr-app-id", "order-processor")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Transaction passed: " + t.toString());
        System.out.println("Response: " + response.body());
        
        return ResponseEntity.ok().body(t);
    }
}