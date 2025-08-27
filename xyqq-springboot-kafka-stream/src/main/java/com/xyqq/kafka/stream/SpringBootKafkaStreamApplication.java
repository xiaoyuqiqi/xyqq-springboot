package com.xyqq.kafka.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

/**
 * Kafka Stream 启动类
 *
 * @author xyqq
 */
@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class SpringBootKafkaStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootKafkaStreamApplication.class, args);
        System.out.println("\n=== Kafka Stream 应用启动成功 ===");
        System.out.println("应用访问地址: http://localhost:8080/kafka-stream");
        System.out.println("健康检查: http://localhost:8080/kafka-stream/kafka/health");
        System.out.println("测试消息发送: POST http://localhost:8080/kafka-stream/kafka/test/message");
        System.out.println("测试事件发送: POST http://localhost:8080/kafka-stream/kafka/test/event");
        System.out.println("================================\n");
    }
}
