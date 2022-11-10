package com.example.ussd.service;


import com.example.ussd.data.Menu;
import com.example.ussd.data.MenuOption;
import com.example.ussd.data.UserData;
import com.example.ussd.data.UssdSession;
import com.example.ussd.repository.UserRepository;
import com.example.ussd.repository.UssdSessionRepository;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UssdRoutingService {
    @Autowired
    private MenuService menuService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UssdSessionRepository repo;

    @Autowired 
    private UserRepository userRepo;
    
    public String menuLevelRouter(String sessionId, String serviceCode, String phoneNumber, String text)
            throws IOException {
        Map<String, Menu> menus = menuService.loadMenus();
        System.out.println(menus);
        UssdSession session = checkAndSetSession(sessionId, serviceCode, phoneNumber, text);
        System.out.println(session);
        System.out.println("------------------------");
        /**
         * Check if response has some value
         */
        System.out.println(session.getCurrentMenuLevel()+" "+menus.get(session.getCurrentMenuLevel()).getText());
        return text.length() > 0 ? getNextMenuItem(session, menus) : menus.get(session.getCurrentMenuLevel()).getText();
    }

  
    public String getNextMenuItem(UssdSession session, Map<String, Menu> menus) throws IOException {
        String[] levels = session.getText().split("\\*");
        String lastValue = levels[levels.length - 1];
        Menu menuLevel = menus.get(session.getCurrentMenuLevel());

        if (Integer.parseInt(lastValue) <= menuLevel.getMaxSelections()) {
            MenuOption menuOption = menuLevel.getMenuOptions().get(Integer.parseInt(lastValue) - 1);
            return processMenuOption(session, menuOption);
        }

        return "CON ";
    }

   
    public String getMenu(String menuLevel) throws IOException {
        return menuService.loadMenus().get(menuLevel).getText();
    }

   
    public String processMenuOption(UssdSession session, MenuOption menuOption) throws IOException {
        switch (menuOption.getType()) {
            case "response":
                return processMenuOptionResponses(session,menuOption);
            case "level":
                updateSessionMenuLevel(session, menuOption.getNextMenuLevel());
                return getMenu(menuOption.getNextMenuLevel());
            default:
                return "CON ";
        }
    }

    
    public String processMenuOptionResponses(UssdSession session,MenuOption menuOption) {
        String response = menuOption.getResponse();
        Map<String, String> variablesMap = new HashMap<>();
        UssdSession sess = repo.findById1(session.getId());
        System.out.println("\n"+sess.getPhoneNumber()+"\n");
        UserData user= userRepo.findByPhone(sess.getPhoneNumber());
        List<UserData> users=userRepo.getUserData();
        System.out.println("\n"+users+"\n");
        switch (menuOption.getAction()) {
            case PROCESS_ACC_BALANCE:
                variablesMap.put("account_balance", "10000");
                break;
            case PROCESS_ACC_NUMBER:
                variablesMap.put("account_number", "123412512");
                break;
            case PROCESS_ACC_PHONE_NUMBER:
                System.out.println("\nPhone number:"+sess.getPhoneNumber()+"\n");
                variablesMap.put("phone_number",sess.getPhoneNumber());
                break;
            case PROCESS_ACC_AADHAR:
                System.out.println("\naadhar number:"+user.getAadharNo()+"\n");
                variablesMap.put("aadhar_number",user.getAadharNo());
                break;
        }
         
        response = replaceVariable(variablesMap, response);
        return response;
    }

   
    public String replaceVariable(Map<String, String> variablesMap, String response) {
        return new StringSubstitutor(variablesMap).replace(response);
    }

    
    public UssdSession updateSessionMenuLevel(UssdSession session, String menuLevel) {
        session.setPreviousMenuLevel(session.getCurrentMenuLevel());
        session.setCurrentMenuLevel(menuLevel);
        return sessionService.update(session);
    }

    
    public UssdSession checkAndSetSession(String sessionId, String serviceCode, String phoneNumber, String text) {
        UssdSession session = sessionService.get(sessionId);
        System.out.println("------------------------");
        System.out.println(session);
        System.out.println("----------------------------");
        if (session != null) {
            session.setText(text);
            return sessionService.update(session);
        }

        session = new UssdSession();
        session.setCurrentMenuLevel("1");
        session.setPreviousMenuLevel("1");
        session.setId(sessionId);
        session.setPhoneNumber(phoneNumber);
        session.setServiceCode(serviceCode);
        session.setText(text);

        return sessionService.createUssdSession(session);
    }
    
}
