package com.atasilyas.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    public static final String messageExchange = "message_exchangeV3";
    public static final String messageQueue = "message_queueV3";
    public static final String messageRoutingKey = "message_routing_keyV3";


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

    @Bean("connectionFactoryProducer")
    public ConnectionFactory getConnection() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(JMSReceiver jmsReceiver) {

        return new MessageListenerAdapter(jmsReceiver, "receive message");
    }

    @Bean
    public SimpleMessageListenerContainer container(@Qualifier("connectionFactoryProducer") ConnectionFactory connectionFactory, MessageListenerAdapter adapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueueNames(messageQueue);
        container.setMessageListener(adapter);
        return container;
    }
}

