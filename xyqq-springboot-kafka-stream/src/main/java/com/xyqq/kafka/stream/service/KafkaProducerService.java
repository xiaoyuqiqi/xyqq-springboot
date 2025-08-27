package com.xyqq.kafka.stream.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyqq.kafka.stream.model.UserEvent;
import com.xyqq.kafka.stream.model.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Kafka 生产者服务
 *
 * @author xyqq
 */
@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${kafka.topics.input}")
    private String inputTopic;

    @Value("${kafka.topics.user-events}")
    private String userEventsTopic;

    /**
     * 发送用户消息
     */
    public void sendUserMessage(UserMessage userMessage) {
        try {
            String messageJson = objectMapper.writeValueAsString(userMessage);
            
            ListenableFuture<SendResult<String, String>> future = 
                    kafkaTemplate.send(inputTopic, userMessage.getUserId(), messageJson);
            
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("用户消息发送成功: topic={}, key={}, offset={}", 
                            inputTopic, userMessage.getUserId(), result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error("用户消息发送失败: topic={}, key={}", 
                            inputTopic, userMessage.getUserId(), ex);
                }
            });
            
        } catch (Exception e) {
            logger.error("序列化用户消息失败", e);
            throw new RuntimeException("发送用户消息失败", e);
        }
    }

    /**
     * 发送用户事件
     */
    public void sendUserEvent(UserEvent userEvent) {
        try {
            String eventJson = objectMapper.writeValueAsString(userEvent);
            
            ListenableFuture<SendResult<String, String>> future = 
                    kafkaTemplate.send(userEventsTopic, userEvent.getUserId(), eventJson);
            
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("用户事件发送成功: topic={}, key={}, offset={}", 
                            userEventsTopic, userEvent.getUserId(), result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error("用户事件发送失败: topic={}, key={}", 
                            userEventsTopic, userEvent.getUserId(), ex);
                }
            });
            
        } catch (Exception e) {
            logger.error("序列化用户事件失败", e);
            throw new RuntimeException("发送用户事件失败", e);
        }
    }

    /**
     * 发送原始消息到指定主题
     */
    public void sendRawMessage(String topic, String key, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("原始消息发送成功: topic={}, key={}, offset={}", 
                        topic, key, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("原始消息发送失败: topic={}, key={}", topic, key, ex);
            }
        });
    }

    /**
     * 批量发送用户消息
     */
    public void sendUserMessagesBatch(UserMessage... userMessages) {
        for (UserMessage userMessage : userMessages) {
            sendUserMessage(userMessage);
        }
        logger.info("批量发送用户消息完成，共 {} 条", userMessages.length);
    }

    /**
     * 批量发送用户事件
     */
    public void sendUserEventsBatch(UserEvent... userEvents) {
        for (UserEvent userEvent : userEvents) {
            sendUserEvent(userEvent);
        }
        logger.info("批量发送用户事件完成，共 {} 条", userEvents.length);
    }
}