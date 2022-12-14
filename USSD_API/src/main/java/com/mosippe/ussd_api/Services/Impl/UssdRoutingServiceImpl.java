package com.mosippe.ussd_api.Services.Impl;

import com.mosippe.ussd_api.Entities.Menu;
import com.mosippe.ussd_api.Entities.MenuOption;
import com.mosippe.ussd_api.Entities.UserData;
import com.mosippe.ussd_api.Entities.UssdSession;
import com.mosippe.ussd_api.Repositories.UserRepository;
import com.mosippe.ussd_api.Repositories.UssdSessionRepository;
import com.mosippe.ussd_api.Services.MenuService;
import com.mosippe.ussd_api.Services.SessionService;
import com.mosippe.ussd_api.Services.UserService;
import com.mosippe.ussd_api.Services.UssdRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service
public class UssdRoutingServiceImpl implements UssdRoutingService {
    @Autowired
    private MenuService menuService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UssdSessionRepository ussdSessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Override
    public String menuLevelRouter(String sessionId, String serviceCode, String phoneNumber, String text) throws IOException {
        Map<String, Menu> menus = menuService.loadMenus();
        UssdSession session = checkAndSetSession(sessionId, serviceCode, phoneNumber, text);
        /**
         * Check if response has some value
         */
        System.out.println(text.length());
        return text.length() > 0 ? getNextMenuItem(session, menus) : menus.get(session.getCurrentMenuLevel()).getText();
    }

    @Override
    public String getNextMenuItem(UssdSession session, Map<String, Menu> menus) throws IOException {
        String lastValue = splitAndGetLastInput(session);
        Menu menuLevel = menus.get(session.getCurrentMenuLevel());

        if(lastValue.length() == 12)
        {
            MenuOption menuOption = menuLevel.getMenuOptions().get(0);
            return processMenuOption(session, menuOption);
        }
        if(lastValue.length() == 4)
        {
            if(userService.validatePin(lastValue,session.getPhoneNumber())) {
                MenuOption menuOption = menuLevel.getMenuOptions().get(0);
                return processMenuOption(session, menuOption);
            }
            else {
                System.out.println("PIN not matched");
            }
        }
        if (Integer.parseInt(lastValue) <= menuLevel.getMaxSelections()) {
            MenuOption menuOption = menuLevel.getMenuOptions().get(Integer.parseInt(lastValue) - 1);
            return processMenuOption(session, menuOption);
        }

        return "CON ";
    }

    @Override
    public String getMenu(String menuLevel) throws IOException {
        return menuService.loadMenus().get(menuLevel).getText();
    }

    @Override
    public String processMenuOption(UssdSession session, MenuOption menuOption) throws IOException {
        switch (menuOption.getType()) {
            case "response":
                return processMenuOptionResponses(session, menuOption);
            case "level":
                updateSessionMenuLevel(session, menuOption.getNextMenuLevel());
                return getMenu(menuOption.getNextMenuLevel());
            default:
                return "CON ";
        }
    }

    @Override
    public String processMenuOptionResponses(UssdSession session, MenuOption menuOption) {
            String response = menuOption.getResponse();
            Map<String, String> variablesMap = new HashMap<>();
            UssdSession ussdSession = this.ussdSessionRepository.findBySessionId(session.getSessionId());
            UserData userData = userRepository.findByPhoneNo(ussdSession.getPhoneNumber());
            switch (menuOption.getAction()) {
                case PROCESS_SAVE_UIN:
                    String lastValue = splitAndGetLastInput(session);
                    System.out.println("#####" + "\n" + lastValue);
                    UserData userData1 = new UserData(lastValue, session.getPhoneNumber(), null);
                    userRepository.save(userData1);
                    break;
                case PROCESS_GENERATE_VID:
                    String vid = userService.generateVid(session.getPhoneNumber());
                    variablesMap.put("VID", vid);
                    break;
                case PROCESS_ACC_UIN:
                    variablesMap.put("UIN", userData.getAadharNo());
            }

            response = replaceVariable(variablesMap, response);
            return response;
    }

    @Override
    public String replaceVariable(Map<String, String> variablesMap, String response) {
        return new StringSubstitutor(variablesMap).replace(response);
    }

    @Override
    public UssdSession updateSessionMenuLevel(UssdSession session, String menuLevel) {
        session.setPreviousMenuLevel(session.getCurrentMenuLevel());
        session.setCurrentMenuLevel(menuLevel);
        return sessionService.updateSession(session);
    }

    @Override
    public UssdSession checkAndSetSession(String sessionId, String serviceCode, String phoneNumber, String text) {
        UssdSession session = sessionService.getSession(sessionId);

        if (session != null) {
            session.setText(text);
            return sessionService.updateSession(session);
        }

        session = new UssdSession();
        session.setCurrentMenuLevel("1");
        session.setPreviousMenuLevel("1");
        session.setSessionId(sessionId);
        session.setPhoneNumber(phoneNumber);
        session.setServiceCode(serviceCode);
        session.setText(text);

        return sessionService.createUssdSession(session);
    }
    private String splitAndGetLastInput(UssdSession session){
        String[] levels = session.getText().split("\\*");
        return levels[levels.length - 1];
    }
}
