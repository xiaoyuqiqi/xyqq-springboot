package com.xyqq.kafka.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 *
 * @author xyqq
 */
@SpringBootApplication
@RestController
public class SpringBootCsvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCsvApplication.class, args);
    }
}
