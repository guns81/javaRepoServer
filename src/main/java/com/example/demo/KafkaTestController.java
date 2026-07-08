package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {

    private final KafkaProducerService producerService;

    public KafkaTestController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/api/send")
    public String send(@RequestParam String message) {
        producerService.sendMessage("test", message);
        return "Messaggio inviato: " + message;
    }
}