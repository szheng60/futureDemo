package com.example.song.futureDemo.controller;

import com.example.song.futureDemo.client.RentalClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@RestController
public class RentalController {

    private final RentalClient rentalClient;
    private final ExecutorService executor;

    public RentalController(RentalClient rentalClient, ExecutorService executor) {
        this.rentalClient = rentalClient;
        this.executor = executor;
    }
    @GetMapping("/api/viewprofiles")
    public String viewProfile() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();

        CompletableFuture[] futureArray = new CompletableFuture[]{};

        List<CompletableFuture<String>> futureList = getEmailList().stream()
                .map(n -> CompletableFuture.supplyAsync(() -> getUserProfile(n), executor))
                .collect(Collectors.toList());

        CompletableFuture<Void> allCompleted = CompletableFuture.allOf(futureList.toArray(futureArray));

        CompletableFuture<List<String>> allFuture = allCompleted.thenApply(v-> futureList.stream()
                .map(future -> future.join())
                .collect(Collectors.toList()));

        allFuture.get().stream()
                    .forEach(f -> {
                        System.out.println(f);
                        sb.append(f);
                        sb.append(System.getProperty("line.separator"));
                    });

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        sb.append("Duration: " + duration);
        return sb.toString();
    }
    private String getUserProfile(String email) {
        return rentalClient.getUserProfile(email).getBody();
    }

    private List<String> getEmailList() {
        return Arrays.asList(
                "user_1@gmail.com",
                "user_2@gmail.com",
                "user_3@abcam.net",
                "xinyuchen919@hotmail.com"
        );
    }
}
