package com.atasilyas.rabbitmqproducer;

import com.atasilyas.rabbitmqproducer.config.RabbitMQConfig;
import com.atasilyas.rabbitmqproducer.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqproducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqproducerApplication.class, args);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Mesaj gonderimi basladi");

        User user = User
                .builder()
                .id("12121232")
                .name("ilyas")
                .surname("atas")
                .age(23)
                .height(1.83d)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.messageExchange, RabbitMQConfig.messageRoutingKey, user);
        System.out.println("Mesaj basarili bir sekilde gonderildi");

    }
}
