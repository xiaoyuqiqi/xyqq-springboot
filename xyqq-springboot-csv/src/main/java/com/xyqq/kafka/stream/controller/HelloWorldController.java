package com.xyqq.kafka.stream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloWorld")
public class HelloWorldController {
    @GetMapping(value = "/v1")
    public String helloWorld() {
        return "HelloWorld";
    }

}
