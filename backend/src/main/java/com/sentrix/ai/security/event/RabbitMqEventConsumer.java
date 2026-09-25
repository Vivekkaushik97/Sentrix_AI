package com.sentrix.ai.security.event;

import com.sentrix.ai.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMqEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqEventConsumer.class);
    private final List<SecurityEventProcessor> processors;

    public RabbitMqEventConsumer(List<SecurityEventProcessor> processors) {
        this.processors = processors;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPI_TRANSACTIONS)
    public void receiveUpiEvent(SecurityEvent event) {
        logger.debug("Received UPI Event: {}", event.getEventId());
        processEvent(event);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_GENERIC_EVENTS)
    public void receiveGenericEvent(SecurityEvent event) {
        logger.debug("Received Generic Event: {}", event.getEventId());
        processEvent(event);
    }

    private void processEvent(SecurityEvent event) {
        for (SecurityEventProcessor processor : processors) {
            if (processor.supports(event.getEventType())) {
                try {
                    processor.process(event);
                } catch (Exception e) {
                    logger.error("Error processing event {} by processor {}", event.getEventId(), processor.getClass().getSimpleName(), e);
                    // Let the exception bubble up if it should be DLQ'd or retried by RabbitMQ
                    throw e; 
                }
            }
        }
    }
}
