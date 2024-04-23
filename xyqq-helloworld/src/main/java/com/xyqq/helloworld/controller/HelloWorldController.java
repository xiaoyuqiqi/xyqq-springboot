package com.xyqq.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * helloWorld
 *
 * @author xyqq
 */
@RestController
@RequestMapping("/helloWorld")
@Valid
public class HelloWorldController {
    @GetMapping(value = "/v1")
    public String helloWorld() {
        return "HelloWorld";
    }

}
