package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;

@RestController
public class WelcomeServelet {

    @GetMapping("/")
    public String welcome() {
        LocalTime currentTime = LocalTime.now();
        String greeting;

        // Simple logic to determine time of day
        if (currentTime.isBefore(LocalTime.NOON)) {
            greeting = "Good morning";
        } else {
            greeting = "Good afternoon";
        }

        return greeting + ", Parth Sharma, Welcome to COMP367";
    }
}