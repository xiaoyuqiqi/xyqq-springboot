package com.xyqq.kafka.stream.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyqq.kafka.stream.model.UserEvent;
import com.xyqq.kafka.stream.model.UserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Kafka Stream 消息处理器
 *
 * @author xyqq
 */
@Component
public class MessageStreamProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageStreamProcessor.class);

    @Value("${kafka.topics.input}")
    private String inputTopic;

    @Value("${kafka.topics.output}")
    private String outputTopic;

    @Value("${kafka.topics.user-events}")
    private String userEventsTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        // 处理用户消息流
        processUserMessages(streamsBuilder);
        
        // 处理用户事件流
        processUserEvents(streamsBuilder);
        
        // 消息聚合处理
        processMessageAggregation(streamsBuilder);
    }

    /**
     * 处理用户消息流
     */
    private void processUserMessages(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));

        messageStream
                .peek((key, value) -> logger.info("接收到消息: key={}, value={}", key, value))
                .filter((key, value) -> value != null && !value.trim().isEmpty())
                .mapValues(this::processMessage)
                .filter((key, value) -> value != null)
                .peek((key, value) -> logger.info("处理后的消息: key={}, value={}", key, value))
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));
    }

    /**
     * 处理用户事件流
     */
    private void processUserEvents(StreamsBuilder streamsBuilder) {
        KStream<String, String> eventStream = streamsBuilder
                .stream(userEventsTopic, Consumed.with(Serdes.String(), Serdes.String()));

        eventStream
                .peek((key, value) -> logger.info("接收到用户事件: key={}, value={}", key, value))
                .filter((key, value) -> value != null)
                .mapValues(this::processUserEvent)
                .filter((key, value) -> value != null)
                .peek((key, value) -> logger.info("处理后的用户事件: key={}, value={}", key, value))
                .to("processed-user-events", Produced.with(Serdes.String(), Serdes.String()));
    }

    /**
     * 消息聚合处理
     */
    private void processMessageAggregation(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));

        // 按用户ID分组，统计消息数量
        messageStream
                .filter((key, value) -> value != null)
                .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
                .count(Materialized.as("message-count-store"))
                .toStream()
                .map((windowedKey, count) -> {
                    String key = windowedKey.key();
                    String value = String.format("{\"userId\":\"%s\",\"count\":%d,\"window\":\"%s\"}", 
                            key, count, windowedKey.window());
                    return KeyValue.pair(key, value);
                })
                .peek((key, value) -> logger.info("消息统计: key={}, value={}", key, value))
                .to("message-statistics", Produced.with(Serdes.String(), Serdes.String()));
    }

    /**
     * 处理单个消息
     */
    private String processMessage(String messageJson) {
        try {
            UserMessage userMessage = objectMapper.readValue(messageJson, UserMessage.class);
            
            // 消息处理逻辑
            if ("URGENT".equals(userMessage.getMessageType())) {
                userMessage.setMessage("[紧急] " + userMessage.getMessage());
            } else {
                userMessage.setMessage("[普通] " + userMessage.getMessage());
            }
            
            return objectMapper.writeValueAsString(userMessage);
        } catch (Exception e) {
            logger.error("处理消息失败: {}", messageJson, e);
            return null;
        }
    }

    /**
     * 处理用户事件
     */
    private String processUserEvent(String eventJson) {
        try {
            UserEvent userEvent = objectMapper.readValue(eventJson, UserEvent.class);
            
            // 事件处理逻辑
            if ("LOGIN".equals(userEvent.getEventType())) {
                userEvent.setEventData("用户登录: " + userEvent.getEventData());
            } else if ("LOGOUT".equals(userEvent.getEventType())) {
                userEvent.setEventData("用户登出: " + userEvent.getEventData());
            }
            
            return objectMapper.writeValueAsString(userEvent);
        } catch (Exception e) {
            logger.error("处理用户事件失败: {}", eventJson, e);
            return null;
        }
    }
}