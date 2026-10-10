package org.example.web.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.demo.api.event.UserUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

@Component
public class KafkaMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "user-events", groupId = "update-group")
    public void handleUserUpdate(UserUpdateEvent userUpdateEvent) {

        logger.info("接受用户更新事件消息: {}", objectMapper.writeValueAsString(userUpdateEvent));
        throw  new RuntimeException();
    }

    @KafkaListener(topics = "user-events-dlt", groupId = "update-dlt-gourp")
    public void handleDeadLetter(UserUpdateEvent userUpdateEvent) {
        logger.info("死信队列信息:{}", objectMapper.writeValueAsString(userUpdateEvent));
    }
}
