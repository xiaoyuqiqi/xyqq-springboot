package com.xyqq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.xyqq.dao")
@SpringBootApplication
public class XyqqSpringbootMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(XyqqSpringbootMybatisPlusApplication.class, args);
    }

}
