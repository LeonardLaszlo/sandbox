package hu.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/api")
    String homeAlone() {
        int i = 0;
        String s = "Hello World!";
        return s;
    }

}
