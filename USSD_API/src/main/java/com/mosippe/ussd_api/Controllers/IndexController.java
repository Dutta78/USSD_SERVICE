package com.mosippe.ussd_api.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class IndexController {
    @GetMapping("")
    public String test(){
        return "Hello there! Nice to meet you";
    }
    @PostMapping("")
    public String test1(){
        return "CON Hello there! This is post method";
    }
}
