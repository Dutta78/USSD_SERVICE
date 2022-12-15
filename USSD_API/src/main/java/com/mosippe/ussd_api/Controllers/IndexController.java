package com.mosippe.ussd_api.Controllers;

import com.mosippe.ussd_api.Entities.Menu;
import com.mosippe.ussd_api.Services.MenuService;
import com.mosippe.ussd_api.Services.UssdRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @GetMapping("")
    public String test(){
        return "Hello there! Nice to meet you";
    }
//    @PostMapping("")
//    public String test1(){
//        return "CON Hello there! This is post method";
//    }
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
