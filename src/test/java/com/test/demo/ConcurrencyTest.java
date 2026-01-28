package com.test.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyTest {

    // 🛑 PASTE YOUR FRESH JWT TOKEN HERE
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4eXoiLCJpYXQiOjE3NjgxOTg3NzgsImV4cCI6MTc2ODIzNDc3OH0.MQZWLx-ycEHXW1DnO8cQFpCWlnbd7soFWKUQvZBvjzs";

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(1);

        System.out.println("🚀 Preparing " + numberOfThreads + " concurrent requests...");

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    latch.await();
                    sendWithdrawRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("🔥 FIRING REQUESTS NOW!");
        latch.countDown();
        executor.shutdown();
    }

    private static void sendWithdrawRequest() {
        try {
            String json = """
                {
                    "id": 1,
                    "amount": 100
                }
            """;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/banking/withdraw"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + JWT_TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // ✅ NEW: Printing the Body to see the Error Message
            String output = String.format(
                    "Thread: %-15s | Status: %d | Body: %s",
                    Thread.currentThread().getName(),
                    response.statusCode(),
                    response.body()
            );

            System.out.println(output);

        } catch (Exception e) {
            System.err.println("Request Failed: " + e.getMessage());
        }
    }
}