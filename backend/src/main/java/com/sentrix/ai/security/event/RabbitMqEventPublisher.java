package com.sentrix.ai.security.event;

import com.sentrix.ai.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEventPublisher implements SecurityEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(SecurityEvent event) {
        String routingKey = determineRoutingKey(event.getEventType());
        logger.debug("Publishing event {} to routing key {}", event.getEventId(), routingKey);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_SECURITY, routingKey, event);
    }

    private String determineRoutingKey(SecurityEventType type) {
        if (type == SecurityEventType.UPI_TRANSACTION) {
            return RabbitMQConfig.ROUTING_KEY_UPI;
        }
        return RabbitMQConfig.ROUTING_KEY_EVENTS;
    }
}
