package com.example.ussd.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.ussd.data.Menu;
import com.example.ussd.service.MenuService;
import com.example.ussd.service.UssdRoutingService;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class IndexController {    

    @Autowired
    private MenuService menuService;

    @Autowired
    private UssdRoutingService ussdRoutingService;

    
    @GetMapping(path = "menus")
    public Map<String, Menu> menusLoad() throws IOException {
        return menuService.loadMenus();
    }
    
    @GetMapping(path = "")
    public String index() {
        return "Your have reached us";
    }
 
    @PostMapping(path = "")
    public String ussdIngress(@RequestParam String sessionId, @RequestParam String serviceCode,
            @RequestParam String phoneNumber, @RequestParam String text) {
        try {
            System.out.println(sessionId+" "+serviceCode+" "+phoneNumber+" "+text);
            return ussdRoutingService.menuLevelRouter(sessionId, serviceCode, phoneNumber, text);
        } catch (IOException e) {
            return "END " + e.getMessage();
        }
    }
}