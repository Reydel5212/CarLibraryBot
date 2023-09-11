package org.ReydelBot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarRestController {
    @GetMapping()
    public String getInfo(){
        return null;

    }
}
