# 本项目是集成security和jwt的  资源服务器的springboot项目

1. 配置文件

pom.xml中配置如下：

`
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
`

application.yml中配置如下：

`spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          public-key-location: classpath:public-key.text`



public-key.text为jwt公钥

