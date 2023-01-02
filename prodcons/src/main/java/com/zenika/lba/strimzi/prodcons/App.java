package com.zenika.lba.strimzi.prodcons;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@EnableKafka
@Slf4j
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 500)
    public void sendMessage() {
        String msg = new String().concat("New message : ").concat(LocalDate.now().toString());
        kafkaTemplate.send("my-topic", msg);
        System.out.println("Send Message : " + msg);
    }

    @KafkaListener(topics = "my-topic", groupId = "my-groupid")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }

}