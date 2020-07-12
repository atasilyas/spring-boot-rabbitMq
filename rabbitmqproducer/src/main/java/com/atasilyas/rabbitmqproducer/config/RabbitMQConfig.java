package com.atasilyas.rabbitmqproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    public static final String messageExchange = "message_exchange";
    public static final String messageQueue = "message_queue";
    public static final String messageRoutingKey = "message_routing_key";


    @Bean
    public Queue getQueue() {
        return new Queue(messageQueue, false);
    }

    @Bean
    public TopicExchange getExchange() {
        return new TopicExchange(messageExchange);
    }

    @Bean
    public Binding bind(TopicExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(messageRoutingKey);
    }

    @Bean
    public ConnectionFactory getConnection() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }
}
