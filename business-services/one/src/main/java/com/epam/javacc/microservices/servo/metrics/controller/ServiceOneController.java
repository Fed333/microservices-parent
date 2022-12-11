package com.epam.javacc.microservices.servo.metrics.controller;

import com.epam.javacc.microservices.servo.metrics.service.ServiceOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/service-one")
@RequiredArgsConstructor
public class ServiceOneController {

    private final ServiceOneService oneService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String,Object> getLinks() {
       return oneService.getLinks();
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public Map<String,String> getAuthorsInfo() {
        return oneService.getAuthorsInfo();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Object> postMethod() {
       return oneService.handlePost();
    }

}
