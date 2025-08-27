package com.xyqq.kafka.stream.controller;

import com.xyqq.kafka.stream.model.UserEvent;
import com.xyqq.kafka.stream.model.UserMessage;
import com.xyqq.kafka.stream.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Stream 控制器
 *
 * @author xyqq
 */
@RestController
@RequestMapping("/kafka")
public class KafkaStreamController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * 发送用户消息
     */
    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody UserMessage userMessage) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            kafkaProducerService.sendUserMessage(userMessage);
            response.put("success", true);
            response.put("message", "用户消息发送成功");
            response.put("data", userMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "用户消息发送失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 发送用户事件
     */
    @PostMapping("/event")
    public ResponseEntity<Map<String, Object>> sendEvent(@RequestBody UserEvent userEvent) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            kafkaProducerService.sendUserEvent(userEvent);
            response.put("success", true);
            response.put("message", "用户事件发送成功");
            response.put("data", userEvent);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "用户事件发送失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 发送测试消息
     */
    @PostMapping("/test/message")
    public ResponseEntity<Map<String, Object>> sendTestMessage(
            @RequestParam(defaultValue = "user001") String userId,
            @RequestParam(defaultValue = "Hello Kafka Stream!") String message,
            @RequestParam(defaultValue = "NORMAL") String messageType) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserMessage userMessage = new UserMessage(userId, message, messageType);
            kafkaProducerService.sendUserMessage(userMessage);
            
            response.put("success", true);
            response.put("message", "测试消息发送成功");
            response.put("data", userMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "测试消息发送失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 发送测试事件
     */
    @PostMapping("/test/event")
    public ResponseEntity<Map<String, Object>> sendTestEvent(
            @RequestParam(defaultValue = "user001") String userId,
            @RequestParam(defaultValue = "LOGIN") String eventType,
            @RequestParam(defaultValue = "用户登录系统") String eventData,
            @RequestParam(defaultValue = "web") String source) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserEvent userEvent = new UserEvent(userId, eventType, eventData, source);
            kafkaProducerService.sendUserEvent(userEvent);
            
            response.put("success", true);
            response.put("message", "测试事件发送成功");
            response.put("data", userEvent);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "测试事件发送失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 批量发送测试消息
     */
    @PostMapping("/test/batch-messages")
    public ResponseEntity<Map<String, Object>> sendBatchTestMessages(
            @RequestParam(defaultValue = "5") int count) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserMessage[] messages = new UserMessage[count];
            for (int i = 0; i < count; i++) {
                String userId = "user" + String.format("%03d", i + 1);
                String message = "批量测试消息 #" + (i + 1);
                String messageType = (i % 2 == 0) ? "NORMAL" : "URGENT";
                messages[i] = new UserMessage(userId, message, messageType);
            }
            
            kafkaProducerService.sendUserMessagesBatch(messages);
            
            response.put("success", true);
            response.put("message", "批量测试消息发送成功");
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "批量测试消息发送失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Kafka Stream Service");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}