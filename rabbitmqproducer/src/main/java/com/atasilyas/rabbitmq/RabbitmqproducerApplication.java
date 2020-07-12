package com.atasilyas.rabbitmq;

import com.atasilyas.rabbitmq.config.RabbitMQConfig;
import com.atasilyas.rabbitmq.model.User;
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

        for (int i = 0; i < 10; i++) {

            User user = User
                    .builder()
                    .id("12121232")
                    .name("ilyas")
                    .surname("atas")
                    .age(i)
                    .height(1.80 +i)
                    .build();
            try {

                rabbitTemplate.convertAndSend(RabbitMQConfig.messageExchange, RabbitMQConfig.messageRoutingKey, user);
                System.out.println("Mesaj basarili bir sekilde gonderildi");

            } catch (Exception e) {

                e.printStackTrace();
            }
        }


    }
}
