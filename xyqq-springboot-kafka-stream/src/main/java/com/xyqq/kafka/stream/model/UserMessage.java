package com.xyqq.kafka.stream.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * 用户消息模型
 *
 * @author xyqq
 */
public class UserMessage {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message_type")
    private String messageType;

    public UserMessage() {
    }

    public UserMessage(String userId, String message, String messageType) {
        this.userId = userId;
        this.message = message;
        this.messageType = messageType;
        this.timestamp = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}