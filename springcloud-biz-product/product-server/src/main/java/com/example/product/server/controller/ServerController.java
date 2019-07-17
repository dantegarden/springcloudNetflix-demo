package com.example.product.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {

    @RequestMapping("/sayHello")
    public String sayHello2RestTemplate() {
        return "hello";
    }
}
