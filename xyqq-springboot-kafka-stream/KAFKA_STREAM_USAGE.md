# Kafka Stream 集成使用指南

## 项目概述

本项目已成功集成 Kafka Stream，提供了完整的流处理功能，包括消息处理、事件处理和流聚合等功能。

## 功能特性

- ✅ Kafka Stream 流处理
- ✅ 用户消息处理和转换
- ✅ 用户事件流处理
- ✅ 消息统计和聚合
- ✅ REST API 接口
- ✅ 批量消息处理

## 快速开始

### 1. 启动 Kafka

```bash
# 启动 Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# 启动 Kafka
bin/kafka-server-start.sh config/server.properties
```

### 2. 创建主题

```bash
# 创建输入主题
bin/kafka-topics.sh --create --topic input-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# 创建输出主题
bin/kafka-topics.sh --create --topic output-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# 创建用户事件主题
bin/kafka-topics.sh --create --topic user-events-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# 创建处理后的用户事件主题
bin/kafka-topics.sh --create --topic processed-user-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# 创建消息统计主题
bin/kafka-topics.sh --create --topic message-statistics --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

### 3. 启动应用

```bash
mvn spring-boot:run
```

## API 接口

### 健康检查
```bash
GET http://localhost:8080/kafka-stream/kafka/health
```

### 发送测试消息
```bash
POST http://localhost:8080/kafka-stream/kafka/test/message
参数:
- userId: 用户ID (默认: user001)
- message: 消息内容 (默认: Hello Kafka Stream!)
- messageType: 消息类型 (默认: NORMAL, 可选: URGENT)
```

### 发送测试事件
```bash
POST http://localhost:8080/kafka-stream/kafka/test/event
参数:
- userId: 用户ID (默认: user001)
- eventType: 事件类型 (默认: LOGIN, 可选: LOGOUT)
- eventData: 事件数据 (默认: 用户登录系统)
- source: 事件来源 (默认: web)
```

### 发送用户消息
```bash
POST http://localhost:8080/kafka-stream/kafka/message
Content-Type: application/json

{
  "userId": "user001",
  "message": "Hello Kafka Stream!",
  "messageType": "NORMAL"
}
```

### 发送用户事件
```bash
POST http://localhost:8080/kafka-stream/kafka/event
Content-Type: application/json

{
  "userId": "user001",
  "eventType": "LOGIN",
  "eventData": "用户登录系统",
  "source": "web"
}
```

### 批量发送测试消息
```bash
POST http://localhost:8080/kafka-stream/kafka/test/batch-messages
参数:
- count: 消息数量 (默认: 5)
```

## 流处理逻辑

### 1. 用户消息处理
- 接收来自 `input-topic` 的消息
- 根据消息类型添加前缀标识
- 输出到 `output-topic`

### 2. 用户事件处理
- 接收来自 `user-events-topic` 的事件
- 根据事件类型进行处理
- 输出到 `processed-user-events`

### 3. 消息统计聚合
- 按用户ID分组统计消息数量
- 5分钟时间窗口聚合
- 输出到 `message-statistics`

## 监控消息

### 监控输出主题
```bash
bin/kafka-console-consumer.sh --topic output-topic --from-beginning --bootstrap-server localhost:9092
```

### 监控用户事件处理结果
```bash
bin/kafka-console-consumer.sh --topic processed-user-events --from-beginning --bootstrap-server localhost:9092
```

### 监控消息统计
```bash
bin/kafka-console-consumer.sh --topic message-statistics --from-beginning --bootstrap-server localhost:9092
```

## 配置说明

主要配置在 `application.yml` 中：

- `spring.kafka.bootstrap-servers`: Kafka 服务器地址
- `spring.kafka.streams.application-id`: Stream 应用ID
- `kafka.topics.*`: 各种主题名称配置

## 注意事项

1. 确保 Kafka 服务正常运行
2. 创建所需的主题
3. 检查端口 8080 是否被占用
4. 查看应用日志了解处理状态

## 故障排除

1. **连接失败**: 检查 Kafka 服务是否启动
2. **主题不存在**: 手动创建所需主题
3. **序列化错误**: 检查消息格式是否正确
4. **处理延迟**: 检查 Kafka Stream 配置参数