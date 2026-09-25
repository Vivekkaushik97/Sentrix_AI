package com.sentrix.ai.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_SECURITY = "sentrix.security.exchange";
    
    public static final String QUEUE_UPI_TRANSACTIONS = "sentrix.queue.upi";
    public static final String ROUTING_KEY_UPI = "sentrix.routing.upi";

    public static final String QUEUE_GENERIC_EVENTS = "sentrix.queue.events";
    public static final String ROUTING_KEY_EVENTS = "sentrix.routing.events";

    @Bean
    public TopicExchange securityExchange() {
        return new TopicExchange(EXCHANGE_SECURITY);
    }

    @Bean
    public Queue upiQueue() {
        return QueueBuilder.durable(QUEUE_UPI_TRANSACTIONS)
                // In a real production system, configure dead-letter exchange here
                .build();
    }

    @Bean
    public Binding upiBinding(Queue upiQueue, TopicExchange securityExchange) {
        return BindingBuilder.bind(upiQueue).to(securityExchange).with(ROUTING_KEY_UPI);
    }

    @Bean
    public Queue eventsQueue() {
        return QueueBuilder.durable(QUEUE_GENERIC_EVENTS).build();
    }

    @Bean
    public Binding eventsBinding(Queue eventsQueue, TopicExchange securityExchange) {
        return BindingBuilder.bind(eventsQueue).to(securityExchange).with(ROUTING_KEY_EVENTS);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
