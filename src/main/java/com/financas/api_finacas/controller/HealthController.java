package com.financas.api_finacas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
public class HealthController {

    @RequestMapping("/health")
    public String health() {
        return "OK";
    }
}
